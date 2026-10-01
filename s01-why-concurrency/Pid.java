void main() {
    IO.println("process id : " + ProcessHandle.current().pid());
    IO.println("thread     : " + Thread.currentThread().getName());
}
