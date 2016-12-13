package name.lamperi.aoc;

import java.util.Optional;

public interface OutputConsumer extends Worker {

   Optional<Integer> getValue();
}
