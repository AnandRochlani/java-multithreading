void main() throws Exception {
    long start = System.nanoTime();
    Callable<Long> fareQuote = () -> {
        long sums = 0;
        while (System.nanoTime() - start < 1_000_000_000L) sums++;   // a second of work, never checks for a tap
        IO.println(at(start) + "clerk: finished anyway, was I tapped? " + Thread.currentThread().isInterrupted());
        return sums;
    };
    try (ExecutorService desk = Executors.newFixedThreadPool(1)) {
        Future<Long> fare = desk.submit(fareQuote);
        Thread.sleep(100);
        IO.println(at(start) + "main: cancel returned " + fare.cancel(true) + ", state " + fare.state());
        try {
            fare.get();
        } catch (CancellationException e) {
            IO.println(at(start) + "main: get threw " + e);
        }
    }
    IO.println(at(start) + "main: the try block is over");
}

static String at(long start) {
    return String.format("%4d ms  ", (System.nanoTime() - start) / 1_000_000);
}
