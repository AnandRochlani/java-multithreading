static final int BOOKINGS = 200_000;
static final Map<String, Integer> fares = new HashMap<>();       // one writer, one reader

void main() throws Exception {
    try (ExecutorService desk = Executors.newFixedThreadPool(2)) {
        Future<?> writer = desk.submit(() -> {                        // Meena writes 200,000 fares
            for (int b = 1; b <= BOOKINGS; b++) {
                fares.put("booking-" + b, 1250);
            }
        });
        Future<Integer> reader = desk.submit(() -> {                  // Tom only reads: he adds up the fares
            int passes = 0;
            while (!writer.isDone()) {
                long total = 0;
                for (int fare : fares.values()) {
                    total += fare;
                }
                passes++;
            }
            return passes;
        });
        try {
            IO.println("reader finished after " + reader.get() + " passes");
        } catch (ExecutionException e) {
            IO.println("reader threw: " + e.getCause());
        }
    }
    IO.println("fares in the map: " + fares.size());
}
