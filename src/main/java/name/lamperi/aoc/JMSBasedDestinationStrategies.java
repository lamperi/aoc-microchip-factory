package name.lamperi.aoc;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.jms.Destination;
import javax.jms.JMSContext;

@Named
@Singleton
public class JMSBasedDestinationStrategies {

   public String buildRobotQueueName(String robotId) {
      return "ROBOT." + robotId;
   }
   
   public Destination createForRobot(JMSContext context, String robotId) {
      return context.createQueue(buildRobotQueueName(robotId));
   }
   
   public String buildOutputQueueName(String outputId) {
      return "OUTPUT." + outputId;
   }

   public Destination createForValueOutput(JMSContext context, String outputId) {
      return context.createQueue(buildOutputQueueName(outputId));
   }
   
   public String getStatsQueueName() {
      return "STATS";
   }
   
   public Destination createForStats(JMSContext context) {
      return context.createQueue(getStatsQueueName());
   }
}
