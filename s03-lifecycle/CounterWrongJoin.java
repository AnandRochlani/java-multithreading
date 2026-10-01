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

void main() throws InterruptedException {
    long start = System.currentTimeMillis();

    Thread meena = new Thread(new BookingClerk("window-1"), "Meena");
    Thread tom   = new Thread(new BookingClerk("window-2"), "Tom");
    Thread kavya = new Thread(new BookingClerk("window-3"), "Kavya");

    meena.start();
    meena.join();
    tom.start();
    tom.join();
    kavya.start();
    kavya.join();

    long elapsed = System.currentTimeMillis() - start;
    IO.println("counter closed: " + ticketsSold + " tickets sold today, in " + elapsed + " ms");
}
