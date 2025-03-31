package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class FutureExceptionMain {
    // 실행 중인 작업을 중단하고 예외를 발생시킨다.
//    private static final boolean mayInterruptIfRunning = true;
    // 실행 중인 작업을 중단하지는 않는다.
    private static final boolean mayInterruptIfRunning = false;

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        log("작업 전달");
        Future<Integer> future1 = es.submit(new MyTask());

        sleep(1000);
        try {
            log("future.get() 호출 시도, future.state(): " + future1.state());
            Integer result = future1.get();
            log("result value = " + result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log("e = " + e);
            Throwable cause = e.getCause();
            log("cause = " + cause);
        }

        es.close();
    }

    static class MyTask implements Callable<Integer> {

        @Override
        public Integer call() {
            log("Callable 실행, 예외 발생");
            throw new IllegalStateException("ex!");
        }
    }
}