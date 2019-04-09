package joydata;

import org.apache.log4j.Logger;

public class GRespData extends RespData {
    private static final Logger logger = Logger.getLogger(GRespData.class);

    /**
     * 授权结果：00 授权成功，01 授权失败
     */
    private String authResult;

    public GRespData() {
        dataNum = "11";
    }

    @Override
    public String genHexData() {
        logger.error("not supported");
        return null;
    }

    @Override
    public void parseHexData(String hexData) {
        if (4 != hexData.length()) {
            logger.error("data length error! " + hexData);
        }
        dataNum = hexData.substring(0, 2);
        authResult = hexData.substring(2, 4);
    }

    @Override
    public String toString() {
        return "GRespData [authResult=" + authResult + ", dataNum=" + dataNum + "]";
    }
}
