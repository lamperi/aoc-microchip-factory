package name.lamperi.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSBasedRobot implements Robot {

   private final Logger logger = LoggerFactory.getLogger(JMSBasedRobot.class);
   private final String id;
   private final JMSContext context;
   
   private final Destination inputDestination;
   private final Destination lowOutputDestination;
   private final Destination highOutputDestination;
   private final Destination statsDestination;
   
   public JMSBasedRobot(String id, JMSContext context, Destination inputDestination, Destination lowOutputDestination, Destination highOutputDestination, Destination statsDestination) {
      this.id = id;
      this.context = context;
      this.inputDestination = inputDestination;
      this.lowOutputDestination = lowOutputDestination;
      this.highOutputDestination = highOutputDestination;
      this.statsDestination = statsDestination;
   }

   @Override
   public void doWork() {
      Thread.currentThread().setName("JMSBasedRobot-" + id);
      
      try {         
         logger.trace("{}: Ready to receive values from {}", id, inputDestination);
   
         JMSConsumer consumer = context.createConsumer(inputDestination);
         
         List<Integer> receivedValues = new ArrayList<>();
         while (receivedValues.size() < 2) {
            Integer value = consumer.receiveBody(Integer.class, 5000);
            if (value != null) {
               receivedValues.add(value);
               logger.trace("{}: Received value #{} {}", new Object[] { id, receivedValues.size(), value });
            }
         }
         
         Collections.sort(receivedValues);
         Integer lowValue = receivedValues.get(0);
         Integer highValue = receivedValues.get(1);
         
         logger.trace("{}: Ready to produce {} to low {} and {} to high {}", new Object[] { id, lowValue, lowOutputDestination, highValue, highOutputDestination });
         JMSProducer producer = context.createProducer();
         producer.send(lowOutputDestination, lowValue);
         producer.send(highOutputDestination, highValue);
         
         Map<String,Object> botStats = new HashMap<>();
         botStats.put("ID", this.id);
         botStats.put("LOW", lowValue);
         botStats.put("HIGH", highValue);
         producer.send(statsDestination, botStats);
         logger.trace("{}: Done", id);
         
      } catch (Exception e) {
         logger.error("Received exception when waiting for value", e);
      } finally {
         Thread.currentThread().setName("JMSBasedRobot-" + id + "-done");
         context.close();
      }
   }

}
