<%@page import="java.util.Enumeration"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
</head>
<body style="background-color: aqua">
Template from aggregated2 received following parameters :
<table>
<tr><th>Name</th><th>Value</th></tr>
<%
	Enumeration names=request.getParameterNames();
	while(names.hasMoreElements()){
		String name = names.nextElement().toString();
		
		%><tr> <td><%=name%></td><td><code><%=request.getParameter(name)%></code></td> </tr> <% 
	}
%>
</table>
</body>
</html>