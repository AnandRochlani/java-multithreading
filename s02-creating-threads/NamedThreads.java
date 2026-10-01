void main() {
    Runnable job = () -> IO.println("I am " + Thread.currentThread().getName());

    new Thread(job, "window-1").start();              // the constructor way

    Thread.Builder clerks = Thread.ofPlatform().name("window-", 2);
    clerks.start(job);                                // the builder way, Java 21+
    clerks.start(job);
}
