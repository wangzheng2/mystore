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
	 * ������id
	 * @return
	 */
	public int getItemid() {
		return itemid;
	}
	/**
	 * ������id
	 * @param itemid
	 */
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	
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
	 * ��Ʒid
	 * @return
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * ��Ʒid
	 * @param pid
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * ��������
	 * @return
	 */
	public int getBuynum() {
		return buynum;
	}
	/**
	 * ��������
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
