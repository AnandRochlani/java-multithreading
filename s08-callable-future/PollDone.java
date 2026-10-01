import java.lang.management.*;

void main() throws Exception {
    ThreadMXBean meter = ManagementFactory.getThreadMXBean();
    Callable<String> seatFinder = () -> {
        Thread.sleep(300);                                        // the seat system: 300 ms
        return "S7-42";
    };
    try (ExecutorService desk = Executors.newFixedThreadPool(1)) {
        Future<String> seat = desk.submit(seatFinder);
        long wall = System.nanoTime(), cpu = meter.getCurrentThreadCpuTime(), checks = 0;
        while (!seat.isDone()) checks++;                          // are we there yet? are we there yet?
        IO.println(String.format("asking isDone in a loop: %,d checks, %d ms of CPU in %d ms",
                checks, (meter.getCurrentThreadCpuTime() - cpu) / 1_000_000, (System.nanoTime() - wall) / 1_000_000));

        seat = desk.submit(seatFinder);
        wall = System.nanoTime(); cpu = meter.getCurrentThreadCpuTime();
        seat.get();                                               // present the slip and let main sleep
        IO.println(String.format("presenting the slip:      %d ms of CPU in %d ms",
                (meter.getCurrentThreadCpuTime() - cpu) / 1_000_000, (System.nanoTime() - wall) / 1_000_000));
    }
}
