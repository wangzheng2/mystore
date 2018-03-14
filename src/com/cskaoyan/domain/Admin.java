package com.cskaoyan.domain;

import java.io.Serializable;

public class Admin implements Serializable {

	private int aid;
	private String username;
	private String password;
	
	/**
	 * ����Աid
	 * @return
	 */
	public int getAid() {
		return aid;
	}
	/**
	 * ����Աid
	 * @param aid
	 */
	public void setAid(int aid) {
		this.aid = aid;
	}

	/**
	 * �û���
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * �û���
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * ����
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * ����
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Admin [aid=" + aid + ", username=" + username + ", password="
				+ password + "]";
	}

}
