package com.cskaoyan.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {

	private String oid;
	private double money;
	private String recipients;
	private String tel;
	private String address;
	private int state;
	private Date ordertime;
	private int uid;
	private User user;
	
	private List<OrderItem> orderitems;
	
	/**
	 * ����id
	 * @return
	 */
	public String getOid() {
		return oid;
	}
	/**
	 * ����id
	 * @param oid
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	/**
	 * �������
	 * @return
	 */
	public double getMoney() {
		return money;
	}
	/**
	 * �������
	 * @param money
	 */
	public void setMoney(double money) {
		this.money = money;
	}
	
	/**
	 * �ռ���
	 * @return
	 */
	public String getRecipients() {
		return recipients;
	}
	/**
	 * �ռ���
	 * @param recipients
	 */
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	/**
	 * �绰
	 * @return
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * �绰
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * ��ַ
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * ��ַ
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 0-����ȡ����1-δ֧����2�Ѿ�֧����3�ѷ���
	 * @return
	 */
	public int getState() {
		return state;
	}
	/**
	 * 0-����ȡ����1-δ֧����2�Ѿ�֧����3�ѷ���
	 * @param state
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * �µ�ʱ��
	 * @return
	 */
	public Date getOrdertime() {
		return ordertime;
	}
	/**
	 * �µ�ʱ��
	 * @param ordertime
	 */
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	/**
	 * �û�id
	 * @return
	 */
	public int getUid() {
		return uid;
	}
	/**
	 * �û�id
	 * @param uid
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	/**
	 * �û�����
	 * @return
	 */
	public User getUser() {
		return user;
	}
	/**
	 * �û�����
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<OrderItem> getOrderitems() {
		return orderitems;
	}
	public void setOrderitems(List<OrderItem> orderitems) {
		this.orderitems = orderitems;
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", money=" + money + ", recipients="
				+ recipients + ", tel=" + tel + ", address=" + address
				+ ", state=" + state + ", ordertime=" + ordertime + ", uid="
				+ uid + "]";
	}

}
