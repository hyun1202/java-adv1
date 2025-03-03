package example;

import static util.MyLogger.log;

public class CounterThread4Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new PrintWork("A", 1000), "Thread-A");
        Thread thread2 = new Thread(new PrintWork("B", 500), "Thread-B");

        thread.start();
        thread2.start();
    }

    static class PrintWork implements Runnable {

        private String content;
        private int sleepMs;

        public PrintWork(String content, int sleepMs) {
            this.content = content;
            this.sleepMs = sleepMs;
        }

        @Override
        public void run() {
            while (true) {
                log(content);
                try {
                    Thread.sleep(sleepMs);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
