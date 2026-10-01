static final int CLERKS = 10;
static final int PER_CLERK = 10_000;
static final Map<String, String> bookings = new ConcurrentHashMap<>();   // the pigeonhole rack
static final AtomicInteger ticketsSold = new AtomicInteger();             // the turnstile

void main() throws Exception {
    List<Callable<Integer>> clerks = new ArrayList<>();
    for (int i = 1; i <= CLERKS; i++) {
        int clerk = i;
        clerks.add(() -> {
            for (int b = 1; b <= PER_CLERK; b++) {
                bookings.put("clerk-" + clerk + "/booking-" + b, "window-" + clerk);
                ticketsSold.incrementAndGet();                    // and one click of the turnstile
            }
            return PER_CLERK;
        });
    }
    try (ExecutorService desk = Executors.newFixedThreadPool(CLERKS)) {
        desk.invokeAll(clerks);
    }
    IO.println("bookings in the rack: " + bookings.size());
    IO.println("tickets sold        : " + ticketsSold.get());
}
