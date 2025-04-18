<%@ page import="com.acs.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"       prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c"    %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"   prefix="fn"   %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<c:set var="cp" value="${pageContext.request.contextPath}"/>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@700&display=swap" rel="stylesheet">
	   
	<link rel="stylesheet" href="/font/notosans/style.css">
	<link rel="stylesheet" href="/css/reset.css">
	<link rel="stylesheet" href="/css/style.css">
	<!-- <link rel="shortcut icon" href="/img/favicon.ico">  -->
	
	<script src="/js/jquery-3.3.1.min.js"></script>
	<script src="/js/jquery.form.min.js"></script>
	<script src="/js/jquery-ui-1.12.1.js"></script>
	<script src="/js/loadAjax.js"></script>
	<script src="/js/function.js"></script>
	
	<!-- cdnjs : use a specific version of Video.js (change the version numbers as necessary) -->
	<link href="https://cdnjs.cloudflare.com/ajax/libs/video.js/7.10.2/video-js.min.css" rel="stylesheet">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/video.js/7.10.2/video.min.js"></script>
</head>

<body>
  	<tiles:insertAttribute name="content" ignore="true"/>
</body>
</html>