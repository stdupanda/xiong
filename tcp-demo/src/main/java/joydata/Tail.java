package joydata;

import org.apache.log4j.Logger;

public class Tail implements BaseMessage {
    private static final Logger logger = Logger.getLogger(Tail.class);

    private String endOfText = "03";

    @Override
    public String genHexData() {
        return endOfText;
    }

    @Override
    public void parseHexData(String hexData) {
        //do tail parse
        if (!endOfText.equals(hexData)) {
            logger.error("data error: " + hexData);
        }
    }
}
