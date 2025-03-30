package thread.spinlock;

import java.util.concurrent.atomic.AtomicBoolean;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class SpinLock {
    private AtomicBoolean lock = new AtomicBoolean(false);

    public void lock() {
        log("락 획득 시도");
        // 락을 획득하면 lock의 값이 true가 된다.
        while(!lock.compareAndSet(false, true)) {
            log("락 획득 실패 - 스핀 대기");
        }
        log("락 획득 완료");
    }

    public void unlock() {
        lock.set(false);
        log("락 반납 완료");
    }
}
