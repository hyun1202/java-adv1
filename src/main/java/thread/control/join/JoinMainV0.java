package thread.control.join;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinMainV0 {
    public static void main(String[] args) {
        log("Start");
        Thread thread = new Thread(new Job(), "thread-1");
        Thread thread2 = new Thread(new Job(), "thread-2");

        thread.start();
        thread2.start();
        log("End");
    }

    static class Job implements Runnable {

        @Override
        public void run() {
            log("작업 시작");
            sleep(2000);    // TIMED_WAITING
            log("작업 완료");
        }
    }
}
