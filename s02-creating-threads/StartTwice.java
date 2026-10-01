void main() {
    Thread tom = new Thread(() -> IO.println("Tom opens window 2"), "Tom");
    tom.start();
    tom.start();                 // the same clerk, sent to the window a second time
}
