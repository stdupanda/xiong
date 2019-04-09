package joydata;

import org.apache.log4j.Logger;

public class Header implements BaseMessage {
    private static final Logger logger = Logger.getLogger(Header.class);

    private String startOfText = "EB";
    private String packetType = "02";
    /**
     * 信息包流水号(1 至 255)，255 的下一个流水号为 1
     */
    private String messageSerialNumber;
    /**
     * Data 字段的长度 (Maximum 5000)
     */
    private String dataLength;

    @Override
    public String genHexData() {
        logger.debug("STX:" + startOfText);
        logger.debug("PT :" + packetType);
        logger.debug("MSN:" + messageSerialNumber);
        logger.debug("L  :" + dataLength);
        String result = startOfText + packetType + messageSerialNumber + dataLength;
        logger.info("result: " + result);
        return result;
    }

    @Override
    public void parseHexData(String hexData) {
        if (10 != hexData.length()) {
            logger.error("data length shoud be 10. " + hexData);
            return;
        }
        if (!"EB".equals(hexData.substring(0, 2))) {
            logger.error("data STX error!");
            return;
        }
        packetType = hexData.substring(2, 4);
        messageSerialNumber = hexData.substring(4, 6);
        dataLength = hexData.substring(6, 10);
    }

    //------
    public String getMessageSerialNumber() {
        return messageSerialNumber;
    }

    public void setMessageSerialNumber(String messageSerialNumber) {
        this.messageSerialNumber = messageSerialNumber;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }
}
