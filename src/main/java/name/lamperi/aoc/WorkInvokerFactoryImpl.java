package name.lamperi.aoc;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
 public class WorkInvokerFactoryImpl implements WorkInvokerFactory {

   @Override
   public WorkInvoker createJob() {
      ExecutorBasedWorkInvoker invoker = new ExecutorBasedWorkInvoker();
      return invoker;
   }
}
