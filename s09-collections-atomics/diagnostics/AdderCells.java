// Looks inside LongAdder with reflection, so run it with:
//   java --add-opens java.base/java.util.concurrent.atomic=ALL-UNNAMED AdderCells.java
// A million increments shared by 10, 100 and 1,000 clerks: how many cells does the adder end up with?
void main() throws Exception {
    Field cellsField = Class.forName("java.util.concurrent.atomic.Striped64").getDeclaredField("cells");
    cellsField.setAccessible(true);
    for (int clerks : new int[]{10, 100, 1_000}) {
        LongAdder ticketsSold = new LongAdder();
        int perClerk = 1_000_000 / clerks;
        Thread[] staff = new Thread[clerks];
        for (int i = 0; i < clerks; i++) {
            staff[i] = new Thread(() -> { for (int t = 0; t < perClerk; t++) ticketsSold.increment(); });
            staff[i].start();
        }
        for (Thread clerk : staff) clerk.join();
        Object[] cells = (Object[]) cellsField.get(ticketsSold);
        int inUse = 0;
        if (cells != null) for (Object cell : cells) if (cell != null) inUse++;
        IO.println(String.format("%,5d clerks: sum %,d, cells %d (in use %d), cores %d", clerks, ticketsSold.sum(),
                cells == null ? 0 : cells.length, inUse, Runtime.getRuntime().availableProcessors()));
    }
}
