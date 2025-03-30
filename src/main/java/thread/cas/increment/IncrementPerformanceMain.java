package thread.cas.increment;

public class IncrementPerformanceMain {
    public static final long COUNT = 100_000_000;

    /*
        BasicInteger: ms=4
        VolatileInteger: ms=177
        SyncInteger: ms=329
        MyAtomicInteger: ms=186
     */
    public static void main(String[] args) {
        // 가장 빠르다, CPU 캐시를 적극 사용, 멀티스레드 상황에선 사용 불가
        test(new BasicInteger());
        // CPU 캐시를 사용하지 않고 메인 메모리 사용, 멀티스레드 상황에선 사용 불가
        test(new VolatileInteger());
        // syncronized를 사용한 안전한 임계 영역이 있으므로 멀티스레드 상황에서 사용 가능, Atomic보다 성능이 느림
        test(new SyncInteger());
        // 자바에서 제공하는 AtomicInteger 사용, 멀티 스레드 상황에서 사용 가능
        // 성능도 synchornized, lock을 사용하는 경우보다 1.5 ~ 2배정도 빠름
        test(new MyAtomicInteger());
    }

    private static void test(IncrementInteger incrementInteger) {
        long startMs = System.currentTimeMillis();
        for (int i=0; i< COUNT; i++) {
            incrementInteger.increment();
        }
        long endMs = System.currentTimeMillis();
        System.out.println(incrementInteger.getClass().getSimpleName() + ": ms=" + (endMs - startMs));
    }
}
