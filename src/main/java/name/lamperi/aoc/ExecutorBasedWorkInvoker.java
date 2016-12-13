package name.lamperi.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutorBasedWorkInvoker implements WorkInvoker {
   private final Logger logger = LoggerFactory.getLogger(ExecutorBasedWorkInvoker.class);

   private final List<Worker> workers = new ArrayList<>();
   
   @Override
   public void addWorker(Worker worker) {
      workers.add(worker);
   }
   
   @Override
   public CompletableFuture<List<Worker>> invokeAll() {
      // We need enough threads to run all workers at once.
      // Hope this is not too big!
      ExecutorService executor = Executors.newFixedThreadPool(workers.size());
      List<Worker> workersToRun = new ArrayList<>(workers);
      workers.clear();
      
      List<CompletableFuture<Worker>> futurs = new ArrayList<>();
      for (Worker worker : workersToRun) {
         CompletableFuture<Worker> future = CompletableFuture.supplyAsync(() -> {
            worker.doWork();
            return worker;
         }, executor);
         futurs.add(future);
      }
      
      logger.trace("Waiting for {} workers to finish", futurs.size());
      // Since Java API is so crappy we have to write this function by ourselves!
      return CompletableFuture.allOf(futurs.toArray(new CompletableFuture[0]))
            .thenRun(() -> logger.trace("Workers finished"))
            .thenApply(voidValue ->
                  futurs.stream()
                     .map(CompletableFuture::join)
                     .collect(Collectors.toList())
            );
   }
}
