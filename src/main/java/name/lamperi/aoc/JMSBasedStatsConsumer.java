package name.lamperi.aoc;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSBasedStatsConsumer implements StatsConsumer {

   private final Logger logger = LoggerFactory.getLogger(JMSBasedStatsConsumer.class);

   private final JMSContext context;
   private final Destination inputDestination;
   private final int lowValue;
   private final int highValue;

   private String botId;
   
   public JMSBasedStatsConsumer(JMSContext context, Destination inputDestination, int lowValue, int highValue) {
      this.context = context;
      this.inputDestination = inputDestination;
      this.lowValue = lowValue;
      this.highValue = highValue;
   }

   @Override
   public void doWork() {
      Thread.currentThread().setName("JMSBasedStatsConsumer");
      try {

         logger.trace("Ready to receive stats from {}", inputDestination);
         JMSConsumer consumer = context.createConsumer(inputDestination);
         
         while (true) {
            @SuppressWarnings("unchecked")
            Map<String, Object> stats = (Map<String, Object>) consumer.receiveBody(Map.class);
            logger.trace("Received {}", stats);
            int low = (int) stats.get("LOW");
            int high = (int) stats.get("HIGH");
            if (low == lowValue && high == highValue) {
               botId = (String) stats.get("ID");
               break;
            }
         }
         
      } catch (Exception e) {
         logger.error("Received exception when waiting for value", e);
      } finally {
         context.close();
      }
   }

   @Override
   public String getMatchingBotId() {
      return botId;
   }

}
