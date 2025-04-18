<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="idSave" value="true" />
<c:set var="pwSave" value="false" />


<div id="wrapper" class="max-height">
   <div class="login_wrap">
      <div class="login_box">
         <h1>4K 영상 녹화 <span>&</span> 오더매핑 시스템</h1>
         <!-- form 영역 -->
         <form id="logForm">
	         <!-- 아이디 입력 -->
	         <input type="text" class="input" id="id" name="id" placeholder="아이디를 입력해 주세요." value="">
	         <!-- 비밀번호 입력 -->
	         <input type="password" class="input" id="password" name="password" placeholder="비밀번호를 입력해 주세요." value="">
	         <!-- 에러 메시지 영역 -->
	         <div id="error"></div>
	         <input type="submit" class="submit" value="로그인">
         </form>
         <!-- form 영역 -->
         <div class="ltxt">COPYRIGHT©2022 lxpantos. INC.ALL RIGHTS RESERVED.</div>
      </div>
   </div>
</div>
           
<script>
// window resize
$(window).on('load',function(){
    windowSize();
});
// window resize
$(window).resize(function(){
    windowSize();
});
$(window).trigger('resize');
    
//validator 유효성 검사		
$("#logForm").validate({
	submitHandler: function() {
	
	var params = new Object();
	params.id = $('#id').val();
	params.password = $('#password').val();
	
	$.ajax({ 
		type : "POST",  
		url  :"${cp}/api/login.json",
		data:params,
		dataType: 'json',  
		cache	: false,
		beforeSend : function(xmlHttpRequest){
	           xmlHttpRequest.setRequestHeader("AJAX", "true"); // ajax 호출을  header에 기록
	       },
		success	: function( obj, textStatus ) {
			if(obj.mResultMap.resCode!='0000'){
				alert(obj.mResultMap.resMessage);
				return;
			}
			location.href = "order/mappingList.do";  
		},
		error:function(exception){
			alert(exception.status + "오류가 발생하였습니다.");
		}
	});
			
	
	     }
	     , errorPlacement : $.noop
	     , invalidHandler: function(form, validator) {
	         let errors = validator.numberOfInvalids();
	         if(errors){
	             $('#error').find('span').remove();
	             $('#error').append('<span>' + validator.errorList[0].message + '</span>');
	         }
	     }
	     ,rules: {
	         id: {
	             required: true
	         },
	         password: {
	             required: true
	         },
	         agree : {
	             required : true
	         }
	     },
	     messages: {
	         id: {
	             required: 'IP 또는 PW를 다시 한번 확인해주세요.'
	         },
	         password: {
	             required: 'IP 또는 PW를 다시 한번 확인해주세요.'
	         },
	         agree: {
	             required: '동의를 확인해주세요.'
	         }
	}
});//end    
    
</script>