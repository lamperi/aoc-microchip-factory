package name.lamperi.aoc;

public class ValueDefinition {

   private final String id;
   private final OutputDefinition output;
   
   public ValueDefinition(String id, OutputDefinition output) {
      this.id = id;
      this.output = output;
   }

   public String getId() {
      return id;
   }
   
   public OutputDefinition getOutput() {
      return output;
   }
}
