void main() throws Exception {
    Callable<String> slowCard = () -> { Thread.sleep(300); return "S7-42"; };
    Callable<String> quickCard = () -> "S7-42";
    Callable<String> tornCard = () -> { throw new IllegalStateException("card 2 is torn"); };
    try (ExecutorService desk = Executors.newFixedThreadPool(4)) {
        Future<String> running = desk.submit(slowCard);
        Future<String> served = desk.submit(quickCard);
        Future<String> torn = desk.submit(tornCard);
        Future<String> cancelled = desk.submit(slowCard);
        cancelled.cancel(true);                                   // tear this one up straight away
        Thread.sleep(50);                                         // give the quick cards a moment
        for (Future<String> slip : List.of(running, served, torn, cancelled)) {
            String inside = switch (slip.state()) {               // Java 19: ask the slip, never wait
                case RUNNING   -> "nothing yet";
                case SUCCESS   -> "answer " + slip.resultNow();
                case FAILED    -> "exception " + slip.exceptionNow();
                case CANCELLED -> "torn up";
            };
            IO.println(String.format("%-9s  isDone %-5b  %s", slip.state(), slip.isDone(), inside));
        }
        try {
            running.resultNow();                                  // grab an answer that is not there yet
        } catch (IllegalStateException e) {
            IO.println("resultNow too early: " + e);
        }
    }
}
