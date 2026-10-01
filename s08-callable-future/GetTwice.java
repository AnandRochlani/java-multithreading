void main() throws Exception {
    Callable<String> seatFinder = () -> {
        Thread.sleep(300);                                        // the seat system: 300 ms
        return "S7-42";
    };
    Callable<String> tornCard = () -> {
        throw new IllegalStateException("card 2 is torn");
    };
    try (ExecutorService desk = Executors.newFixedThreadPool(1)) {
        Future<String> seat = desk.submit(seatFinder);
        for (int i = 1; i <= 3; i++) {                            // present the same slip three times
            long start = System.nanoTime();
            String berth = seat.get();
            IO.println("get number " + i + ": " + berth + " after " + (System.nanoTime() - start) / 1_000_000 + " ms");
        }
        Future<String> torn = desk.submit(tornCard);
        ExecutionException first = null, second = null;
        try { torn.get(); } catch (ExecutionException e) { first = e; }
        try { torn.get(); } catch (ExecutionException e) { second = e; }
        IO.println("torn slip, presented twice: the same envelope? " + (first == second)
                + ", the same exception inside? " + (first.getCause() == second.getCause()));
    }
}
