package com.swu.erp.util.encode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 订单号生成工具类
 * @author Administrator
 *
 */
public class OrderNumGeneratorUtil {
	public static int serNum = 1;
	public static int len = 5;
	private static final byte[] zeros = {48,48,48,48,48,48};
	public static void main(String[] args) {
		System.out.println(generatorOrderNum());
		System.out.println(generatorOrderNum());
		System.out.println(generatorOrderNum());
		System.out.println(generatorOrderNum());
		System.out.println(generatorOrderNum());
	}
	public static final String generatorOrderNum(){
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("yyMMdd");
		String fir = df.format(d);
		int num = serNum++;
		int len2 = (num+"").length();
		String sec = new String(zeros,0,len-len2);
		return Long.toHexString(new Long(fir+sec+num)).toUpperCase();
	}
}
