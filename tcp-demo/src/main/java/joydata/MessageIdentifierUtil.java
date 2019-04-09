package joydata;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicLong;

public class MessageIdentifierUtil {
    private static final Logger logger = Logger.getLogger(MessageIdentifierUtil.class);
    private static final AtomicLong atomicLong = new AtomicLong();

    public static String getStr() {
        int i = get();
        String str = String.format("%02X", i);
        logger.debug("num: " + str);
        return str;
    }

    private static int get() {
        long t = atomicLong.get();
        if (255 < t) {
            atomicLong.set(0);
        }
        int num = (int) atomicLong.getAndIncrement();
        logger.debug("num: " + num);
        return num;
    }
}
