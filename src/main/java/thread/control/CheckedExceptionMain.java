package thread.control;

import util.ThreadUtils;

import static util.ThreadUtils.*;

public class CheckedExceptionMain {
    public static void main(String[] args) throws Exception {

    }

    static class CheckedRunnable implements Runnable {
        // 체크 예외를 던질 수 없음 - 해당 부모(인터페이스)에서 체크 예외를 던지지 않음
        // 언체크 예외 (런타임): 예외 처리를 강제하지 않으므로 상관 없이 던질 수 있음
        @Override
        public void run() /*throws exception*/ {
//            throw new Exception();
        }
    }
}
