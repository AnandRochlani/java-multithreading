void main() throws Exception {
    Callable<Integer> fareQuote = () -> {
        Thread.sleep(3_600_000);                                  // the fare system has gone quiet: an hour
        return 1250;
    };
    try (ExecutorService desk = Executors.newFixedThreadPool(1)) {
        Future<Integer> fare = desk.submit(fareQuote);
        IO.println("form handed in, waiting for the fare");
        int price = fare.get();                                   // no time limit on this wait
        IO.println("fare " + price);
    }
}
