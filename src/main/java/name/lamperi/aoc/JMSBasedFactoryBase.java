package name.lamperi.aoc;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;

public class JMSBasedFactoryBase {

   @Inject
   protected ConnectionFactory connectionFactory;
   
   @Inject
   protected JMSBasedDestinationStrategies destinationStrategies;

   public JMSBasedFactoryBase() {
      super();
   }

   protected Destination createDestination(JMSContext topicCreator, OutputDefinition def) {
      Destination destination;
      if (def.isRobot()) {
         destination = destinationStrategies.createForRobot(topicCreator, def.getId());
      } else {
         destination = destinationStrategies.createForValueOutput(topicCreator, def.getId());
      }
      return destination;
   }

}