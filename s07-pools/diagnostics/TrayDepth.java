void main() {
    ThreadPoolExecutor desk = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    IO.println("room left in an empty tray: " + desk.getQueue().remainingCapacity());
    IO.println("Integer.MAX_VALUE:          " + Integer.MAX_VALUE);
    desk.shutdown();
}
