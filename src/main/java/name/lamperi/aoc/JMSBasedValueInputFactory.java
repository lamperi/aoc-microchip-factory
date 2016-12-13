package name.lamperi.aoc;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.jms.Destination;
import javax.jms.JMSContext;

@Named
@Singleton
public class JMSBasedValueInputFactory extends JMSBasedFactoryBase implements ValueInputFactory {

   @Override
   public ValueInput createValueInput(ValueDefinition valueDef) {
      JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE);
      Destination outputDestination = createDestination(context, valueDef.getOutput());
   
      JMSBasedValueInput valueInput = new JMSBasedValueInput(valueDef.getId(), context, outputDestination);
      return valueInput;   
   }
}
