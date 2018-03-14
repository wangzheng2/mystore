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
					response.getWriter().println(" 查看订单失败 ！<br>"+e.getMessage());
					response.setHeader("refresh", "2;url="+request.getServletContext().getContextPath()+"/user/OrderServlet?op=findOrdersByUid");		
				}
							
			}else {						
				response.getWriter().println(" 查看订单失败 ！");
				response.setHeader("refresh", "2;url="+request.getServletContext().getContextPath()+"/user/OrderServlet?op=findOrdersByUid");	
			}
		
		
	}

	private void cancelOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String oid = request.getParameter("oid");
		String state =request.getParameter("state");
		
		
		
		//oid 要有
		if (oid!=null&&!oid.isEmpty() 
			&& Integer.parseInt(state)==0  //订单必须是未支付状态才可以取消	
			)
		{
			
			OrderService service = new OrderServiceImpl();
			
			service.cancelOrder(oid);
			
			request.getRequestDispatcher("/user/OrderServlet?op=findOrdersByUid").forward(request, response);
						
		}else {						
			response.getWriter().println(" 取消订单失败 ！");
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
			  response.getWriter().println("获取订单列表失败，请稍后重试！！\n" +e.getMessage());
			  response.setHeader("refresh", "2;url="+request.getServletContext().getContextPath()+"/index.jsp");	
	          
		  }
		
		 
	}

	private void createOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		//先从请求中封装Order对象
	    Order order = getOrderFromRequest(request, response);
		
		String[] ids = request.getParameterValues("ids");
		
		if (ids==null||ids.length==0) {
			response.getWriter().println("没有选中任何商品，请重新下单！\n" );
			response.setHeader("refresh", "1;url="+request.getServletContext().getContextPath()+"/placeOrder.jsp");	
            return ;
		}
		
		Object attribute = request.getSession().getAttribute("shoppingCar");		
		if (attribute==null) {
			//会话过期，得不到session里的购物车对象了
			response.sendRedirect(request.getServletContext().getContextPath()+"/index.jsp");
			return;
		}		
		ShoppingCart shoppingCart = ( ShoppingCart) attribute;
		
		//得到一个订单里的单品条目
		List<OrderItem> orderItems = getOrderItems(order, ids, shoppingCart);
		
		//生成订单
 		try {
			OrderService orderService = new OrderServiceImpl();
			orderService.craetOrder(order,orderItems);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			response.getWriter().println("非常抱歉，生成订单失败了，请重新下单！\n"+e.getMessage() );
			response.setHeader("refresh", "3;url="+request.getServletContext().getContextPath()+"/placeOrder.jsp");	
            return ;
			
		}
		
 		//更新购物车
 		ShoppingItemService shoppingItemService=new ShoppingItemServiceImpl();
 		for (ShoppingItem shoppingItem : shoppingCart.getShoppingItems()) {
			for (String id   : ids) {				
			    if (shoppingItem.getPid().equals(id)) {//说明用户提交订单时选中了这个商品				
			    	shoppingItemService.deleteShoppingItem(shoppingItem.getItemid());			    	  		    	
				}				
			}						
		}
 		
 		//转到订单列表		
 		request.getRequestDispatcher("/user/OrderServlet?op=findOrdersByUid&uid="
 		+shoppingCart.getUid()).forward(request, response);;
		 
 		
	}

	//把请求参数中的order参数封装到order对象里
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
			response.getWriter().println("订单参数有误，请重新下单！\n"+e.getMessage() );
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
			    if (shoppingItem.getPid().equals(id)) {//说明用户提交订单时选中了这个商品
				
			    	  OrderItem orderItem = new OrderItem();
			    	  orderItem.setOid(order.getOid()); 
			    	  orderItem.setBuynum(shoppingItem.getSnum());
			    	  orderItem.setPid(id);
			    	  orderItems.add(orderItem);
			    	  //orderItem.setItemid(itemid); 自增即可			    	
				}				
			}						
		}
		return orderItems;
	}

}
