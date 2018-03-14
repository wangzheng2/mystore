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
	 * 订单id
	 * @return
	 */
	public String getOid() {
		return oid;
	}
	/**
	 * 订单id
	 * @param oid
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	/**
	 * 订单金额
	 * @return
	 */
	public double getMoney() {
		return money;
	}
	/**
	 * 订单金额
	 * @param money
	 */
	public void setMoney(double money) {
		this.money = money;
	}
	
	/**
	 * 收件人
	 * @return
	 */
	public String getRecipients() {
		return recipients;
	}
	/**
	 * 收件人
	 * @param recipients
	 */
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	/**
	 * 电话
	 * @return
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * 电话
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 地址
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 地址
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 0-订单取消，1-未支付，2已经支付，3已发货
	 * @return
	 */
	public int getState() {
		return state;
	}
	/**
	 * 0-订单取消，1-未支付，2已经支付，3已发货
	 * @param state
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * 下单时间
	 * @return
	 */
	public Date getOrdertime() {
		return ordertime;
	}
	/**
	 * 下单时间
	 * @param ordertime
	 */
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	/**
	 * 用户id
	 * @return
	 */
	public int getUid() {
		return uid;
	}
	/**
	 * 用户id
	 * @param uid
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	/**
	 * 用户对象
	 * @return
	 */
	public User getUser() {
		return user;
	}
	/**
	 * 用户对象
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
