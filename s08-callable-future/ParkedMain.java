void main() throws Exception {
    Thread main = Thread.currentThread();
    Callable<String> seatFinder = () -> {
        Thread.sleep(300);                                        // the seat system: 300 ms
        return "S7-42";
    };
    try (ExecutorService desk = Executors.newFixedThreadPool(2)) {
        Future<String> seat = desk.submit(seatFinder);
        desk.execute(() -> {                                      // the second clerk glances at main
            nap(100);
            IO.println("100 ms in, main is " + main.getState());
            for (StackTraceElement frame : main.getStackTrace()) {
                if (frame.getClassName().contains("FutureTask")) IO.println("    at " + frame);
            }
        });
        String berth = seat.get();                                // main presents the slip and waits
        IO.println("main got " + berth);
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
