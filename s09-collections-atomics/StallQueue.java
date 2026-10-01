static final BlockingQueue<String> tray = new ArrayBlockingQueue<>(1);   // Notify Stall's tray: room for ONE slip
static final int SLIPS = 6;
static final AtomicInteger taken = new AtomicInteger();

void main() throws InterruptedException {
    Thread ravi = new Thread(() -> {
        try {
            for (int n = 1; n <= SLIPS; n++) {
                tray.put("slip #" + n);                           // no naps: everyone runs flat out
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }, "printer");
    Runnable clerk = () -> {
        try {
            for (int n = 1; n <= SLIPS / 2; n++) {                // two clerks, three slips each
                tray.take();
                taken.incrementAndGet();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    };
    Thread meena = new Thread(clerk, "Meena");
    Thread tom   = new Thread(clerk, "Tom");
    List<Thread> staff = List.of(ravi, meena, tom);

    for (Thread t : staff) {
        t.setDaemon(true);                                        // the JVM will not wait for daemons
        t.start();
    }
    for (Thread t : staff) {
        t.join(Duration.ofMillis(500));                           // wait for each one, but never forever
    }
    IO.println("shutters down: " + taken.get() + " of " + SLIPS + " slips taken, tray = " + tray);
    for (Thread t : staff) {
        IO.println(t.getName() + ": " + t.getState());
    }
}
