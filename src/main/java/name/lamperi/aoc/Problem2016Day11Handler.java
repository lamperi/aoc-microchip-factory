package name.lamperi.aoc;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Named
@Singleton
@Path("/aoc/2016/10")
public class Problem2016Day11Handler {

   @Inject
   ProblemSolver problemSolver;
   
   @Inject
   InputParserBean parserBean; 
   
   @POST
   @Consumes("text/plain")
   @Path("/part1")
   public int handleProblem1(String body) {
      Problem problem = parserBean.parseProblem(body);
      return problemSolver.solveProblem1(problem);
   }
   
   @GET
   @Path("/part1")
   public String describeProblem1() {
      return "Please do a post request with problem input data"; 
   }
   
   @POST
   @Consumes("text/plain")
   @Path("/part2")
   public int handleProblem2(String body) {
      Problem problem = parserBean.parseProblem(body);
      return problemSolver.solveProblem2(problem);
   }
   
   @GET
   @Path("/part2")
   public String describeProblem2() {
      return "Please do a post request with problem input data"; 
   }
   

}
