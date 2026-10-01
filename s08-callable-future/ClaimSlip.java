void main() throws Exception {
    Callable<String> seatFinder = () -> {
        Thread.sleep(300);                                        // the seat system: 300 ms
        return "S7-42";
    };
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newFixedThreadPool(1)) {
        Future<String> seat = desk.submit(seatFinder);
        IO.println(at(start) + "isDone? " + seat.isDone());      // a peek: never waits
        try {
            seat.get(100, TimeUnit.MILLISECONDS);                 // wait, but only for 100 ms
        } catch (TimeoutException e) {
            IO.println(at(start) + "stopped waiting: " + e);
        }
        IO.println(at(start) + "isDone? " + seat.isDone());      // the clerk is still at it
        String berth = seat.get();                                // now wait as long as it takes
        IO.println(at(start) + "get returned " + berth + ", isDone? " + seat.isDone());
    }
}

static String at(long start) {
    return String.format("%4d ms  ", (System.nanoTime() - start) / 1_000_000);
}
