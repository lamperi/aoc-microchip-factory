package name.lamperi.aoc;

import java.util.Optional;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSBasedOutputConsumer implements OutputConsumer {

   private final Logger logger = LoggerFactory.getLogger(JMSBasedOutputConsumer.class);

   private final String id;
   private final JMSContext context;
   private final Destination inputDestination;

   private Integer receivedValue;
   
   public JMSBasedOutputConsumer(String id, JMSContext context, Destination inputDestination) {
      this.id = id;
      this.context = context;
      this.inputDestination = inputDestination;
   }

   @Override
   public void doWork() {
      Thread.currentThread().setName("JMSBasedOutputConsumer-" + id);
      try {

         logger.trace("{}: Ready to receive value from {}", id, inputDestination);
         JMSConsumer consumer = context.createConsumer(inputDestination);
         
         receivedValue = consumer.receiveBody(Integer.class);
         logger.trace("{}: Received {}", id, receivedValue);
         
      } catch (Exception e) {
         logger.error("Received exception when waiting for value", e);
      } finally {
         context.close();
      }
   }

   @Override
   public Optional<Integer> getValue() {
      return Optional.ofNullable(receivedValue);
   }

}
