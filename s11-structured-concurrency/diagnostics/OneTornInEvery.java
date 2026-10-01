// EveryAnswer with the fare lookup failing (java --enable-preview): the scope is cancelled and join throws.
import java.util.concurrent.StructuredTaskScope.Joiner;

void main() throws Exception {
    long start = System.nanoTime();
    try (var enquiries = StructuredTaskScope.open(Joiner.<String>allSuccessfulOrThrow())) {
        enquiries.fork(lookup("berth S7-42", 300, start, false));
        enquiries.fork(lookup("fare 1250", 250, start, true));               // this one fails
        enquiries.fork(lookup("platform 3", 200, start, false));
        List<String> answers = enquiries.join();
        IO.println(at(start) + "join returned " + answers);
    } catch (ExecutionException e) {
        IO.println(at(start) + "join threw " + e.getClass().getSimpleName() + ", cause: " + e.getCause());
    }
}

static Callable<String> lookup(String answer, long ms, long start, boolean fails) {
    return () -> {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            IO.println(at(start) + "told to stop: " + answer);
            throw e;
        }
        if (fails) throw new IllegalStateException(answer + ": service is down");
        IO.println(at(start) + "a clerk finished: " + answer);
        return answer;
    };
}

static String at(long start) {
    return String.format("%4d ms  ", (System.nanoTime() - start) / 1_000_000);
}
