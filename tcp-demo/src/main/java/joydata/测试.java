package joydata;

public class 测试 {
    public static void main(String[] args) {
        for (int i = 0; i < 257; i++) {
            genHReq();
        }
    }

    static void genHReq() {
        HReqData data = new HReqData();
        String dataHex = data.genHexData();

        Header header = new Header();
        int length = dataHex.length();
        String format = String.format("%04X", length);
        header.setDataLength(format);
        header.setMessageSerialNumber(MessageIdentifierUtil.getStr());

        Message message = new Message();
        message.setHeader(header);
        message.setData(data);
        message.setTail(new Tail());
        String hexData = message.genHexData();
        System.out.println("心跳请求报文：" + hexData);
    }

    static void genGReq() {
        GReqData data = new GReqData();
        String dataHex = data.genHexData();

        Header header = new Header();
        int length = dataHex.length();
        String format = String.format("%04X", length);
        header.setDataLength(format);
        header.setMessageSerialNumber(MessageIdentifierUtil.getStr());

        Message message = new Message();
        message.setHeader(header);
        message.setData(data);
        message.setTail(new Tail());
        String hexData = message.genHexData();
        System.out.println("心跳请求报文：" + hexData);
    }
}
