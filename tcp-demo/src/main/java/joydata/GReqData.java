package joydata;

import org.apache.log4j.Logger;

public class GReqData extends ReqData {
    private static final Logger logger = Logger.getLogger(GReqData.class);

    /**
     * 授权开门卡信息，由调用方传入，做日志查询用。长度40
     */
    private String authedCardInfo;

    public GReqData() {
        dataNum = "11";
    }

    @Override
    public String genHexData() {
        logger.debug("dataNum         : " + dataNum);
        logger.debug("serverIdentifier: " + serverIdentifier);
        logger.debug("authedCardInfo  : " + authedCardInfo);
        String result = dataNum + serverIdentifier + authedCardInfo;
        logger.info("result: " + result);
        return result;
    }

    @Override
    public void parseHexData(String hexData) {
        logger.error("not supported!");
    }
}
