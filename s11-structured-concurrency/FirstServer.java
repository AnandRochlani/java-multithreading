// Section 8's three mirror servers (FirstAnswer) in one scope that wants the first good answer (java --enable-preview).
import java.util.concurrent.StructuredTaskScope.Joiner;

void main() throws Exception {
    long start = System.nanoTime();
    try (var mirrors = StructuredTaskScope.open(Joiner.<String>anySuccessfulOrThrow())) {
        mirrors.fork(mirror("north", 300, start));                            // one question, three servers
        mirrors.fork(mirror("south", 200, start));
        mirrors.fork(mirror("east", 250, start));
        String berth = mirrors.join();                                        // the first good answer wins
        IO.println(at(start) + "join returned: " + berth);
    }
    IO.println(at(start) + "the try block is over");
}

static Callable<String> mirror(String server, long ms, long start) {
    return () -> {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            IO.println(at(start) + server + " server: told to stop");
            throw e;
        }
        IO.println(at(start) + server + " server: answered");
        return "S7-42 from the " + server + " server";
    };
}

static String at(long start) {
    return String.format("%4d ms  ", (System.nanoTime() - start) / 1_000_000);
}
