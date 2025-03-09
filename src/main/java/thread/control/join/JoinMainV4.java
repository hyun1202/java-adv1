package thread.control.join;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinMainV4 {
    public static void main(String[] args) throws InterruptedException {
        log("Start");
        SumTask task1 = new SumTask(1, 50);
        Thread thread = new Thread(task1, "thread-1");

        thread.start();

        // 1초간 대기
        // 이때 WAITING이 아닌 TIMED_WAITING이 됨
        // WAITING: 무한정 기다림
        // TIMED_WAITING: 시간 만큼만 기다림
        log("join(1000) - main 스레드가 thread1 종료까지 1초 대기");
        thread.join(1000);  // WAITING이 아닌 TIMED_WAITING, 스레드가 종료되면 RUNNABLE
        log("main thread awake");

        log("task1.result = " + task1.result);
        log("End");
    }

    static class SumTask implements Runnable {
        int startValue;
        int endValue;
        int result = 0;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public void run() {
            log("작업 시작");
            sleep(2000);    // TIMED_WAITING
            int sum = 0;
            for (int i=startValue; i<=endValue; i++) {
                sum += i;
            }
            result = sum;
            log("작업 완료 result = " + result);
        }
    }
}
