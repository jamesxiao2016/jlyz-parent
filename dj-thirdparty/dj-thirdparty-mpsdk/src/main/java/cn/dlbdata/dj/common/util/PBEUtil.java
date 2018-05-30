package cn.dlbdata.dj.common.util;

import org.jasypt.encryption.StringEncryptor;

import cn.dlbdata.dj.common.secret.EncryptorFactory;

public class PBEUtil {
    private final static StringEncryptor stringEncryptor = EncryptorFactory.newStringEncryptor();

    /**
     * 加密
     * @param var
     * @return
     */
    public static String encrypt(String var) {
        return stringEncryptor.encrypt(var);
    }

    /**
     * 解密
     * @param var
     * @return
     */
    public static String decrypt(String var) {
        return stringEncryptor.decrypt(var);
    }

    public static void main(String[] args) {
        System.out.println(PBEUtil.encrypt("71257125"));
    }
}
