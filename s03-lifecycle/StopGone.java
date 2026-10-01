void main() {
    Thread tom = new Thread(() -> IO.println("Tom is selling tickets"), "Tom");
    tom.start();
    tom.stop();                    // the old stop button
}
