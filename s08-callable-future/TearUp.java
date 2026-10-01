void main() throws Exception {
    long start = System.nanoTime();
    Callable<Integer> fareQuote = () -> {
        try {
            Thread.sleep(3_600_000);                              // the fare system has gone quiet
            return 1250;
        } catch (InterruptedException e) {
            IO.println(at(start) + "clerk: tapped on the shoulder, stopping");
            throw e;
        }
    };
    try (ExecutorService desk = Executors.newFixedThreadPool(1)) {
        Future<Integer> fare = desk.submit(fareQuote);
        try {
            fare.get(1, TimeUnit.SECONDS);                        // a second is all we will wait
        } catch (TimeoutException e) {
            boolean torn = fare.cancel(true);                     // true: interrupt the clerk if he has started
            IO.println(at(start) + "main: cancel returned " + torn + ", isCancelled " + fare.isCancelled()
                    + ", isDone " + fare.isDone());
        }
        try {
            fare.get();                                           // present a torn-up slip
        } catch (CancellationException e) {
            IO.println(at(start) + "main: presenting it now throws " + e);
        }
        Future<String> seat = desk.submit(() -> "S7-42");         // a slip that is already served
        seat.get();
        IO.println(at(start) + "main: cancel a finished slip? " + seat.cancel(true));
    }
}

static String at(long start) {
    return String.format("%4d ms  ", (System.nanoTime() - start) / 1_000_000);
}
