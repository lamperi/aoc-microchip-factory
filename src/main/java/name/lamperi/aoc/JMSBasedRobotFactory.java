package name.lamperi.aoc;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.jms.Destination;
import javax.jms.JMSContext;

@Named
@Singleton
public class JMSBasedRobotFactory extends JMSBasedFactoryBase implements RobotFactory {

   @Override
   public Robot createRobot(RobotDefinition robotDef) {
      JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE);
      Destination inputDestination = destinationStrategies.createForRobot(context, robotDef.getId());
      Destination lowOutputDestination = createDestination(context, robotDef.getLowOutput());
      Destination highOutputDestination = createDestination(context, robotDef.getHighOutput()); 
      Destination dest = destinationStrategies.createForStats(context);
      JMSBasedRobot robot = new JMSBasedRobot(robotDef.getId(), context, inputDestination, lowOutputDestination, highOutputDestination, dest);
      return robot;
   }
}
