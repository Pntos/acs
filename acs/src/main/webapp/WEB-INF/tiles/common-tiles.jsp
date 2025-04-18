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
<style>
html, body{margin:0;padding:0}
body {background:#f8f8f8; display: table; height: 100%; width: 100%;}
div { display: table-cell; vertical-align: middle; text-align: center; color:#333;}
div > img { max-width: 100%; height: auto;}
div > h6 {font-size:40px; padding:50px 0 0 0;margin:0}
div > p {font-size:16px; padding:0;margin:0}
</style>
</head>

<body>
  	<tiles:insertAttribute name="content" ignore="true"/>
</body>
</html>