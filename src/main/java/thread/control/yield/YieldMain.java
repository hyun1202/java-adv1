package thread.control.yield;

import thread.HelloRunnable;

import static util.ThreadUtils.sleep;

public class YieldMain {
    static final int THREAD_COUNT = 1000;
    public static void main(String[] args) {
        for (int i=0; i< THREAD_COUNT; i++) {
            Thread thread = new Thread(new MyRunnable());
            thread.start();
        }
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            for (int i=0; i<10; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);
                // empty -> 상대적으로 하나의 스레드가 연달아 실행되다가 다른 스레드로 넘어감
//                // sleep -> 1. 상태 변경이라는 복잡한 과정을 거침 2. 양보할 스레드가 없어도 혼자 양보한 이상한 상황이 될 수 있음
//                sleep(1); // runnable -> timed_waiting으로 변경, 실행 스케줄링에서 잠시 제외됨
                // yield -> 자바의 스레드가 runnable상태일 때 운영체제의 스케줄링은 Running, Ready와 같은 상태를 가진다.
                // 운영 체제는 Ready상태를 잠깐만 Running로 변경하여 실행하고 이 과정을 반복함 -> 자바에서는 두 상태를 구분 불가
                // yield는 현재 실행 중인 스레드가 자발적으로 CPU를 양보(할당된 실행 시간 포기)한다.
                // 운영체제에게 힌트를 제공할 뿐 반드시 다른 스레드가 실행되는 것이 아니다. -> 양보할 사람이 없다면 본인 스레드가 계속 실행될 수 있다.
                Thread.yield(); // 다른 스레드에 실행을 넘긴다.
            }
        }
    }
}
