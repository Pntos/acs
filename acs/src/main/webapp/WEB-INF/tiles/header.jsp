<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@ page session="true"%>

<c:set var="position" value="${sessionScope.SESSION_MAP.HUMAN_POSI_CD}" />

<!-- header 영역 시작 -->
<h1><a href="/"><img src="/img/top_logo.png" alt="4K영상 녹화 &amp; 오더매핑 시스템"></a></h1>
<div class="h_tab">
   <a href="/order/mappingList.do" id="m01">영상목록</a>
   <a href="/system/setting.do" id="m02">환경설정</a>
   <a href="/log/logList.do" id="m03">시스템로그</a>
</div>
<div class="member">
   ${sessionScope.SESSION_MAP.USER_NM} <strong>${sessionScope.SESSION_MAP.USER_GNM}</strong>
   <div class="btn-member"><a href="javascript: logout();"><img src="/img/login_btn.png" alt="로그아웃"></a></div>
</div>

<script type="text/javascript">
	$(function () {
		let menu = "${menu}";
		console.log(menu);
		
		if("${menu}" == "M01"){
			$("#m01").addClass('on');
		}else if("${menu}" == "M02"){
			$("#m02").addClass('on');
		}else if("${menu}" == "M03"){
			$("#m03").addClass('on');
		}
	});

	function logout(){
		if(!confirm("로그아웃 하시겠습니까?")) return;
		$.ajax({
			url:'/api/logout.json',
			type: 'POST',
			data:null,
			dataType:"json",
			async:false,
			error:function(XMLHttpRequest, textStatus, errorThrown) {
				alert("[" + textStatus + "] " + errorThrown);
			},
			success:function(obj){
				location.href = "${cp}/";
			}
		});
	}
</script>