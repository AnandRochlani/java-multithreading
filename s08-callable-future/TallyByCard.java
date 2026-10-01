static final int CLERKS = 10;
static final int PER_CLERK = 100_000;

void main() throws Exception {
    long start = System.currentTimeMillis();

    List<Callable<Integer>> clerks = new ArrayList<>();
    for (int i = 0; i < CLERKS; i++) {
        clerks.add(() -> {
            int sold = 0;                                         // this clerk's own tally, shared with nobody
            for (int t = 0; t < PER_CLERK; t++) {
                sold++;
            }
            return sold;                                          // hand the tally back on the slip
        });
    }
    int ticketsSold = 0;
    try (ExecutorService desk = Executors.newFixedThreadPool(CLERKS)) {
        for (Future<Integer> slip : desk.invokeAll(clerks)) {
            ticketsSold += slip.get();                            // only main ever adds, one slip at a time
        }
    }

    long elapsed = System.currentTimeMillis() - start;
    IO.println("expected : " + CLERKS * PER_CLERK);
    IO.println("register : " + ticketsSold);
    IO.println("time     : " + elapsed + " ms");
}
