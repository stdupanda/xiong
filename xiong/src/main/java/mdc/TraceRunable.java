package mdc;

import org.slf4j.MDC;

import java.util.Map;

/**
 * Runnable that passes {@code org.slf4j.MDC} between threads. This can be used to avoid
 * loosing MDC context info when a new {@code java.lang.Runnable} instance is submitted into a threadpool.
 * <p/>
 * <p><b>Sample Usage.</b> Here is a simple demo:
 * <pre> {@code
 * TraceRunable task = new TraceRunable(new Runnable() {
 *     @Override
 *     public void run() {
 *         // do something.
 *     }
 * }, org.slf4j.MDC.getCopyOfContextMap());
 * ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));
 * executor.submit(task);// 这样任务执行时即可保留原有 MDC 上下文信息
 * }</pre>
 */
public class TraceRunable implements Runnable {

    private Runnable delegate;
    private Map contextMap;

    /**
     * 初始化不丢失 MDC 上下文信息的线程。<p/>
     * <b>默认使用当前线程的 MDC 上下文</b>
     *
     * @param delegate 目标线程
     */
    public TraceRunable(Runnable delegate) {
        this.delegate = delegate;
        this.contextMap = MDC.getCopyOfContextMap();
    }

    /**
     * 初始化不丢失 MDC 上下文信息的线程
     *
     * @param delegate   目标线程
     * @param contextMap 需要传递的 MDC 上下文
     */
    public TraceRunable(Runnable delegate, Map contextMap) {
        this.delegate = delegate;
        this.contextMap = contextMap;
    }

    @Override
    public void run() {
        Map prev = MDC.getCopyOfContextMap();
        if (null != contextMap) {
            MDC.setContextMap(contextMap);
        }

        try {
            this.delegate.run();
        } finally {
            if (null != prev) {
                MDC.clear();
                MDC.setContextMap(prev);// 恢复将当前线程池worker线程的 MDC 上下文
            }
        }
    }
}
