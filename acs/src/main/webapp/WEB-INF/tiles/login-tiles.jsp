<%@ page import="com.acs.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c"    %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"   prefix="fn"   %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<tiles:insertAttribute name="common" ignore="true"/>
</head>

<body>
  	<tiles:insertAttribute name="content" ignore="true"/>
</body>
</html>