package com.bdxh.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: xuyuan
 * @create: 2018-12-17 16:57
 **/
public class ObjectUtil {

	//根据时间毫秒值生成id
	public static String getId() {
		StringBuffer buf=new StringBuffer();
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmsss");
		Lock lock=new ReentrantLock();
		lock.lock();
		String str=dateformat.format(new Date());
		lock.unlock();
		int s=(int)((Math.random()*9+1)*1000);
		buf.append(str).append(String.valueOf(s));
		return buf.toString();
	}

	//生成日期时间
	public static String  getNowStr(){
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String format = dateFormat.format(new Date());
		return format;
	}

	//生成日期
	public static String getDateStr(){
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String format = dateFormat.format(new Date());
		return format;
	}

	//获取固定位数随机数
	public static String getRandom(int num){
		if (num<=0){
			throw new RuntimeException("参数异常");
		}
		int fac=1;
		for (int i=0;i<num-1;i++){
			fac=fac*10;
		}
		int random=(int)((Math.random()*9+1)*fac);
		return String.valueOf(random);
	}
}