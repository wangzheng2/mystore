package com.cskaoyan.domain;

import java.io.Serializable;

public class OrderItem implements Serializable {

	private int itemid;
	private String oid;
	private String pid;
	private int buynum;
	
	private Product product;
	
	

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * 订单项id
	 * @return
	 */
	public int getItemid() {
		return itemid;
	}
	/**
	 * 订单项id
	 * @param itemid
	 */
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	
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

	/**
	 * 购买数量
	 * @return
	 */
	public int getBuynum() {
		return buynum;
	}
	/**
	 * 购买数量
	 * @param buynum
	 */
	public void setBuynum(int buynum) {
		this.buynum = buynum;
	}
	
	@Override
	public String toString() {
		return "OrderItem [itemid=" + itemid + ", oid=" + oid + ", pid=" + pid
				+ ", buynum=" + buynum + "]";
	}

}
