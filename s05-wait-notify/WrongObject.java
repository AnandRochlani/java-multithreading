void main() throws InterruptedException {
    Object cashDrawer = new Object();
    Object ledger = new Object();
    synchronized (cashDrawer) {
        ledger.wait();                  // holding the cash key, waiting in the ledger's room
    }
}
