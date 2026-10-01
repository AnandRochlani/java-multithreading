void main() {
    try (ExecutorService desk = Executors.newFixedThreadPool(1)) {
        desk.execute(() -> {
            Thread me = Thread.currentThread();
            IO.println(me.getName() + ": daemon " + me.isDaemon() + ", priority " + me.getPriority()
                    + ", group " + me.getThreadGroup().getName());
        });
    }
    IO.println("main: daemon " + Thread.currentThread().isDaemon());
}
