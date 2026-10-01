void main() throws Exception {
    List<Callable<String>> lookups = new ArrayList<>();
    for (int n = 1; n <= 8; n++) {
        int card = n;
        lookups.add(() -> {
            Thread.sleep(100);                                    // each lookup waits 100 ms on a server
            return "lookup " + card;
        });
    }
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newFixedThreadPool(2)) {   // eight slips, two clerks
        desk.invokeAll(lookups);
    }
    IO.println("8 lookups of 100 ms on 2 clerks took " + (System.nanoTime() - start) / 1_000_000 + " ms");
}
