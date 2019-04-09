package joydata;

import org.apache.log4j.Logger;

public class HRespData extends RespData {
    private static final Logger logger = Logger.getLogger(HRespData.class);

    /**
     * 设备状态：00正常，01故障
     */
    private String deviceStatus;
    /**
     * 工作状态：00正常模式（授权通行），01常开模式（自由通行）
     */
    private String workingMode;
    /**
     * 门物理状态：00 故障，01 打开状态，02 关闭状态
     */
    private String gatePhysicalStatus;

    public HRespData() {
        dataNum = "01";
    }

    @Override
    public String genHexData() {
        logger.error("not supported");
        return null;
    }

    @Override
    public void parseHexData(String hexData) {
        if (8 != hexData.length()) {
            logger.error("data length error! " + hexData);
        }
        dataNum = hexData.substring(0, 2);
        deviceStatus = hexData.substring(2, 4);
        workingMode = hexData.substring(4, 6);
        gatePhysicalStatus = hexData.substring(6, 8);
    }

    @Override
    public String toString() {
        return "HRespData [deviceStatus=" + deviceStatus + ", workingMode=" + workingMode
                + ", gatePhysicalStatus=" + gatePhysicalStatus + ", dataNum=" + dataNum + "]";
    }
}
