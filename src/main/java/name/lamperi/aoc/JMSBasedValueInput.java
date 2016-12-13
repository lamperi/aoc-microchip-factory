package name.lamperi.aoc;

import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSBasedValueInput implements ValueInput {

   private final Logger logger = LoggerFactory.getLogger(JMSBasedValueInput.class);

   private final String id;
   private final JMSContext context;
   private final Destination outputDestination;
   
   public JMSBasedValueInput(String id, JMSContext context, Destination outputDestination) {
      this.id = id;
      this.context = context;
      this.outputDestination = outputDestination;
   }

   @Override
   public void doWork() {
      Thread.currentThread().setName("JMSBasedValueInput-" + id);
      try {
      
         Integer value = Integer.parseInt(id);
         logger.trace("ValueInput {} ready to produce {}", id, value);
         
         JMSProducer producer = context.createProducer();
         producer.send(outputDestination, value);
         
         logger.trace("ValueInput {} produced {} to {}", id, value, outputDestination);
         
      } catch (Exception e) {
         logger.error("Received exception when waiting for value", e);
      } finally {
         context.close();
      }
   }

}
