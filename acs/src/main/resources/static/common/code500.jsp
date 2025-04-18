<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<style>
html, body{margin:0;padding:0}
body {background:#f8f8f8; display: table; height: 100%; width: 100%;}
div { display: table-cell; vertical-align: middle; text-align: center; color:#333}
div > img { max-width: 100%; height: auto;}
div > h6 {font-size:40px; padding:50px 0 0 0;margin:0}
div > p {font-size:16px; padding:0;margin:0}
</style>
</head>
<body>
	<div>
		<img src="/image/common/error.png">
		<h6>500</h6>
		<p><b>내부 서버</b>오류가 발생했습니다.</p>
		<p>
			request_uri :
			<c:out value="${requestScope['javax.servlet.error.request_uri']}" />
		</p>
		<p>
			status_code :
			<c:out value="${requestScope['javax.servlet.error.status_code']}" />
		</p>
		<p>
			servlet_name :
			<c:out value="${requestScope['javax.servlet.error.servlet_name']}" />
		</p>
		<p>
			exception :
			<c:out value="${requestScope['javax.servlet.error.exception']}" />
		</p>
		<p>
			servlet_name :
			<c:out value="${requestScope['javax.servlet.error.servlet_name']}" />
		</p>
		<p>
			message :
			<c:out value="${requestScope['javax.servlet.error.message']}" />
		</p>

	</div>
</body>
</html>