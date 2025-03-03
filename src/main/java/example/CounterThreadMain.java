package example;

public class CounterThreadMain {
    public static void main(String[] args) {
        CounterThread thread = new CounterThread();
        thread.start();
    }
}
