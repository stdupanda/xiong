package com.bs.web.all.webservice.util;

import org.apache.axis.message.SOAPHeaderElement;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.SOAPException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThirdCloudCardApiUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdCloudCardApiUtil.class);
    /**
     * yyyyMMddHHmmss 20171109154344
     */
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// 20171109154344
    /**
     * yyyy-MM-dd HH:mm:ss 2017-11-09 15:43:44
     */
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 2017-11-09 15:43:44

    /**
     * 获取axis请求形式的加密头
     *
     * @return SOAPHeaderElement
     */
    public static SOAPHeaderElement genSoapHeader() {

        // 下面开始添加认证头
        SOAPHeaderElement head = new SOAPHeaderElement("http://tempuri.org/", "CredentialSoapHeader");
        head.setNamespaceURI("http://tempuri.org/");
        try {
            String ID = "";//BusConfig.getBusWebserviceId();
            String AppTypeID = "";//BusConfig.getBusWebserviceAppTypeId();
            String IP = "";//BusConfig.getBusWebserviceIp();
            Date date = new Date();
            String str = sdf.format(date);
            String str2 = sdf2.format(date).replace(" ", "T");
            String Random = "c00475ae-c776-4801-9ddb-c28d1c9e676d";
            String VER = "";//BusConfig.getBusWebserviceVer();
            String KEY = "";//BusConfig.getBusWebserviceKey();
            String Hash = genHash(ID, str, Random, KEY);
            String CustomerUnitCode = "08600000000";
            head.addChildElement("ID").setValue(ID);
            head.addChildElement("AppTypeID").setValue(AppTypeID);
            head.addChildElement("IP").setValue(IP);
            head.addChildElement("Timestamp").setValue(str2);
            head.addChildElement("Random").setValue(Random);
            head.addChildElement("Hash").setValue(Hash);
            head.addChildElement("VER").setValue(VER);
            head.addChildElement("KEY").setValue(KEY);
            head.addChildElement("CustomerUnitCode").setValue(CustomerUnitCode);

            head.setPrefix("");
            head.setActor(null);
            // head.setMustUnderstand(true);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        return head;
    }

    // yyyyMMddHHmmss
    private static String genHash(String id, String timeStr, String random, String key) {
        try {
            byte[] bytesId;
            bytesId = id.getBytes("utf-8");
            byte[] bytesTimeStr = timeStr.getBytes();
            byte[] bytesRandom = random.getBytes();
            byte[] bytesKey = key.getBytes();
            int length = bytesId.length + bytesTimeStr.length + bytesRandom.length + bytesKey.length;
            int offset = 0;
            byte[] hashArr = new byte[length];
            // System.arraycopy(src, srcPos, dest, destPos, length);

            System.arraycopy(bytesId, 0, hashArr, offset, bytesId.length);
            offset += bytesId.length;

            System.arraycopy(bytesTimeStr, 0, hashArr, offset, bytesTimeStr.length);
            offset += bytesTimeStr.length;

            System.arraycopy(bytesRandom, 0, hashArr, offset, bytesRandom.length);
            offset += bytesRandom.length;

            System.arraycopy(bytesKey, 0, hashArr, offset, bytesKey.length);
            offset += bytesKey.length;

            byte[] b64 = DigestUtils.md5(hashArr);
            byte[] encodeBase64 = Base64.encodeBase64(b64);
            String hash = new String(encodeBase64);
            LOGGER.debug("{id:{}, timeStr:{}, random:{}, hash:{}}", id, timeStr, random, hash);
            return hash;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
