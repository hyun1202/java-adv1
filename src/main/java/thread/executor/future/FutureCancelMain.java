package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class FutureCancelMain {
    // 실행 중인 작업을 중단하고 예외를 발생시킨다.
//    private static final boolean mayInterruptIfRunning = true;
    // 실행 중인 작업을 중단하지는 않는다.
    private static final boolean mayInterruptIfRunning = false;

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<String> future1 = es.submit(new MyTask());
        log("Future.state: " + future1.state());

        // 일정 시간 후 취소 시도
        sleep(3000);

        // cancel() 호출
        log("future.cancel(" + mayInterruptIfRunning + ") 호출");
        boolean cancelResult = future1.cancel(mayInterruptIfRunning);
        log("cancel(" + mayInterruptIfRunning + ") result: " + cancelResult);

        // 결과 확인
        try {
            log("Future result: " + future1.get());
        }
        catch (CancellationException e) {
            log("Future는 이미 취소 되었습니다.");
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        es.close();
    }

    static class MyTask implements Callable<String> {

        @Override
        public String call() {
            try {
                for (int i = 0; i < 10; i++) {
                    log("작업 중" + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                log("인터럽트 발생");
                return "Interrupted";
            }

            return "Completed";
        }
    }
}