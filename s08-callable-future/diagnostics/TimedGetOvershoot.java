// How long does get(100 ms) really wait before it gives up? Twenty timeouts on one slip, warm.
void main() throws Exception {
    try (ExecutorService desk = Executors.newFixedThreadPool(1)) {
        Future<String> seat = desk.submit(() -> { Thread.sleep(5_000); return "S7-42"; });
        double shortest = Double.MAX_VALUE, longest = 0, sum = 0;
        for (int i = 0; i < 21; i++) {
            long t = System.nanoTime();
            try { seat.get(100, TimeUnit.MILLISECONDS); } catch (TimeoutException e) { }
            double took = (System.nanoTime() - t) / 1e6;
            if (i == 0) { IO.println(String.format("first timeout (cold): %.1f ms", took)); continue; }
            shortest = Math.min(shortest, took); longest = Math.max(longest, took); sum += took;
        }
        IO.println(String.format("next 20 timeouts: shortest %.1f ms, longest %.1f ms, average %.1f ms", shortest, longest, sum / 20));
        seat.cancel(true);
    }
}
