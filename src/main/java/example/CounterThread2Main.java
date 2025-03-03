package example;

import static util.MyLogger.log;

public class CounterThread2Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new CountRunnable(), "counter");
        thread.start();
    }

    static class CountRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                log("value: " + (i + 1));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
