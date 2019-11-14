package mdc;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @see mdc.TraceRunable
 */
public class TraceCallable<V> implements Callable<V> {

    private Callable<V> delegate;
    private Map contextMap;

    /**
     * @see mdc.TraceRunable#TraceRunable(Runnable)
     */
    public TraceCallable(Callable<V> delegate) {
        this.delegate = delegate;
        this.contextMap = MDC.getCopyOfContextMap();
    }

    /**
     * @see mdc.TraceRunable#TraceRunable(Runnable, Map)
     */
    public TraceCallable(Callable<V> delegate, Map contextMap) {
        this.delegate = delegate;
        this.contextMap = contextMap;
    }

    @Override
    public V call() throws Exception {
        Map prev = MDC.getCopyOfContextMap();
        if (null != contextMap) {
            MDC.setContextMap(contextMap);
        }

        try {
            return this.delegate.call();
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != prev) {
                MDC.clear();
                MDC.setContextMap(prev);// 恢复将当前线程池 worker 的 MDC 上下文
            }
        }
    }
}
