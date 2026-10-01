void main() throws Exception {
    Callable<String> seatFinder = () -> {                         // one word changed: Callable
        Thread.sleep(300);                                        // no try, no catch, no nap helper
        return "S7-42";                                           // coach S7, berth 42
    };
    try (ExecutorService desk = Executors.newFixedThreadPool(1)) {
        Future<String> seat = desk.submit(seatFinder);            // the slip now promises a String
        String berth = seat.get();                                // present it: a String, no cast
        IO.println("berth " + berth + ", served by a card that answered");
    }
}
