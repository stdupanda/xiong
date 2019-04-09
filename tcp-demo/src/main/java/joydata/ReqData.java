package joydata;

public abstract class ReqData extends Data {
    /**
     * 报文编号：01心跳，11入闸授权
     */
    protected String dataNum = "";
    /**
     * 外部服务器标识符  gyface，长度12
     */
    protected String serverIdentifier = "677966616365";
}
