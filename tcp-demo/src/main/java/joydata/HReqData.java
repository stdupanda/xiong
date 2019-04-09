package joydata;

import org.apache.log4j.Logger;

public class HReqData extends ReqData {
    private static final Logger logger = Logger.getLogger(HReqData.class);

    public HReqData() {
        dataNum = "01";
    }

    @Override
    public String genHexData() {
        logger.debug("dataNum         : " + dataNum);
        logger.debug("serverIdentifier: " + serverIdentifier);
        String result = dataNum + serverIdentifier;
        logger.info("result: " + result);
        return result;
    }

    @Override
    public void parseHexData(String hexData) {
        logger.error("not supported!");
    }
}
