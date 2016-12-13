package name.lamperi.aoc;

import java.util.Set;

public class Problem {

   private final Set<ValueDefinition> values;
   private final Set<RobotDefinition> robots;
   
   public Problem(Set<ValueDefinition> values, Set<RobotDefinition> robots) {
      this.values = values;
      this.robots = robots;
   }
   
   public Set<ValueDefinition> getValueDefinitions() {
      return values;
   }
   
   public Set<RobotDefinition> getRobotDefinitions() {
      return robots;
   }
}
