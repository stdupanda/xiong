package joydata;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class Message implements BaseMessage {
    private static final Logger logger = Logger.getLogger(Message.class);

    private Header header;
    private Data data;
    private Tail tail;

    @Override
    public String genHexData() {
        String result = header.genHexData() + data.genHexData() + tail.genHexData();
        logger.debug("hex data: " + result);
        return result;
    }

    @Override
    public void parseHexData(String hexData) {
        int length = hexData.length();
        logger.debug("hex data length:" + length);
        if (StringUtils.isBlank(hexData) || 10 > length) {
            logger.error("data error:" + hexData);
            return;
        }
        header.parseHexData(hexData.substring(0, 10));
        String dataLengthStr = header.getDataLength();
        int dataLength = 0;
        try {
            dataLength = Integer.parseInt(dataLengthStr, 16);
        } catch (Exception e) {
            logger.error("data parse error!", e);
            return;
        }
        int num = 10 + dataLength * 2;
        if (num + 2 != length) {
            logger.error("data length error!");
            return;
        }
        data.parseHexData(hexData.substring(num, length));
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Tail getTail() {
        return tail;
    }

    public void setTail(Tail tail) {
        this.tail = tail;
    }

}
