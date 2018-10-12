/*
 * Copyright 2018 易立讯 Co., Ltd All rights reserved.
 */
 /**
 *===============================================================================<br>
 * <br>
 * 公司名	易立讯 <br>
 * 项目名	学生工作管理系统 <br>
 * 处理名	MD5加密处理Util <br>
 * <br>
 * 变更日	2018-08-27 <br>
 * 变更者	Wu ZhiQiang <br>
 * 变更理由	新规作成 <br>
 * <br>
 *===============================================================================<br>
 */
package com.slx.zsxt.dao;

import java.security.MessageDigest;

/**
 * 
 * @Title:MD5Util
 * @Package:cn.yilixun.ccswust.xcsam.util
 * @Description:MD5加密
 * @author Wu,ZhiQiang
 * @date:2018-08-27
 * @version:V1.0.0
 */
public class MD5Util {
	public final static String encrypt(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
