void main() {
    Runnable job = () -> {
        Thread.sleep(700);
        IO.println("ticket sold");
    };
    new Thread(job).start();
}
