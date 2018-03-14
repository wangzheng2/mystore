<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Shoes Store - Product Detail</title>
<meta name="keywords" content="shoes store, product detail, free template, ecommerce, online shop, website templates, CSS, HTML" />
<meta name="description" content="Shoes Store, Product Detail, free ecommerce template provided " />
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

<script type="text/javascript" src="js/jquery-1-4-2.min.js"></script> 
<link rel="stylesheet" href="css/slimbox2.css" type="text/css" media="screen" /> 
<script type="text/JavaScript" src="js/slimbox2.js"></script> 


</head>

<body>
<c:if test="${empty categorylist }">
		<jsp:forward page="/MainPageServlet"></jsp:forward>
</c:if>
<div id="templatemo_body_wrapper">
<div id="templatemo_wrapper">

	<div id="templatemo_header">
    	<div id="site_title"><h1><a href="#">Online  Store</a></h1></div>
        <div id="header_right">
        	<p>
	        <c:if test="${!empty user }">
		        <a href="${pageContext.request.contextPath }/user/personal.jsp">我的个人中心</a> |
        	</c:if>
	        <a href="${pageContext.request.contextPath }/CartServlet?op=findCart">购物车</a> | 
	        <c:if test="${ empty user }">
	        	<a href="${pageContext.request.contextPath }/user/login.jsp">登录</a> |
	        	<a href="${pageContext.request.contextPath }/user/regist.jsp">注册</a></p>
	        </c:if>
	        <c:if test="${!empty user }">
	        	${user.nickname }
	        	<a href="${pageContext.request.contextPath }/user/UserServlet?op=lgout">退出</a></p>
	        </c:if> 
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
              <input type="submit"  value=" " alt="Search" id="searchbutton" title="Search" class="sub_btn"  />
            </form>
        </div>
    </div> <!-- END of templatemo_menubar -->
    
    <div id="templatemo_main">
    	<div id="sidebar" class="float_l">
        	<div class="sidebar_box"><span class="bottom"></span>
            	<h3>院系</h3>   
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
        	<h3>${product.pname}</h3>
            <div class="content_half float_l">
        	<a  rel="lightbox[portfolio]" href="images/product/10_l.jpg">
        	<img src="productimg/${product.imgurl }" style="width: 300px; height: 250px" /></a>
            </div>
            <div class="content_half float_r">
                <table>
                <tr>
                        <td width="160">品牌:</td>
                        <td>${product.category.cname}</td>
                    </tr>
                    <tr>
                        <td width="160">商城价格:</td>
                        <td>${product.estoreprice}</td>
                    </tr>
                    <tr>
                        <td>市场价格:</td>
                        <td>${product.markprice}</td>
                    </tr>
                    <tr>
                        <td>商品号:</td>
                        <td>${product.pid }</td>
                    </tr>
                    <tr>
                    	<td>购买数量:</td>
                        <td><input type="text" id="snum" value="1" style="width: 20px; text-align: right" />&nbsp;库存:${product.pnum }</td>
                    </tr>
                </table>
                <div class="cleaner h20"></div>

                <c:if test="${empty user }">
	                <a href="javascript:login()" class="addtocart"></a>
	            </c:if>
	            <c:if test="${!empty user }">
	                <a href="javascript:addCart(${product.pid },${user.uid})" class="addtocart"></a>
	            </c:if>

			</div>
            <div class="cleaner h30"></div>
            
            <h5>商品描述</h5>
            <p>${requestScope.product.description }</p>	
        </div> 
        <div class="cleaner"></div>
    </div> <!-- END of templatemo_main -->
    
    <div id="templatemo_footer">
    	
    </div> <!-- END of templatemo_footer -->
    
</div> <!-- END of templatemo_wrapper -->
</div> <!-- END of templatemo_body_wrapper -->
<script type="text/javascript">
		function login(){
			alert("请先登录");
			window.location.href="${pageContext.request.contextPath}/user/login.jsp";
		}
		function addCart(pid,uid){
			var snum = $("#snum").val();
			window.location.href="${pageContext.request.contextPath}/CartServlet?op=addCart&pid="+pid+"&uid="+uid+"&snum="+snum;
		}
	</script>
</body>
</html>