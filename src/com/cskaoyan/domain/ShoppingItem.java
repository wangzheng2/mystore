package com.cskaoyan.domain;

import java.io.Serializable;

public class ShoppingItem implements Serializable{

	private int itemid;
	private int sid;
	private int snum;
	private String pid;
	
	private Product product;
	
	/**
	 * 购物车项id
	 * @return
	 */
	public int getItemid() {
		return itemid;
	}
	/**
	 * 购物车项id
	 * @param itemid
	 */
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	
	/**
	 * 购物车id
	 * @return
	 */
	public int getSid() {
		return sid;
	}
	/**
	 * 购物车项id
	 * @param sid
	 */
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	/**
	 * 商品id
	 * @return
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 商品id
	 * @param pid
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getSnum() {
		return snum;
	}
	public void setSnum(int snum) {
		this.snum = snum;
	}
	@Override
	public String toString() {
		return "ShoppingItme [itemid=" + itemid + ", sid=" + sid + ", pid="
				+ pid + "]";
	}

}
