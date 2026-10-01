import java.lang.management.*;

static final class Clerk {
    final String name;
    Clerk other;
    volatile boolean hasPassenger = true;                     // the queues are long: nobody ever runs out
    volatile long handovers = 0;                              // written by this clerk's own thread only
    volatile long stamped = 0;
    Clerk(String name) { this.name = name; }
}

static volatile Clerk stamp;                                  // who holds the station's one rubber stamp

static void work(Clerk me) {
    while (true) {
        if (stamp != me) {                                    // not holding the stamp: wait for it
            Thread.onSpinWait();
            continue;
        }
        if (me.other.hasPassenger) {                          // the other clerk has a passenger too
            stamp = me.other;                                 // so, politely: "after you"
            me.handovers++;
        } else {
            me.stamped++;                                     // nobody else waiting: stamp my own ticket
        }
    }
}

void main() throws InterruptedException {
    Clerk meena = new Clerk("Meena"), tom = new Clerk("Tom");
    meena.other = tom;
    tom.other = meena;
    stamp = meena;
    Thread m = new Thread(() -> work(meena), "Meena");
    Thread t = new Thread(() -> work(tom), "Tom");
    m.setDaemon(true);                                        // so the station can still close tonight
    t.setDaemon(true);
    m.start();
    t.start();
    Thread.sleep(1000);

    ThreadMXBean cpu = ManagementFactory.getThreadMXBean();
    IO.println("after one second: Meena is " + m.getState() + ", Tom is " + t.getState());
    IO.println("CPU used: Meena " + cpu.getThreadCpuTime(m.threadId()) / 1_000_000 + " ms, Tom "
            + cpu.getThreadCpuTime(t.threadId()) / 1_000_000 + " ms");
    IO.println("the stamp changed hands " + (meena.handovers + tom.handovers) + " times");
    IO.println("tickets stamped: " + (meena.stamped + tom.stamped));
}
