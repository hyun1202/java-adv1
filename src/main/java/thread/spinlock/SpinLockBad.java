package thread.spinlock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class SpinLockBad {
    private volatile boolean lock = false;

    public void lock() {
        log("락 획득 시도");
        while(true) {
            // 락 사용 여부 확인과 락의 값 변경하는 부분이 원자적이지 않으므로 정상적으로 동작하지 않는다.
            if (!lock) { // 1. 락 사용 여부 확인
                sleep(100); // 문제 상황 확인용 스레드 대기
                lock = true;    // 2. 락 변경
                break;
            } else {
                // 락 획득 까지 스핀 대기(바쁜 대기)
                log("락 획득 실패 - 스핀 대기");
            }
        }
        log("락 획득 완료");
    }

    public void unlock() {
        // 연산이 하나인 원자적인 연산이다.
        // 따라서 멀티스레드 환경에서 문제가 없다.
        lock = false;
        log("락 반납 완료");
    }
}
