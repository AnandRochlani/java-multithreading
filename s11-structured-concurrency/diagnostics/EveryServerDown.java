// FirstServer with all three servers failing, then with no servers at all (java --enable-preview).
import java.util.concurrent.StructuredTaskScope.Joiner;

void main() throws Exception {
    long start = System.nanoTime();
    try (var mirrors = StructuredTaskScope.open(Joiner.<String>anySuccessfulOrThrow())) {
        mirrors.fork(down("north", 300));
        mirrors.fork(down("south", 200));
        mirrors.fork(down("east", 250));
        IO.println("join returned: " + mirrors.join());
    } catch (ExecutionException e) {
        IO.println(at(start) + "join threw " + e.getClass().getSimpleName() + ", cause: " + e.getCause());
    }
    try (var mirrors = StructuredTaskScope.open(Joiner.<String>anySuccessfulOrThrow())) {
        IO.println("join returned: " + mirrors.join());                     // nothing was forked
    } catch (ExecutionException e) {
        IO.println("no servers: join threw " + e.getClass().getSimpleName() + ", cause: " + e.getCause());
    }
}

static Callable<String> down(String server, long ms) {
    return () -> {
        Thread.sleep(ms);
        throw new IllegalStateException(server + " server is down");
    };
}

static String at(long start) {
    return String.format("%4d ms  ", (System.nanoTime() - start) / 1_000_000);
}
