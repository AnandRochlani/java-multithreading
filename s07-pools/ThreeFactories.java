static void show(String label, ExecutorService desk) {
    List<String> names = Collections.synchronizedList(new ArrayList<>());
    try (desk) {                                              // close(): shut down, then wait
        for (int n = 1; n <= 6; n++) {
            desk.execute(() -> {
                nap(100);
                names.add(Thread.currentThread().getName());
            });
        }
    }
    IO.println(label + " " + names);
}

void main() {
    IO.println("cores on this machine: " + Runtime.getRuntime().availableProcessors());
    show("fixed(4): ", Executors.newFixedThreadPool(4));
    show("single:   ", Executors.newSingleThreadExecutor());
    show("cached:   ", Executors.newCachedThreadPool());
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
