void main() {
    ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor();   // Section 10 material
    desk.submit(() -> IO.println("task ran on [" + Thread.currentThread().getName() + "]"));
    IO.println("main is finished");
}
