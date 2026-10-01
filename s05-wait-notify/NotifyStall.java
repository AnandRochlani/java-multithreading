static final Queue<String> tray = new ArrayDeque<>();
static final int CAPACITY = 1;
static final int SLIPS = 6;
static int taken = 0;                                         // guarded by the tray's key

static void print(int n) throws InterruptedException {       // Ravi's side of the hatch
    synchronized (tray) {
        while (tray.size() == CAPACITY) {
            IO.println("printer: tray full, waiting");
            tray.wait();
        }
        tray.add("slip #" + n);
        IO.println("printer: printed slip #" + n + "   (tray " + tray.size() + ")");
        tray.notify();                                        // notify, not notifyAll
    }
}

static String collect() throws InterruptedException {        // the clerk's side of the hatch
    synchronized (tray) {
        while (tray.isEmpty()) {
            IO.println(Thread.currentThread().getName() + ": tray empty, waiting");
            tray.wait();
        }
        String slip = tray.remove();
        taken++;
        IO.println(Thread.currentThread().getName() + ": took " + slip + "   (tray " + tray.size() + ")");
        tray.notify();                                        // notify, not notifyAll
        return slip;
    }
}

void main() throws InterruptedException {
    Thread ravi = new Thread(() -> {
        try {
            for (int n = 1; n <= SLIPS; n++) {
                print(n);                                     // no naps: everyone runs flat out
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }, "printer");
    Runnable clerk = () -> {
        try {
            for (int n = 1; n <= SLIPS / 2; n++) {           // two clerks, three slips each
                collect();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    };
    Thread meena = new Thread(clerk, "Meena");
    Thread tom   = new Thread(clerk, "Tom");
    List<Thread> staff = List.of(ravi, meena, tom);

    for (Thread t : staff) {
        t.setDaemon(true);                                    // the JVM will not wait for daemons
        t.start();
    }
    for (Thread t : staff) {
        t.join(Duration.ofMillis(500));                       // wait for each one, but never forever
    }
    synchronized (tray) {
        IO.println("shutters down: " + taken + " of " + SLIPS + " slips taken, tray = " + tray);
    }
    for (Thread t : staff) {
        IO.println(t.getName() + ": " + t.getState());
    }
}
