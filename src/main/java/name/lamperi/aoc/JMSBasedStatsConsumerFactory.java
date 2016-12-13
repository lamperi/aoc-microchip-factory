package name.lamperi.aoc;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.jms.Destination;
import javax.jms.JMSContext;

@Named
@Singleton
public class JMSBasedStatsConsumerFactory extends JMSBasedFactoryBase implements StatsConsumerFactory {

   @Override
   public StatsConsumer createStatsConsumer(int lowValue, int highValue) {
      JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE);
      Destination dest = destinationStrategies.createForStats(context);
      JMSBasedStatsConsumer statsConsumer = new JMSBasedStatsConsumer(context, dest, lowValue, highValue);
      return statsConsumer;
   }
   
}
