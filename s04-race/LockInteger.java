static Integer ticketsSold = 0;

void main() {
    synchronized (ticketsSold) {
        ticketsSold++;
    }
    IO.println("register says " + ticketsSold);
}
