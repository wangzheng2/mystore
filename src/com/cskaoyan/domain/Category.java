package com.cskaoyan.domain;

import java.io.Serializable;

public class Category implements Serializable {

	
		 private int cid ;
		 private String cname ;
		 
		public int getCid() {
			return cid;
		}
		public void setCid(int cid) {
			this.cid = cid;
		}
		public String getCname() {
			return cname;
		}
		public void setCname(String cname) {
			this.cname = cname;
		}
			
		public Category() {
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "Category [cid=" + cid + ", cname=" + cname + "]";
		}
	 
	 
}
