package com.cskaoyan.domain;

import java.io.Serializable;

public class Admin implements Serializable {

	private int aid;
	private String username;
	private String password;
	
	/**
	 * 管理员id
	 * @return
	 */
	public int getAid() {
		return aid;
	}
	/**
	 * 管理员id
	 * @param aid
	 */
	public void setAid(int aid) {
		this.aid = aid;
	}

	/**
	 * 用户名
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 用户名
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 密码
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 密码
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
