package name.lamperi.aoc;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class ProblemSolverImpl implements ProblemSolver {

   @Inject
   RobotFactory robotFactory;
   
   @Inject
   ValueInputFactory valueInputFactory;
   
   @Inject
   OutputConsumerFactory outputConsumerFactory;

   @Inject
   StatsConsumerFactory statsConsumerFactory;

   @Inject
   WorkInvokerFactory workInvoker;
   
   @Override
   public int solveProblem1(Problem problem) {
      CompletableFuture<List<Worker>> results = getResults(problem);
      
      try {
         return results.get().stream()
               .filter(StatsConsumer.class::isInstance)
               .map(StatsConsumer.class::cast)
               .map(StatsConsumer::getMatchingBotId)
               .map(Integer::parseInt)
               .findFirst().orElse(null);
      } catch (InterruptedException | ExecutionException e) {
         // Just throw as unchecked :)
         throw new RuntimeException(e);
      }
   }
   
   @Override
   public int solveProblem2(Problem problem) {
      CompletableFuture<List<Worker>> results = getResults(problem);
      
      try {
         return results.get().stream()
               .filter(OutputConsumer.class::isInstance)
               .map(OutputConsumer.class::cast)
               .map(OutputConsumer::getValue)
               .map(Optional::get) // It's not optional
               .reduce(1, (left, right) -> left * right);
      } catch (InterruptedException | ExecutionException e) {
         // Just throw as unchecked :)
         throw new RuntimeException(e);
      }
   }

   private CompletableFuture<List<Worker>> getResults(Problem problem) {
      WorkInvoker invoker = workInvoker.createJob();
      
      for (RobotDefinition definition : problem.getRobotDefinitions()) {
         Robot robot = robotFactory.createRobot(definition);
         invoker.addWorker(robot);
      }
      for (ValueDefinition definition : problem.getValueDefinitions()) {
         ValueInput valueInput = valueInputFactory.createValueInput(definition);
         invoker.addWorker(valueInput);
      }
      
      for (int i = 0; i < 3; i++) {
         OutputConsumer consumer = outputConsumerFactory.createOutputConsumer(String.valueOf(i));
         invoker.addWorker(consumer);
      }
      
      StatsConsumer statsConsumer = statsConsumerFactory.createStatsConsumer(17, 61);
      invoker.addWorker(statsConsumer);
      
      CompletableFuture<List<Worker>> results = invoker.invokeAll();
      
      return results;
   }
}
