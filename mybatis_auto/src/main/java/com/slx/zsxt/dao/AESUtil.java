/*
 * Copyright 2018 易立讯 Co., Ltd All rights reserved.
 */
/**
 * ===============================================================================<br>
 * <br>
 * 公司名	易立讯 <br>
 * 项目名	学生工作管理系统 <br>
 * 处理名	AES加解密处理Util <br>
 * <br>
 * 变更日	2018-08-27 <br>
 * 变更者	Wu ZhiQiang <br>
 * 变更理由	新规作成 <br>
 * <br>
 * ===============================================================================<br>
 */
package com.slx.zsxt.dao;

import java.beans.Encoder;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.druid.util.Base64;


/**
 *
 * @Title:AESUtil
 * @Package:cn.yilixun.ccswust.xcsam.util
 * @Description:AES加解密
 * @author Wu, ZhiQiang
 * @date:2018-08-27
 * @version:V1.0.0
 */
public class AESUtil {
    /**
     * CODE TYPE
     */
    public static final String CODE_TYPE = "UTF-8";

    /** AES */
    public static final String AES = "AES";

    /**
     * AES KEY
     */
    private static final String AES_KEY = "837F31ADFE034949A8045FC227FFD8C1";

    public static String encode(String content) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance(AES);
            keygen.init(128, new SecureRandom(AES_KEY.getBytes()));
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKey key = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byte_encode = content.getBytes(CODE_TYPE);
            byte[] byte_AES = cipher.doFinal(byte_encode);
            String AES_encode = new String(Base64.byteArrayToBase64(byte_AES));
            return AES_encode;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    public static String decode(String content) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance(AES);
            keygen.init(128, new SecureRandom(AES_KEY.getBytes()));
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKey key = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] byte_content = Base64.base64ToByteArray(content);
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, CODE_TYPE);
            return AES_decode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String createSign(SortedMap<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        Iterator<Entry<String, String>> it = parameters.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String> entry = it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        String sign = MD5Util.encrypt(sb.toString()).toUpperCase();
        return sign;
    }

    public static void main(String[] args) {
        System.out.println(AESUtil.encode("CC062"));
        System.out.println(AESUtil.decode(AESUtil.encode("CC062")));

        // 参数形式
//		String studentNo = "201740100"; // 学号
//		String timeStamp = System.currentTimeMillis() + "";
//		String imei = "C227FFD8C1837F31ADFE034949A8045F";
//
//        // 创建请求对象
//        SortedMap<String, String> parameters = new TreeMap<String, String>();
//        parameters.put("timeStamp", timeStamp);
//        parameters.put("studentNo", studentNo);
//        parameters.put("imei", imei);
//
//        String sign = AESUtil.createSign(parameters);
//        System.out.println("parameter sign:" + sign);
//
//
//        String linkParam = "studentNo=" + AESUtil.encode(studentNo)
//        					+ "&timeStamp=" + AESUtil.encode(timeStamp)
//        					+ "&imei=" + AESUtil.encode(imei)
//        					+ "&sign=" + sign;
//        System.out.println("link paramter:" + linkParam);
//
//        SortedMap<String, String> dp = new TreeMap<String, String>();
//        dp.put("timeStamp", AESUtil.decode(AESUtil.encode(timeStamp)));
//        dp.put("studentNo", AESUtil.decode(AESUtil.encode(studentNo)));
//        dp.put("imei", AESUtil.decode(AESUtil.encode(imei)));
//        String dpSign = AESUtil.createSign(dp);
//
//        System.out.println("decode sign:" + dpSign);

    }



}
