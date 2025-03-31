package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;

public class ExecutorShutdownMain {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("longTask", 100_000));
        printState(es);
        log("== shutdown 시작");
        shutdownAndAwaitTermination(es);
        log("== shutdown 완료");
    }

    private static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown(); // non-blocking, 새로운 작업을 받지 않는다. 처리 중이거나 대기 중인 작업은 모두 처리한다.
        try {
            // 이미 대기 중인 작업을 모두 완료할 때 까지 기다린다. (블로킹)
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                log("서비스 정상 종료 실패 -> 강제 종료 시도");
                es.shutdownNow();
                // 작업이 취소될 때 까지 기다린다.
                if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                    log("서비스가 종료되지 않았습니다.");
                }
            }
        } catch (InterruptedException e) {
            // awaitTermination()으로 대기 중인 현재 스레드가 인터럽트될 수 있다.
            es.shutdownNow();
        }
    }
}
