<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="com.acs.util.Function"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@ page session="true"%>


<div class="content-left">
   <div class="left">
     <div class="left-in">

     </div>
   </div>
 
   <div class="content-in">
   	<div class="cont-tit">
   	  <div>사용자 관리</div>
    </div>
    <form id="paramForm" method="post">
		<input type="hidden" id="pageNumber" name="pageNumber"/>
		<input type="hidden" id="rowsPerPage" name="rowsPerPage"/>
		<input type="hidden" id="totalPage" name="totalPage"/>
		<input type="hidden" id="userId" name="empNo" value="${sessionScope.SESSION_MAP.USER_ID}"/>
		<input type="hidden" id="mode"  name="mode" />
		<input type="hidden" id="userNo"  name="userNo" />
		<input type="hidden" id="chkID"  name="chkID" />
		
    <table class="col_table wfull">
      <colgroup>
          <col width="15%" />
          <col width="35%" />
          <col width="15%" />
          <col width="35%" />
      </colgroup>
      <tbody>
      	<tr>
      		<th>아이디</th>
      		<td class="t_left" colspan="3">
      			<input class="input w287" type="text" id="userID" name="userID" value="" />
      			<div class="btn btn-xs btn-gray mar w100" id="chkBtn" style="cursor: pointer; margin-left: 20px" onClick="javascript: dbCheck();" >중복확인</div>
      		</td>
      	</tr>
      	
      	<tr>
      		<th>비밀번호</th>
      		<td class="t_left">
      			<input class="input w287" type="password" id="passwd" name="passwd" value="" />
      		</td>
      		<th>비밀번호 확인</th>
      		<td class="t_left">
      			<input class="input w287" type="password" id="passwordChk" name="passwordChk" value="" />
      		</td>
      	</tr>
      	
      	<tr>
      		<th>이름</th>
      		<td class="t_left">
      			<input class="input w287" type="text" id="userNm" name="userNm" value="" />
      		</td>
      		<th>소속</th>
      		<td class="t_left">
      			<input class="input w287" type="text" id="userOrg" name="userOrg" value="" />
      		</td>
      	</tr>
      	
      	<tr>
      		<th>사용구분</th>
      		<td class="t_left">
      			<label><input style="margin : 0 10px" type="radio" name="userGb" value="M" />마스터</label>
                <label><input style="margin : 0 10px" type="radio" name="userGb" value="G" />게스트</label>
      		</td>
      		<th>사용여부</th>
      		<td class="t_left">
      			<label><input style="margin : 0 10px" type="radio" name="useYn" value="Y" />사용</label>
                <label><input style="margin : 0 10px" type="radio" name="useYn" value="N" />정지</label>
      		</td>
      	</tr>
      	
      	<tr>
      		<th>메모</th>
      		<td class="t_left" colspan="3">
      			<input class="input wfull" type="text" id="userMemo" name="userMemo" value="" />
      		</td>
      	</tr>
      	
      </tbody>
     </table>
     
     </form>
     
     <div class="btn_area right">
         <a href="/user/userList.do" class="btn btn-xs btn-gray w100 mar">
             취소
         </a>
         <button id="saveBtn" class="btn btn-xs btn-blue w100" style="cursor: pointer" >
             저장
         </button>
     </div>
   </div>
</div>
 
<script type="text/javascript">
$(document).ready(function(){
	$("#saveBtn").click(function(){
		let checkVal = handleValidation();
        if(checkVal){
            if (window.confirm("저장 하시겠습니까?")) {
            	saveUser();
            }
        }
        
	});
	
	init();
});

function init(){
	$("#mode").val("${param.mode}");
	$("#userNo").val("${param.userNo}");
	let mode = $('#paramForm').find("#mode").val();
	let userNo = $('#paramForm').find("#userNo").val();
	
	console.log(mode);
	
	if(mode=="N"){
		$(":radio[name='userGb'][value='G']").attr('checked', true);
		$(":radio[name='useYn'][value='Y']").attr('checked', true);
	}else{
		selectUser(userNo);
	} 
} 

function selectUser(userNo){
	let user_No = $('#paramForm').find("#userNo").val();
	let params={};
	params.USER_NO = user_No;
	$.ajax({
		url:'/api/user/selectUserInfo.json',
		type: 'POST',
		data: params,
		dataType : 'json',
		success:function(data){		
			var rsData = data.mResultMap.result;
			console.log(rsData);
			
			$("#chkBtn").hide();
			$('#paramForm').find("#userID").attr("disabled",true); 
			
			$('#paramForm').find("#userID").val(rsData.USER_ID);
			$('#paramForm').find("#userNm").val(rsData.USER_NM);
			$('#paramForm').find("#userOrg").val(rsData.USER_ORG);
			$(":radio[name='userGb'][value='"+rsData.USER_GB+"']").attr('checked', true);
			$(":radio[name='useYn'][value='"+rsData.USE_YN+"']").attr('checked', true);
			$('#paramForm').find("#userMemo").val(rsData.USER_MEMO);
			$("#chkID").val("Y");
			
			
		}
	});	
}

//아이디 중복확인
function dbCheck(){
	let userID = $('#paramForm').find("#userID").val();
	if($('#paramForm').find("#userID").val().length == 0){
		alert("아이디를 입력해주세요.");
        return false;
	}
	
	let params={};
	params.USER_ID = userID;
	$.ajax({
		url:'/api/user/selectUserId.json',
		type: 'POST',
		data: params,
		dataType : 'json',
		success:function(data){		
            if(data.mResultMap.resCode!='0000'){
            	if(data.mResultMap.resCode=='9999'){
            		document.location.href = "/";
            	}else{
            		alert(data.mResultMap.resMessage);
    				return;
            	}
			}
            let cnt = data.mResultMap.checkIdCnt;
            if(cnt == 0){
            	$("#chkID").val("Y");
            	alert("사용가능한 아이디입니다.");
            }else{
            	$("#chkID").val("N");
            	alert("이미 사용중인 아이디입니다.\n 아이디를 수정해주세요.");
            }
		}
	});	
}

function handleValidation(){
	let formIsValid = true;
	if($('#paramForm').find("#userID").val().length == 0){
        alert("아이디를 입력해주세요.");
        formIsValid = false;
        $('#paramForm').find("#userID").focus();
        return false;
    }
	if($('#paramForm').find("#chkID").val().length == 0){
        alert("아이디 중복확인을 해주세요.");
        formIsValid = false;
        $('#paramForm').find("#userID").focus();
        return false;
    }
	if($('#paramForm').find("#chkID").val() == "N"){
        alert("사용할수 없는 아이디입니다.");
        formIsValid = false;
        $('#paramForm').find("#userID").focus();
        return false;
    }
	/* 	
	let passwd = $('#paramForm').find("#passwd").val();
	let passwordChk = $('#paramForm').find("#passwordChk").val();
	if(passwd.length == 0){
        alert("비밀번호를 입력해주세요.");
        formIsValid = false;
        return false;
    }
    if(passwd.length < 8){
        alert("비밀번호는 8자이상 입력해주세요.");
        formIsValid = false;
        return false;
    }
    if(passwd !== passwordChk){
        alert("비밀번호가 일치하지 않습니다.");
        formIsValid = false;
        return false;
    }     

    let pattern1 = /[0-9]/; 
    let pattern2 = /[a-zA-Z]/; 
    let pattern3 = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; 
    let pattern4 = /[~!@#\#$%<>^&*]/; 

    if (!pattern1.test(data.passwd)) {
        alert("비밀번호는 숫자를 포함하여 조합하여야 합니다."); 
        formIsValid = false;
        return false;
    }

    if (!pattern2.test(data.passwd)) {
        alert("비밀번호는 영문자를 포함하여 조합하여야 합니다."); 
        formIsValid = false;
        return false;
    }



    if (!pattern4.test(data.passwd)) {
        alert("비밀번호는 특수문자(~,!,@,#,\,#,$,%,<,>,^,&,*)를 포함하여 조합하여야 합니다."); 
        formIsValid = false;
        return false;
    }
 */
     
	if($('#paramForm').find("#userNm").val().length == 0){
        alert("이름을 입력해주세요.");
        formIsValid = false;
        $('#paramForm').find("#userNm").focus();
        return false;
    }
	if($('#paramForm').find("#userOrg").val().length == 0){
        alert("소속을 입력해주세요.");
        formIsValid = false;
        $('#paramForm').find("#userOrg").focus();
        return false;
    }
	return formIsValid;
}

function saveUser(){
	var formArrays = $('#paramForm').serializeArray(); // form의 입력데이터를 배열의 Object형태로 만들어준다.
    var params = formArrays;
    console.log("// / " +params);

	var url= "/api/user/saveUser.json";  
	$.ajax({
		url:url,
		type: 'POST',
		data: params,
		dataType : 'json',
		success:function(data){		
        	if(data.mResultMap.resCode!='0000'){
            	if(data.mResultMap.resCode=='9999'){
            		document.location.href = "/";
            	}else{
            		alert(data.mResultMap.resMessage);
    				return;
            	}
			}
        	
        	alert("저장되었습니다.");
        	var url = "/user/userList.do";
        	$('#paramForm').attr("action",url);
        	$('#paramForm').submit();
		   

		}
	});
}
</script>
 
 
 
 