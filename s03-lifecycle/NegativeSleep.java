void main() throws InterruptedException {
    long deadline = System.currentTimeMillis() - 1;          // we are already late
    Thread.sleep(deadline - System.currentTimeMillis());
}
