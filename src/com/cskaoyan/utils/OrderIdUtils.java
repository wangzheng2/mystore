package com.cskaoyan.utils;

import java.util.UUID;

public class OrderIdUtils {

	public static String getOid(){
		String oid = UUID.randomUUID().toString().replaceAll("-", "");
		return oid;
	}
	
}
