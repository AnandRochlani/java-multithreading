// 10,000 NAMED virtual threads asleep for 30 s, so jstack and jcmd can look at them. Prints its pid first.
void main() throws InterruptedException {
    IO.println("pid " + ProcessHandle.current().pid());
    ThreadFactory hire = Thread.ofVirtual().name("passenger-", 1).factory();
    try (ExecutorService desk = Executors.newThreadPerTaskExecutor(hire)) {
        for (int i = 0; i < 10_000; i++) desk.execute(() -> nap(30_000));
        IO.println("10000 named virtual threads asleep");
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
