<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IRead愛閱讀</title>
</head>
<body>
${ LoginOK.lastLogin}
<center> 
     <table>
     		<tr>
 				 <td>
 				   <div>
 				        <c:if test="${ empty LoginOK}">
 				       		<a href="<c:url value='/insert/InsertMemberForm.jsp' />">
 				           	註冊
 				           	</a>
 				         </c:if>
 				       		
 				   </div>
 				 </td>
 				 <td>
 				   <div>
 				       <a href="<c:url value='/index.jsp' />">
 				                                儲值
 				       </a>
 				   </div>
 				 </td>
 				 <td>
 				   <div>
 				      <c:if test="${ empty LoginOK}">
						<a href="<c:url value='/login/Login.jsp' />">
						     登入 
						</a>
		              </c:if>
 				   </div>
 				 </td> 
 				 <td>
 				   <div>
 				      <c:if test="${! empty LoginOK}">
						<a href="<c:url value='/login/Member.jsp' />">
						      會員
						</a>
		              </c:if>
 				   </div>
 				 </td> 
 				 <td>
 				   <div>
 				      <c:if test="${! empty LoginOK}">
 				      	<a href="<c:url value='/login/Logout.jsp' />">
						     登出 
						</a>
		              </c:if>
 				   </div>
 				 </td>   	  	
     		</tr>
     </table>
</center>     
</body>
</html>