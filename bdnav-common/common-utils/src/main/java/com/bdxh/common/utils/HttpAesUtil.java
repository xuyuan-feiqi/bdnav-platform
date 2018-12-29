package com.bdxh.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * @description: AES加密工具类
 * @author: xuyuan
 * @create: 2018-12-17 14:57
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpAesUtil {

    private static final String CHAR_SET = "UTF-8";

    /**
     * 加密
     * @param contentParam 需要加密的内容
     * @param keyParam     加密密码
     * @param md5Key       是否对key进行md5加密
     * @param ivParam      加密向量16字节
     * @return 加密后的字节数据 string
     */
    public static String encrypt(String contentParam, String keyParam, boolean md5Key, String ivParam) {
        try {
            byte[] content = contentParam.getBytes(CHAR_SET);
            byte[] key = keyParam.getBytes(CHAR_SET);
            byte[] iv = ivParam.getBytes(CHAR_SET);
            if (md5Key) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                key = md.digest(key);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
            //使用CBC模式, 需要一个向量iv, 可增加加密算法的强度
            IvParameterSpec ivps = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivps);
            byte[] bytes = cipher.doFinal(content);
            return new BASE64Encoder().encode(bytes);
        } catch (Exception ex) {
            log.error("加密密码失败", ex);
            throw new RuntimeException("加密失败");
        }
    }

    /**
     * 解密
     * @param contentParam 需要解密的内容
     * @param keyParam     加密密码
     * @param md5Key       是否对key进行md5加密
     * @param ivParam      加密向量16字节
     * @return string
     */
    public static String decrypt(String contentParam, String keyParam, boolean md5Key, String ivParam) {
        try {
            if (PubUtils.isNull(contentParam, keyParam, md5Key, ivParam)) {
                return "";
            }
            byte[] content = new BASE64Decoder().decodeBuffer(contentParam);
            byte[] key = keyParam.getBytes(CHAR_SET);
            byte[] iv = ivParam.getBytes(CHAR_SET);
            if (md5Key) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                key = md.digest(key);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
            //使用CBC模式, 需要一个向量iv, 可增加加密算法的强度
            IvParameterSpec ivps = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivps);
            byte[] bytes = cipher.doFinal(content);
            return new String(bytes, CHAR_SET);
        } catch (Exception ex) {
            log.error("解密密码失败", ex);
            throw new RuntimeException("解密失败");
        }
    }

    public static void main(String[] args) {
        String xuyuan = HttpAesUtil.encrypt("xuyuan", "123456", true, "1231231231233215");
        System.out.println(xuyuan);
    }
}