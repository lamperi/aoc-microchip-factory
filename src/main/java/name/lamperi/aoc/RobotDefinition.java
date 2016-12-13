package name.lamperi.aoc;

public class RobotDefinition {

   private final String id;
   private final OutputDefinition lowOutput;
   private final OutputDefinition highOutput;
   
   public RobotDefinition(String id, OutputDefinition lowOutput, OutputDefinition highOutput) {
      this.id = id;
      this.lowOutput = lowOutput;
      this.highOutput = highOutput;
   }

   public String getId() {
      return id;
   }
   
   public OutputDefinition getLowOutput() {
      return lowOutput;
   }
   
   public OutputDefinition getHighOutput() {
      return highOutput;
   }
}
