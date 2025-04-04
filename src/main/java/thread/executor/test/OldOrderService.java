package thread.executor.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class OldOrderService {
    public void order(String orderNo) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        Callable<Boolean> inventoryWork = new InventoryWork(orderNo);
        Callable<Boolean> shippingWork = new ShippingWork(orderNo);
        Callable<Boolean> accountingWork = new AccountingWork(orderNo);
        Future<Boolean> inventoryFuture = es.submit(inventoryWork);
        Future<Boolean> shippingFuture = es.submit(shippingWork);
        Future<Boolean> accountFuture = es.submit(accountingWork);

        Boolean inventoryResult = inventoryFuture.get();
        Boolean shippingResult = shippingFuture.get();
        Boolean accountingResult = accountFuture.get();

        if (inventoryResult && shippingResult && accountingResult) {
            log("모든 주문 처리가 성공적으로 완료되었습니다.");
        } else {
            log("일부 작업이 실패했습니다.");
        }
        es.close();
    }

    static class InventoryWork implements Callable<Boolean> {
        private final String orderNo;

        public InventoryWork(String orderNo) {
            this.orderNo = orderNo;
        }

        public Boolean call() {
            log("재고 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class ShippingWork implements Callable<Boolean> {
        private final String orderNo;

        public ShippingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        public Boolean call() {
            log("배송 시스템 알림: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class AccountingWork implements Callable<Boolean> {
        private final String orderNo;

        public AccountingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        public Boolean call() {
            log("회계 시스템 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }
    }
}
