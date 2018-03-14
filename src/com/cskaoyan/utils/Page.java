package com.cskaoyan.utils;

import java.util.List;

public class Page {
	
	private List<Object> itemList ;
	
	private int totalNumber ;    //�ܵļ�¼��
	private int currentPageNumber ;//��ǰ��ҳ��
	private int totolPageNumber;  //�ܵ�ҳ����
	
	private int countPerPage; //ÿһҳ��Ŀ��
	// ��ҳ =1
	// βҳ =totolPageNumber
	
	//��һҳ   currentPageNumber-1	
	private int previewPageNum ;
	
	//��һҳ   currentPageNumber+1
	private int nextPageNum ;

	public List<Object> getItemList() {
		return itemList;
	}

	public void setItemList(List<Object> itemList) {
		this.itemList = itemList;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	public int getTotolPageNumber() {
		return totolPageNumber;
	}

	public void setTotolPageNumber(int totolPageNumber) {
		this.totolPageNumber = totolPageNumber;
	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}

	public int getPreviewPageNum() {
		return previewPageNum;
	}

	public void setPreviewPageNum(int previewPageNum) {
		this.previewPageNum = previewPageNum;
	}

	public int getNextPageNum() {
		return nextPageNum;
	}

	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	@Override
	public String toString() {
		return "Page [itemList=" + itemList + ", totalNumber=" + totalNumber
				+ ", currentPageNumber=" + currentPageNumber
				+ ", totolPageNumber=" + totolPageNumber + ", countPerPage="
				+ countPerPage + ", previewPageNum=" + previewPageNum
				+ ", nextPageNum=" + nextPageNum + "]";
	}

	public Page(int currentPageNumber,int totalNumber,int countPerPage) {
		 
		
	     setCurrentPageNumber(currentPageNumber);
	     setCountPerPage(countPerPage);
		 setTotalNumber(totalNumber);
		
		 totolPageNumber = (totalNumber%countPerPage==0) ?totalNumber/countPerPage: totalNumber/countPerPage+1 ; 
		 setTotolPageNumber(totolPageNumber);
		
		 
 		 		
		int previewPageNum=(currentPageNumber-1==0)? currentPageNumber:currentPageNumber-1;	
		setPreviewPageNum(previewPageNum);
 	 
		int nextPageNum=(currentPageNumber==totolPageNumber)? totolPageNumber:currentPageNumber+1;
		setNextPageNum(nextPageNum);
	 
	}
	
	public Page() {}
	
	

}
