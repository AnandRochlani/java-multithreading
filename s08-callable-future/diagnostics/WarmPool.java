// The claim under test: "The extra fifteen is the pool starting its two clerks, and that happens once."
// Same two lookups as SeatQuote, five rounds on ONE pool, plus the two sleeps timed alone.
void main() throws Exception {
    Callable<String> seatFinder = () -> { Thread.sleep(300); return "S7-42"; };
    Callable<Integer> fareQuote = () -> { Thread.sleep(200); return 1250; };
    for (int ms : new int[] {300, 200}) {
        double worst = 0, sum = 0;
        for (int i = 0; i < 5; i++) {
            long t = System.nanoTime();
            Thread.sleep(ms);
            double took = (System.nanoTime() - t) / 1e6;
            worst = Math.max(worst, took); sum += took;
        }
        IO.println(String.format("Thread.sleep(%d) alone, 5 times: average %.1f ms, longest %.1f ms", ms, sum / 5, worst));
    }
    long created = System.nanoTime();
    try (ExecutorService desk = Executors.newFixedThreadPool(2)) {
        IO.println(String.format("pool created in %.2f ms", (System.nanoTime() - created) / 1e6));
        for (int round = 1; round <= 5; round++) {
            long start = System.nanoTime();
            Future<String> seat = desk.submit(seatFinder);
            Future<Integer> fare = desk.submit(fareQuote);
            seat.get(); fare.get();
            IO.println(String.format("round %d (%s): %.1f ms", round, round == 1 ? "clerks hired now" : "same two clerks", (System.nanoTime() - start) / 1e6));
        }
    }
}
