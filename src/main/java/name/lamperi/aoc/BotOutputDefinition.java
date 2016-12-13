package name.lamperi.aoc;

public class BotOutputDefinition implements OutputDefinition {

   private final String id;
   
   public BotOutputDefinition(String id) {
      this.id = id;
   }
   
   public String getId() {
      return id;
   }

   @Override
   public boolean isOutput() {
      return false;
   }
   
   @Override
   public boolean isRobot() {
      return true;
   }
}
