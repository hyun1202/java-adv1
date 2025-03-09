package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV2 {
    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");

        thread.start();

        sleep(4000);
        // 변수 사용 => 특정 스레드의 작업을 중단하는 가장 쉬운 방법
        log("작업 중단 지시 thread.interrupt()");
        thread.interrupt();
        log("work 스레드 인터럽트 상태1 = " + thread.isInterrupted());
    }

    static class MyTask implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    log("작업 중");
                    // interrupt()를 호출했다고 해서 즉각 InterruptedException이 발생하는 것이 아님
                    // sleep()과 InterruptedException을 던지는 메서드를 호출하거나 호출 중일 때 예외 발생함
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                // TIMED_WAITING -> RUNNABLE 변경
                log("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted());
                log("interrupt message= " + e.getMessage());
                log("state= " + Thread.currentThread().getState());
            }
            log("자원 정리");
            log("자원 종료");
        }
    }
}
