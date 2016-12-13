package name.lamperi.aoc;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.jms.Destination;
import javax.jms.JMSContext;

@Named
@Singleton
public class JMSBasedOutputConsumerFactory extends JMSBasedFactoryBase implements OutputConsumerFactory {


   @Override
   public OutputConsumer createOutputConsumer(String id) {
      JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE);
      Destination inputDestination = destinationStrategies.createForValueOutput(context, id);
      JMSBasedOutputConsumer outputConsumer = new JMSBasedOutputConsumer(id, context, inputDestination);
      return outputConsumer;
   }
}
