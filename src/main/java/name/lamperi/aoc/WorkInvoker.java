package name.lamperi.aoc;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface WorkInvoker {

   void addWorker(Worker robot);

   CompletableFuture<List<Worker>> invokeAll();

}
