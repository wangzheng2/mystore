package com.cskaoyan.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	
	 public static String getMD5(String pwd){
		 
		 
	        String md5hashvalue="";

	        try {
	            //һ�����ȿ���չ��String
	            StringBuffer stringBuffer = new StringBuffer();

	            //1����һ��������algorithm �㷨
	            MessageDigest md5 = MessageDigest.getInstance("MD5");

	            //2��****MD5ֵ�����㷨���������룬����һ��MD5�ֽ�����(16�ֽڳ���)**** 16*8=128
	            byte[] digest = md5.digest(pwd.getBytes());//Ҫ�Ƚ�����������ַ�������ֽ�����

	            //3���Խ�����д��� ��MD5ֵ��16���ֽڵ��ֽ����飩��ԭ���ַ���
	            for(byte b:digest){
	                //��MD5�ֽ�������һ���ֽ�һ���ֽ��ó���������StringBuffer��

	                //3.1 ��byte��Ϊint ��һ���ֽڶ�Ӧһ����λ��16��������
	                int i = b & 0x000000FF;
	                //3.2 intתΪ16���Ƶ��ַ�������һ����λ����16������ תΪ һ���ֽڣ�
	                
	                int value =    10;//(��ֵ) 
	                
	                String s = Integer.toHexString(i+value);//Ҫ��һ��intֵ 
	                
	                //00001111  15  F
	                
	                //3.3 ��ÿ���ֽڶ�Ӧ���ַ���һ�η���StringBuffer ��
	                if(s.length()==1){
	                    stringBuffer.append(0);
	                }
	                stringBuffer.append(s);
	            }
	            md5hashvalue = stringBuffer.toString();

	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }

	        //����һ��MD5 hashֵ
	        return md5hashvalue;
	    }
	 
}
