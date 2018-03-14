package com.cskaoyan.controller.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserFormBean {

	private int uid;
	private String username;
	private String nickname;
	private String email;
	private String password;
	private String birthday=null;
	
	private Map<String,String> error = new HashMap<String, String>();

	public boolean validate(){
		if(username == null || "".equals(username.trim())){
			error.put("username", "�û�������Ϊ��");
		}else if(!(username.length() > 1 && username.length() < 17)){
			error.put("username", "�û���������2-16���ַ�֮��");
		}
		
		if(nickname == null || "".equals(nickname.trim())){
			error.put("nickname", "�ǳƲ���Ϊ��");
		}else if(!(nickname.length() > 1 && nickname.length() < 17)){
			error.put("nickname", "�ǳƳ�����2-16���ַ�֮��");
		}
		
		if(password == null || "".equals(password.trim())){
			error.put("password", "���벻��Ϊ��");
		}else if(!(password.length() > 1 && nickname.length() < 17)){
			error.put("password", "���볤����2-16���ַ�֮��");
		}
	    if(birthday == null || "".equals(birthday.trim())){
			error.put("birthday", "�������ڲ���Ϊ��");
		  }else{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = df.parse(birthday);
			} catch (ParseException e) {
				error.put("birthday", "�������ڸ�ʽΪyyyy-MM-dd");
			}
		}
		
		if(email == null || email.equals("")){
			error.put("email", "���䲻��Ϊ��");
		}else if(!email.matches("\\b^['_a-z0-9-\\+]+(\\.['_a-z0-9-\\+]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2}|aero|arpa|asia|biz|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|nato|net|org|pro|tel|travel|xxx)$\\b")){
			error.put("email", "���䲻��ȷ");
		}
		return error.isEmpty();
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Map<String, String> getError() {
		return error;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "UserFormBean [uid=" + uid + ", username=" + username
				+ ", nickname=" + nickname + ", email=" + email + ", password="
				+ password + ", birthday=" + birthday + ", error=" + error
				+ "]";
	}
	
}
