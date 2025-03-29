package thread.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankAccountV5 implements BankAccount {
    private int balance;
    private final Lock lock = new ReentrantLock();

    public BankAccountV5(int balance) {
        this.balance = balance;
    }

    // 동기화된 메서드: 한 번에 하나의 스레드만 실행 가능
    @Override
    public boolean withdraw(int amount) {
        log("거래 시작: " + getClass().getSimpleName());

        // 기다리지 않고 바로 나가버림
        if (!lock.tryLock()) {
            log("[진입 실패] 이미 처리 중인 작업이 있습니다.");
            return false;
        }
        // 잔고가 출금액 보다 적으면, 진행하면 안됨
        // 안전한 임계 영역 지정
        try {
            log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
            if (balance < amount) {
                log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
                if (balance < amount) {
                    return false;
                }
            }
            // 잔고가 출금액 보다 많으면 진행
            log("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
            sleep(1000);
            balance = balance - amount;
            log("[출금 완료] 출금액: " + amount + ", 잔액: " + balance);
        } finally {
            // 반드시 lock을 해제해주어야 한다.
            lock.unlock();
        }
        log("거래 종료");
        return true;
    }

    @Override
    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
