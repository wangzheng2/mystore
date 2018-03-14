package com.cskaoyan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cskaoyan.service.OrderService;
import com.cskaoyan.service.impl.OrderServiceImpl;
import com.cskaoyan.utils.PaymentUtil;

 

 //����֧�����
public class ResponsePayServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");//֧�������1����ɹ�
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur= request.getParameter("r4_Cur");
		String r5_Pid= request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");//�������
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");//1��������ʵġ�2��Ե�
		String hmac = request.getParameter("hmac");
		
		//����У��
		boolean ok = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		if(!ok){
			out.write("�����п��ܱ��۸ģ�����ϵ��վ");
		}else{
			if("1".equals(r1_Code)){
				//֧���ɹ������ݶ����Ÿ��Ķ���״̬��  �㿨���ֵʱע������ظ��ύ���⡣
				if("2".equals(r9_BType)){
					out.write("success");
				}
				//�޸����ݿ⼴��
				System.out.println(r6_Order);
				
				OrderService service = new OrderServiceImpl();
				service.updateOrderState(r6_Order,2);
				
				out.write("<br>֧���ɹ�" +r6_Order);
				out.write("<br>������ת����ҳ..." +r6_Order);
				response.setHeader("refresh", "3;url="+request.getContextPath()+"/index.jsp");

				
				
			}
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
