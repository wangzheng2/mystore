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

 

 //处理支付结果
public class ResponsePayServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");//支付结果。1代表成功
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur= request.getParameter("r4_Cur");
		String r5_Pid= request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");//订单编号
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");//1浏览器访问的。2点对点
		String hmac = request.getParameter("hmac");
		
		//数据校验
		boolean ok = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		if(!ok){
			out.write("数据有可能被篡改，请联系网站");
		}else{
			if("1".equals(r1_Code)){
				//支付成功：根据订单号更改订单状态。  点卡或充值时注意表单的重复提交问题。
				if("2".equals(r9_BType)){
					out.write("success");
				}
				//修改数据库即可
				System.out.println(r6_Order);
				
				OrderService service = new OrderServiceImpl();
				service.updateOrderState(r6_Order,2);
				
				out.write("<br>支付成功" +r6_Order);
				out.write("<br>即将跳转到首页..." +r6_Order);
				response.setHeader("refresh", "3;url="+request.getContextPath()+"/index.jsp");

				
				
			}
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
