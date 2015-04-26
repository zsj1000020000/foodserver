package cn.edu.hstc.utils;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

public class Md5Encrypt {

	private static Logger logger = Logger.getLogger(Md5Encrypt.class);
	
    public static  String encrypt(String strSrc) {
        MessageDigest md = null;
        Md5Encrypt mept = new Md5Encrypt();
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(bt);
            strDes = mept.bytes2Hex(md.digest()); //to HexString
        } catch (Exception e) {
        	logger.info("Invalid algorithm.\n" + e.getMessage());
            return null;
        }
        return strDes;
    }

    private String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;

        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
