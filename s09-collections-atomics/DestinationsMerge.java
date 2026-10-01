static final int CLERKS = 10;
static final int PER_CLERK = 10_000;
static final String[] TRAINS = {"northbound", "southbound", "eastbound", "westbound"};
static final Map<String, Integer> perTrain = new ConcurrentHashMap<>();   // thread-safe: every call on it is safe

void main() throws Exception {
    List<Callable<Integer>> clerks = new ArrayList<>();
    for (int i = 1; i <= CLERKS; i++) {
        clerks.add(() -> {
            for (int b = 0; b < PER_CLERK; b++) {
                String train = TRAINS[b % TRAINS.length];          // a quarter of the bookings for each train
                perTrain.merge(train, 1, Integer::sum);            // read, add one and write, as ONE call
            }
            return PER_CLERK;
        });
    }
    try (ExecutorService desk = Executors.newFixedThreadPool(CLERKS)) {
        desk.invokeAll(clerks);
    }
    int total = 0;
    for (String train : TRAINS) {
        IO.println(String.format("%-10s : %,6d", train, perTrain.get(train)));
        total += perTrain.get(train);
    }
    IO.println(String.format("counted    : %,d of %,d bookings", total, CLERKS * PER_CLERK));
}
