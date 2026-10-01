static final int CLERKS = 10;
static final int PER_CLERK = 10_000;
static final Map<String, String> register = new ConcurrentHashMap<>();   // ONE register, every clerk writes to it

void main() throws Exception {
    List<Callable<Integer>> clerks = new ArrayList<>();
    for (int i = 1; i <= CLERKS; i++) {
        int clerk = i;
        clerks.add(() -> {
            for (int b = 1; b <= PER_CLERK; b++) {
                register.put("clerk-" + clerk + "/booking-" + b, "window-" + clerk);   // every key is different
            }
            return PER_CLERK;                                     // "I wrote all ten thousand"
        });
    }
    int claimed = 0;
    try (ExecutorService desk = Executors.newFixedThreadPool(CLERKS)) {
        for (Future<Integer> slip : desk.invokeAll(clerks)) {
            try {
                claimed += slip.get();
            } catch (ExecutionException e) {
                IO.println("a slip threw: " + e.getCause());
            }
        }
    }
    IO.println("the slips say  : " + claimed + " bookings written");
    IO.println("the register has: " + register.size() + " bookings");
}
