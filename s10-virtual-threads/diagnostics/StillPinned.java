// What still pins on 27: a virtual thread that naps INSIDE a class initializer keeps its carrier.
// Run it twice:  java StillPinned.java   and   java -Djdk.virtualThreadScheduler.parallelism=1 StillPinned.java
static class Timetable {
    static { nap(1_000); }                                    // loading the timetable takes one second
    static void read() {}
}

void main() throws InterruptedException {
    Thread loader = Thread.ofVirtual().start(Timetable::read);   // one card loads the timetable: a nap inside an initializer
    Thread.sleep(50);                                         // let it start
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {
        for (int i = 0; i < 1_000; i++) desk.execute(() -> nap(100));   // a thousand ordinary naps
    }
    IO.println("1000 ordinary naps of 100 ms took " + (System.nanoTime() - start) / 1_000_000
            + " ms, while another card napped inside a class initializer");
    loader.join();
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
