package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV3 {
    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");

        thread.start();

        sleep(100);
        // 변수 사용 => 특정 스레드의 작업을 중단하는 가장 쉬운 방법
        log("작업 중단 지시 thread.interrupt()");
        thread.interrupt();
        log("work 스레드 인터럽트 상태1 = " + thread.isInterrupted());
    }

    static class MyTask implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) { // 인터럽트 상태는 변하지 않는다.
                log("작업 중");
            }
            // TIMED_WAITING -> RUNNABLE 변경
            log("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted());
            log("state= " + Thread.currentThread().getState());
            try {
                log("자원 정리");
                Thread.sleep(1000);
                log("자원 종료");
            } catch (InterruptedException e) {
                // 만약 자원정리를 하던 도중 인터럽트가 발생할 수 있는 로직이 있다면 반드시 예외가 발생하게 된다.
                // 위와 같은 이유로 인터럽트가 발생하면 인터럽트 상태를 다시 false로 돌리는 것
                log("자원 정리 실패 - 자원 정리 중 인터럽트 발생");
                log("work 스레드 인터럽트 상태3 = " + Thread.currentThread().isInterrupted());
            }
        }
    }
}
