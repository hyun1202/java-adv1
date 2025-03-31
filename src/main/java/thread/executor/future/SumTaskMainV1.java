package thread.executor.future;

import thread.control.join.JoinMainV1;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class SumTaskMainV1 {
    public static void main(String[] args) throws InterruptedException {
        log("Start");
        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);
        Thread thread = new Thread(task1, "thread-1");
        Thread thread2 = new Thread(task2, "thread-2");

        thread.start();
        thread2.start();

        thread.join();
        thread2.join();

        // thread 종료 전 메인스레드가 해당 코드를 실행했기에, 계산 전 데이터를 조회하기 때문
        log("task1.result = " + task1.result); // 0
        log("task2.result = " + task2.result); // 0

        int sumAll = task1.result + task2.result; // 0
        log("task1 + task2 = " + sumAll);
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
