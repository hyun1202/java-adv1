package thread.control;

import thread.HelloRunnable;

import static util.MyLogger.log;

public class ThreadInfoMain {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        log("mainThread = " + mainThread);
        // 스레드의 고유 식별자, jvm내에서 유일함 스레드가 생성될 때 지정되며 직접 지정 불가
        log("mainThread.threadId() = " + mainThread.threadId());
        log("mainThread.getName() = " + mainThread.getName());
        // 스케줄러가 어떤 스레드를 우선 실행할지 결정하는 데에 사용함
        // 실제 실행 순서는 jvm, 운영체제에 따라 달라질 수 있음
        log("mainThread.getPriority() = " + mainThread.getPriority());
        // 스레드를 그룹화하여 관리(일괄 종료, 우선순위 설정 등), 기본적으로 부모 스레드와 동일한 그룹에 속한다.
        log("mainThread.getThreadGroup() = " + mainThread.getThreadGroup());
        // 스레드의 현재 상태를 반환
        // new: 스레드가 시작되지 않음
        // runnable: 스레드가 실행 중이거나 실행될 준비가 됨
        // blocked: 스레드가 동기화 락을 기다리는 상태
        // waiting: 스레드가 다른 스레드의 특정 작업이 완료되기를 기다리는 상태
        // timed_waiting: 일정 시간 동안 기다리는 상태 (ex Thread.sleep)
        // terminated: 스레드가 실행을 마침
        log("mainThread.getState() = " + mainThread.getState());

        Thread myThread = new Thread(new HelloRunnable(), "myThread");
        log("myThread = " + myThread);
        log("myThread.threadId() = " + myThread.threadId());
        log("myThread.getName() = " + myThread.getName());
        log("myThread.getPriority() = " + myThread.getPriority());
        log("myThread.getThreadGroup() = " + myThread.getThreadGroup());
        log("myThread.getState() = " + myThread.getState());
    }
}
