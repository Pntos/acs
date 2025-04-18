<%--
- author : (주)디모텍
- create : 2022 
- update : 
- Copyright © 2022 DIMOTECH CO.,LTD. All rights reserved.
- @(#)
- 설명: 메타정보 및 공통 스크립트/CSS 파일 정의
--%>

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c"    %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, height=device-height">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>4K 영상녹화-오더매핑시스템</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@700&display=swap" rel="stylesheet">
   
<link rel="stylesheet" href="/font/notosans/style.css">
<link rel="stylesheet" href="/css/reset.css">
<link rel="stylesheet" href="/css/style.css">
<link rel="SHORTCUT ICON" href="/image/favicon.png">
<link rel="SHORTCUT ICON" href="/image/favicon.ico">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/jquery.form.min.js"></script>
<script src="/js/jquery-ui-1.12.1.js"></script>
<script src="/js/jquery.validate.min.js"></script>
<script src="/js/moment.min.js"></script>
<script src="/js/fullcalendar.min.js"></script>
<script src="/js/date.js"></script>
<script src="/js/loadAjax.js"></script>
<script src="/js/function.js"></script>
<script src="/js/common.js"></script>

<!-- cdnjs : use a specific version of Video.js (change the version numbers as necessary) -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/video.js/7.10.2/video-js.min.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/video.js/7.10.2/video.min.js"></script>