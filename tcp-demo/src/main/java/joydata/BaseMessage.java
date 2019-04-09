package joydata;

public interface BaseMessage {
    /**
     * 生成请求报文数据，16进制字符串
     *
     * @return 请求报文
     */
    String genHexData();

    /**
     * 解析响应报文数据，16进制字符串
     *
     * @param hexData 待解析的响应报文
     */
    void parseHexData(String hexData);
}
