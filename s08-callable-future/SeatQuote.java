void main() throws Exception {
    Callable<String> seatFinder = () -> {
        Thread.sleep(300);                                        // the seat system: 300 ms
        return "S7-42";
    };
    Callable<Integer> fareQuote = () -> {
        Thread.sleep(200);                                        // the fare system: 200 ms
        return 1250;
    };
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newFixedThreadPool(2)) {   // two clerks at the enquiry windows
        Future<String> seat = desk.submit(seatFinder);            // form one in, slip one back
        Future<Integer> fare = desk.submit(fareQuote);            // form two in, slip two back
        IO.println("both forms handed in after " + ms(start) + " ms");
        String berth = seat.get();                                // present slip one
        int price = fare.get();                                   // present slip two
        IO.println("berth " + berth + ", fare " + price + ", after " + ms(start) + " ms");
    }
}

static long ms(long start) {
    return (System.nanoTime() - start) / 1_000_000;
}
