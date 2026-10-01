// Section 8's three enquiries (ThreeWindows) in one scope that wants every answer (java --enable-preview).
import java.util.concurrent.StructuredTaskScope.Joiner;

void main() throws Exception {
    long start = System.nanoTime();
    try (var enquiries = StructuredTaskScope.open(Joiner.<String>allSuccessfulOrThrow())) {
        enquiries.fork(lookup("berth S7-42", 300, start));                    // the slowest, forked first
        enquiries.fork(lookup("fare 1250", 250, start));
        enquiries.fork(lookup("platform 3", 200, start));                     // the fastest, forked last
        List<String> answers = enquiries.join();                              // every answer, or an exception
        IO.println(at(start) + "join returned " + answers);
    }
}

static Callable<String> lookup(String answer, long ms, long start) {
    return () -> {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            IO.println(at(start) + "told to stop: " + answer);
            throw e;
        }
        IO.println(at(start) + "a clerk finished: " + answer);
        return answer;
    };
}

static String at(long start) {
    return String.format("%4d ms  ", (System.nanoTime() - start) / 1_000_000);
}
