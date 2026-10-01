// A million forks read one fare table (java --enable-preview). Usage: ... MillionReaders.java none | scoped | local | inheritable
import java.lang.management.ManagementFactory;

record FareTable(byte[] rows) {}                                          // one kilobyte of fares, never changed

static final AtomicInteger tablesMade = new AtomicInteger();
static FareTable newTable() { tablesMade.incrementAndGet(); return new FareTable(new byte[1024]); }

static final ScopedValue<FareTable> FARES = ScopedValue.newInstance();                     // bound once, read by all
static final ThreadLocal<FareTable> MY_FARES = ThreadLocal.withInitial(() -> newTable());  // a copy per thread
static final InheritableThreadLocal<FareTable> INHERITED = new InheritableThreadLocal<>(); // set once, handed down

void main(String[] args) throws Exception {
    String mode = args[0];
    FareTable table = newTable();                                         // the one table main makes
    if (mode.equals("inheritable")) INHERITED.set(table);                 // handed down to every thread main starts
    long start = System.nanoTime();
    long heap = ScopedValue.where(FARES, table).call(() -> {
        try (var rushHour = StructuredTaskScope.open()) {
            for (int i = 0; i < 1_000_000; i++) {
                rushHour.fork(() -> {
                    FareTable fares = switch (mode) {
                        case "scoped"      -> FARES.get();
                        case "local"       -> MY_FARES.get();
                        case "inheritable" -> INHERITED.get();
                        default            -> null;                      // "none": reads nothing
                    };
                    if (fares != null && fares.rows()[0] != 0) IO.println("never printed");
                    Thread.sleep(2_000);                                  // every fork stays alive, asleep, for a while
                    return null;
                });
            }
            IO.println(mode + ": forked 1000000 after " + (System.nanoTime() - start) / 1_000_000 + " ms");
            Thread.sleep(1_000);                                          // every fork is now asleep
            System.gc();
            long used = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() >> 20;
            rushHour.join();
            return used;
        }
    });
    IO.println(mode + ": heap in use after a GC while they slept: " + heap + " MB, fare tables made: " + tablesMade.get()
            + ", " + (System.nanoTime() - start) / 1_000_000 + " ms");
}
