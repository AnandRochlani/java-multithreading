void main() throws InterruptedException {
    Thread one = Thread.startVirtualThread(() -> report("startVirtualThread"));             // way 1: start one now
    Thread two = Thread.ofVirtual().name("clerk-", 1).start(() -> report("ofVirtual"));     // way 2: the builder, with a name
    one.join();
    two.join();                                                                              // join, as always
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {             // way 3: a new one per card
        desk.execute(() -> report("the executor"));
    }
}

static void report(String how) {
    Thread me = Thread.currentThread();
    IO.println(how + ": " + me + "  isVirtual " + me.isVirtual() + ", name \"" + me.getName() + "\"");
}
