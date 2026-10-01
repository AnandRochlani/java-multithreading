static int ticketsSold = 0;          // shared by every clerk: a landmine, see Section 4

record BookingClerk(String window) implements Runnable {
    @Override
    public void run() {
        for (int ticket = 1; ticket <= 3; ticket++) {
            try {
                Thread.sleep(700);                     // one ticket: check, print, take the money
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            ticketsSold++;
            IO.println(window + " sold ticket " + ticket + "   [" + Thread.currentThread().getName() + "]");
        }
    }
}

void main() {
    long start = System.currentTimeMillis();

    new Thread(new BookingClerk("window-1"), "Meena").start();
    new Thread(new BookingClerk("window-2"), "Tom").start();
    new Thread(new BookingClerk("window-3"), "Kavya").start();

    long elapsed = System.currentTimeMillis() - start;
    IO.println("counter closed: " + ticketsSold + " tickets sold today, in " + elapsed + " ms");
}
