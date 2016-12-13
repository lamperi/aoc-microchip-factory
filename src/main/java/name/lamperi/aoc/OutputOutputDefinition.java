package name.lamperi.aoc;

public class OutputOutputDefinition implements OutputDefinition {

   private final String id;
   
   public OutputOutputDefinition(String id) {
      this.id = id;
   }

   public String getId() {
      return id;
   }
   
   @Override
   public boolean isOutput() {
      return true;
   }
   
   @Override
   public boolean isRobot() {
      return false;
   }
}
