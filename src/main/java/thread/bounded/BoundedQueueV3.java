package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BoundedQueueV3 implements BoundedQueue {
    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV3(int max) {
        this.max = max;
    }

    // dead lock 발생.
    // 생산자가 먼저 실행되었을 때 락을 반납하려면 c1이 먼저 큐의 데이터를 소비해야하지만
    // 이미 생산자가 락을 가지고 임계 영역에 있기 때문에 무한 대기 발생

    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            log("[put] 큐가 가득 참, 생산자 대기: " + data);
            try {
                wait(); // RUNNABLE -> WAITING, 락 반납
                log("[put] 생산자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.offer(data);
        log("[put] 생산자 데이터 저장, notify() 호출");
        notify();   // 대기 스레드, WAIT -> BLOCKED
    }

    // 소비자가 먼저 실행되었을 때 락을 반납하려면 생산자가 먼저 큐에 데이터를 입력해야하지만
    // 이미 소비자가 락을 가지고 임계 영역에 있끼 때문에 무한 대기 발생
    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log("[take] 큐에 데이터가 없음, 소비자 대기");
            try {
                wait();
                log("[take] 소비자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        String data = queue.poll();
        log("[take] 소비자 데이터 획득, notify() 호출");
        notify();   // 대기 스레드, WAIT -> BLOCKED
        return data;
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
