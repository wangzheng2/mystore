package com.cskaoyan.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.cskaoyan.dao.ShoppingItemDao;
import com.cskaoyan.domain.Order;
import com.cskaoyan.domain.OrderItem;
import com.cskaoyan.domain.ShoppingCart;
import com.cskaoyan.domain.ShoppingItem;
import com.cskaoyan.domain.User;
import com.cskaoyan.service.OrderService;
import com.cskaoyan.service.ShoppingCartService;
import com.cskaoyan.service.ShoppingItemService;
import com.cskaoyan.service.impl.OrderServiceImpl;
import com.cskaoyan.service.impl.ShoppingCartServiceImpl;
import com.cskaoyan.service.impl.ShoppingItemServiceImpl;
import com.cskaoyan.utils.OrderIdUtils;

public class OrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		 String op = request.getParameter("op");
		 
		 if (op!=null&&!op.isEmpty()) {
			
			 switch (op) {
			case "createOrder":				
				createOrder(request,response);				
				break;
				
			case "findOrdersByUid":				
				findOrdersByUid(request,response);				
				break;

			case "cancelOrder":				
				cancelOrder(request,response);				
				break;
			case "showOrderDetail":				
				showOrderDetail(request,response);				
				break;
				
				
				
			default:
				break;
			}
			 
		}
		 
	}

	private void showOrderDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		
		    if (oid!=null&&!oid.isEmpty() )
			{
				
				try {
					OrderService service = new OrderServiceImpl();
					
					Order order =service.getOrderByOid(oid);			
					request.setAttribute("order", order);
					request.getRequestDispatcher("/user/orderDetials.jsp").forward(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response.getWriter().println(" �鿴����ʧ�� ��<br>"+e.getMessage());
					response.setHeader("refresh", "2;url="+request.getServletContext().getContextPath()+"/user/OrderServlet?op=findOrdersByUid");		
				}
							
			}else {						
				response.getWriter().println(" �鿴����ʧ�� ��");
				response.setHeader("refresh", "2;url="+request.getServletContext().getContextPath()+"/user/OrderServlet?op=findOrdersByUid");	
			}
		
		
	}

	private void cancelOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String oid = request.getParameter("oid");
		String state =request.getParameter("state");
		
		
		
		//oid Ҫ��
		if (oid!=null&&!oid.isEmpty() 
			&& Integer.parseInt(state)==0  //����������δ֧��״̬�ſ���ȡ��	
			)
		{
			
			OrderService service = new OrderServiceImpl();
			
			service.cancelOrder(oid);
			
			request.getRequestDispatcher("/user/OrderServlet?op=findOrdersByUid").forward(request, response);
						
		}else {						
			response.getWriter().println(" ȡ������ʧ�� ��");
			response.setHeader("refresh", "2;url="+request.getServletContext().getContextPath()+"/index.jsp");	
		}
		
 		
	}

	// /user/OrderSerlvet
	private void findOrdersByUid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

			
			User user = (User) request.getSession().getAttribute("user");
			
			
			int uid=user.getUid();
	 
 
		  OrderService orderService = new OrderServiceImpl();
          try {
			  List<Order> orders = orderService.findOrdersByUid(uid);
			  
			  request.setAttribute("orders", orders);
			  request.getRequestDispatcher("/user/myOrders.jsp").forward(request, response);
			  
			  
		  } catch (Exception e) {
			// TODO: handle exception
			  e.printStackTrace();
			  response.getWriter().println("��ȡ�����б�ʧ�ܣ����Ժ����ԣ���\n" +e.getMessage());
			  response.setHeader("refresh", "2;url="+request.getServletContext().getContextPath()+"/index.jsp");	
	          
		  }
		
		 
	}

	private void createOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		//�ȴ������з�װOrder����
	    Order order = getOrderFromRequest(request, response);
		
		String[] ids = request.getParameterValues("ids");
		
		if (ids==null||ids.length==0) {
			response.getWriter().println("û��ѡ���κ���Ʒ���������µ���\n" );
			response.setHeader("refresh", "1;url="+request.getServletContext().getContextPath()+"/placeOrder.jsp");	
            return ;
		}
		
		Object attribute = request.getSession().getAttribute("shoppingCar");		
		if (attribute==null) {
			//�Ự���ڣ��ò���session��Ĺ��ﳵ������
			response.sendRedirect(request.getServletContext().getContextPath()+"/index.jsp");
			return;
		}		
		ShoppingCart shoppingCart = ( ShoppingCart) attribute;
		
		//�õ�һ��������ĵ�Ʒ��Ŀ
		List<OrderItem> orderItems = getOrderItems(order, ids, shoppingCart);
		
		//���ɶ���
 		try {
			OrderService orderService = new OrderServiceImpl();
			orderService.craetOrder(order,orderItems);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			response.getWriter().println("�ǳ���Ǹ�����ɶ���ʧ���ˣ��������µ���\n"+e.getMessage() );
			response.setHeader("refresh", "3;url="+request.getServletContext().getContextPath()+"/placeOrder.jsp");	
            return ;
			
		}
		
 		//���¹��ﳵ
 		ShoppingItemService shoppingItemService=new ShoppingItemServiceImpl();
 		for (ShoppingItem shoppingItem : shoppingCart.getShoppingItems()) {
			for (String id   : ids) {				
			    if (shoppingItem.getPid().equals(id)) {//˵���û��ύ����ʱѡ���������Ʒ				
			    	shoppingItemService.deleteShoppingItem(shoppingItem.getItemid());			    	  		    	
				}				
			}						
		}
 		
 		//ת�������б�		
 		request.getRequestDispatcher("/user/OrderServlet?op=findOrdersByUid&uid="
 		+shoppingCart.getUid()).forward(request, response);;
		 
 		
	}

	//����������е�order������װ��order������
	private Order getOrderFromRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, String[]> parameterMap = request.getParameterMap();		  
		Order order = new Order();
		  
		try {
			BeanUtils.populate(order, parameterMap);
			order.setOid(OrderIdUtils.getOid());
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block		
			e.printStackTrace();
			response.getWriter().println("�������������������µ���\n"+e.getMessage() );
			response.setHeader("refresh", "1;url="+request.getServletContext().getContextPath()+"/shoppingcart.jsp");
		}
		
		System.out.println("OrderServlet.createOrder()"+order);
		return order;
	}

	private List<OrderItem> getOrderItems(Order order, String[] ids,
			ShoppingCart shoppingCart) {
		List<ShoppingItem> shoppingItems = shoppingCart.getShoppingItems();
		
		
		List<OrderItem> orderItems= new ArrayList<>();
		for (ShoppingItem shoppingItem : shoppingItems) {
			for (String id   : ids) {				
			    if (shoppingItem.getPid().equals(id)) {//˵���û��ύ����ʱѡ���������Ʒ
				
			    	  OrderItem orderItem = new OrderItem();
			    	  orderItem.setOid(order.getOid()); 
			    	  orderItem.setBuynum(shoppingItem.getSnum());
			    	  orderItem.setPid(id);
			    	  orderItems.add(orderItem);
			    	  //orderItem.setItemid(itemid); ��������			    	
				}				
			}						
		}
		return orderItems;
	}

}
