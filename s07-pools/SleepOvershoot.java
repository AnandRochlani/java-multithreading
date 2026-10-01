void main() throws InterruptedException {
    long min = Long.MAX_VALUE, max = 0, sum = 0;
    for (int i = 0; i < 20; i++) {
        long start = System.nanoTime();
        Thread.sleep(100);                                    // ask for a hundred milliseconds
        long took = (System.nanoTime() - start) / 1_000;      // microseconds
        min = Math.min(min, took);
        max = Math.max(max, took);
        sum += took;
    }
    IO.println(String.format("Thread.sleep(100), 20 times: shortest %.1f ms, longest %.1f ms, average %.1f ms",
            min / 1000.0, max / 1000.0, sum / 20000.0));
}
