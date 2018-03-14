<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Shoes Store - Shopping Cart</title>
<meta name="keywords" content="shoes store, shopping cart, free template, ecommerce, online shop, website templates, CSS, HTML" />
<meta name="description" content="Shoes Store, Shopping Cart, online store template " />
<link href="css/templatemo_style.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="css/ddsmoothmenu.css" />

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/ddsmoothmenu.js">


</script>

<script type="text/javascript">

ddsmoothmenu.init({
	mainmenuid: "top_nav", //menu DIV id
	orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
	classname: 'ddsmoothmenu', //class added to menu's outer DIV
	//customtheme: ["#1c5a80", "#18374a"],
	contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
})

</script>

</head>

<body>
<c:if test="${empty categorylist }">
		<jsp:forward page="/MainPageServlet"></jsp:forward>
</c:if>
<div id="templatemo_body_wrapper">
<div id="templatemo_wrapper">

	<div id="templatemo_header">
    	<div id="site_title"><h1><a href="http://localhost/${pageContext.request.contextPath }">Online Shoes Store</a></h1></div>
        <div id="header_right">
        	<p>
	        <c:if test="${!empty user }">
	        	<a href="${pageContext.request.contextPath }/user/personal.jsp">我的个人中心</a> |
	        </c:if>
	        <a href="${pageContext.request.contextPath }/CartServlet?op=findCart">购物车</a> | 
	        <c:if test="${user == null }">
	        	<a href="${pageContext.request.contextPath }/user/login.jsp">登录</a> |
	        	<a href="${pageContext.request.contextPath }/user/regist.jsp">注册</a>
	        </c:if>
	        <c:if test="${!empty sessionScope.user }">
	        	${user.nickname }
	        	<a href="${pageContext.request.contextPath }/user/UserServlet?op=lgout">退出</a></p>
	        </c:if>
	        </p>
	        <p>
		        <c:if test="${!empty user }">
		        	<a href="${pageContext.request.contextPath }/user/OrderServlet?op=findOrdersByUid">我的订单</a> |
		        </c:if>
	        </p>
		</div>
        <div class="cleaner"></div>
    </div> <!-- END of templatemo_header -->
    
    <div id="templatemo_menubar">
    	<div id="top_nav" class="ddsmoothmenu">
            <ul>
                <li><a href="${pageContext.request.contextPath }/MainPageServlet" class="selected">主页</a></li>
            </ul>
            <br style="clear: left" />
        </div> <!-- end of ddsmoothmenu -->
        <div id="templatemo_search">
            <form action="${pageContext.request.contextPath }/ProductServlet" method="get">
              <input type="hidden" name="op" value="searchProductsByKeyword"/>
              <input type="text" value="${keyword}" name="keyword" id="keyword" title="keyword" onfocus="clearText(this)" onblur="clearText(this)" class="txt_field" />
              <input type="submit" name="Search" value=" " alt="Search" id="searchbutton" title="Search" class="sub_btn"  />
            </form>
        </div>
    </div> <!-- END of templatemo_menubar -->
    
    <div id="templatemo_main">
    	<div id="sidebar" class="float_l">
        	<div class="sidebar_box"><span class="bottom"></span>
            	<h3>品牌</h3>   
                <div class="content"> 
                	<ul class="sidebar_list">
                    	<c:forEach items="${categorylist }" var="category" varStatus="vs">
                			 		<li>
                						<a href="${pageContext.request.contextPath }/ProductServlet?op=findProductsbyCid&cid=${category.cid}">${category.cname}</a>
                					</li>                		 
                		</c:forEach>
                    </ul>
                </div>
            </div>
            
        </div>
        <div id="content" class="float_r">
         	
        	<h3>个人资料</h3>
			<hr />
			<br />
			<!-- 
				 （仅限修改昵称，密码，邮箱和出生日期）
				 nickname
				 password
				 email
				 birthday
			 -->
			<form action="${pageContext.request.contextPath }/user/UserServlet" method="post">
				 <input type="hidden" name="op" value="updateUser"/>
				<input type="hidden" name="uid" value="${user.uid}"/>
				<input type="hidden" name="username" value="${user.username}"/>
				
				用&nbsp;户&nbsp;名： ${user.username }
				<br /> <br />
				昵&nbsp;&nbsp;&nbsp;&nbsp;称：<input type="text" name="nickname" value="${user.nickname }">
				<br /><br />
				密&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="password" name="password" value="${user.password }"/>
				<br /><br />
				邮&nbsp;&nbsp;&nbsp;&nbsp;箱：<input type="text" name="email" value="${user.email }"/>
				<br /> <br />
				出生日期：<input type="text" name="birthday" value="${user.birthday}"/> 
				<span> 出生日期格式为yyyy-MM-dd</span> 
				<br /><br />
				<%-- 头像： ${user.headicon} --%>
				<br /><br />
				<%-- 注册时间： ${user.updatetime } --%>
				<br /><br />
				 
				<input type="submit" value="保存填写" id="submit"/>
				<input type="reset" value="撤销重写" id="reset"/>
			</form>
 
         
                     
			</div>
        <div class="cleaner"></div>
    </div> 
    
    <div id="templatemo_footer">
		    Copyright (c) 2016 <a href="#">Web商城</a> | <a href="#">Web工作室</a>	
    </div> 
    
</div> 
</div>

</body>
</html>