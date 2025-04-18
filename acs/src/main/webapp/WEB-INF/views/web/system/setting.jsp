<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="com.acs.util.Function"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@ page session="true"%>
<!-- content -->
<div class="sub_tit">시스템 환경설정</div>

<div class="panel mb40">
   <form id="paramForm" method="post">
    <input type="hidden" id="userId" name="userId" value="${sessionScope.SESSION_MAP.USER_ID}"/>
    <input type="hidden" id="mode"  name="mode" value="${dataYn}" />
    <input type="hidden" id="systemNo"  name="systemNo" />	
   <div class="sub_stit">WMS 정보</div>
   <table class="d_table mb30">
      <colgroup>
        <col width="150">
        <col width="*">
        <col width="150">
        <col width="*">
        <col width="150">
        <col width="*">
     </colgroup>
     <tr>
        <th>URL</th>
        <td colspan="5">
        	<input class="input w950" type="text" id="wmsUrl" name="wmsUrl" value="${resultMap.WMS_URL}" />
        </td>
     </tr>
     <tr>
        <th>아이디</th>
        <td>
			<input class="input wfull" type="text" id="wmsId" name="wmsId" value="${resultMap.WMS_ID}" />
		</td>
        <th>비밀번호</th>
        <td>
			<input class="input wfull" type="text" id="wmsPw" name="wmsPw" value="${resultMap.WMS_PW}" />
		</td>
        <th>포트</th>
        <td>
        	<input class="input wfull" type="text" id="wmsPt" name="wmsPt" value="${resultMap.WMS_PORT}" />
        </td>
     </tr>
  </table>

  <div class="sub_stit">영상 API 정보</div>
  <table class="d_table mb30">
     <colgroup>
        <col width="150">
        <col width="*">
        <col width="150">
        <col width="*">
        <col width="150">
        <col width="*">
     </colgroup>
     <tr>
        <th>URL</th>
        <td colspan="5">
        	<input type="text" class="input w950" id="apiUrl" name="apiUrl" value="${resultMap.API_URL}" />
        </td>
     </tr>
     <tr>
        <th>아이디</th>
        <td>
        	<input class="input wfull" type="text" id="apiId" name="apiId" value="${resultMap.API_ID}" />
        </td>
        <th>비밀번호</th>
        <td>
        	<input class="input wfull" type="text" id="apiPw" name="apiPw" value="${resultMap.API_PW}" />
		</td>
        <th>포트</th>
        <td>
        	<input class="input wfull" type="text" id="apiPt" name="apiPt" value="${resultMap.API_PORT}" />
		</td>
     </tr>
  </table>

  <div class="sub_stit">시스템 정보</div>
  <table class="d_table mb20">
     <colgroup>
        <col width="150">
        <col width="*">
        <col width="150">
        <col width="*">
     </colgroup>
     <tr>
        <th>시작 시간</th>
        <td>
           <select id="sysStime" name="sysStime" class="select w200">
           	<option value="">선택</option>
            <c:forEach var="i"  begin="1" end="6">
		        <option value="${i*10}" <c:if test="${i*10 == resultMap.SYS_STIME}">selected</c:if> >${i*10}초</option>
		    </c:forEach>
           </select>
        </td>
        <th>녹화 시간</th>
        <td>
           <select id="sysEtime" name="sysEtime" style="margin-right: 10px;">
           	<option value="">선택</option>
           	<c:forEach var="i"  begin="1" end="20">
		        <option value="${i}" <c:if test="${i == resultMap.SYS_ETIME}">selected</c:if> >${i}분</option>
		    </c:forEach>
           </select>
        </td>
     </tr>
     <tr>
        <th>API Polling 시간</th>
        <td>
           <select id="sysPtime" name="sysPtime" style="margin-right: 10px;">
           	<option value="">선택</option>
           	<c:forEach var="i"  begin="1" end="10">
		    	<option value="${i}" <c:if test="${i == resultMap.SYS_PTIME}">selected</c:if> >${i}분</option>
		    </c:forEach>
           </select>
        </td>
        <th>영상저장경로</th>
        <td>
        	<input type="text" class="input wfull" type="text" id="sysPath" name="sysPath" value="${resultMap.SYS_PATH}" />
        </td>
     </tr>
   </table>
   </form>
   	
   <div class="btn_submit">
      <button id="saveBtn" class="submit" style="cursor: pointer" >
         저장하기
     </button>
   </div>
</div>

<div class="half_wrap">
   <div class="half">
      <div class="panel h685">
         <div class="sub_stit">사용자 관리</div>
         <form id="userForm" method="post">
         <input type="hidden" id="chkID"  name="chkID" />
         <input type="hidden" id="uMode"  name="uMode" />
         <input type="hidden" id="userNo"  name="userNo" />
         
         <table class="d_table mb30">
            <colgroup>
              <col width="150">
              <col width="*">
              <col width="150">
              <col width="*">
           </colgroup>
           <tr>
           	<th>아이디</th>
           	<td colspan="3">
           		<input type="text" class="input w287" id="userID" name="userID" />
           		<button class="submit" id="chkBtn" style="cursor: pointer; margin-left: 20px">중복확인</button>
           	</td>
           </tr>
           <tr>
           	<th>비밀번호</th>
           	<td><input type="password" class="input wfull" id="passwd" name="passwd" /></td>
           	<th>비밀번호 확인</th>
           	<td><input type="password" class="input wfull" id="passwordChk" name="passwordChk" /></td>
           </tr>
           <tr>
           	<th>이름</th>
           	<td><input type="text" class="input wfull" id="userNm" name="userNm" /></td>
           	<th>소속</th>
           	<td><input type="text" class="input wfull" id="userOrg" name="userOrg" /></td>
           </tr>
           <tr>
           <th>사용구분</th>
           	<td colspan="3">
              <div class="iradio_wrap">
                 <div class="iradio mar">
                    <input type="radio" id="z6" name="userGb" value="M" />
                    <label for="z6">마스터</label>
                 </div>
                 <div class="iradio">
                    <input type="radio" id="z5" name="userGb" value="G" />
                    <label for="z5">게스트</label>
                 </div>
              </div>
           	</td>
           </tr>
           <tr>
           	<th>사용여부</th>
           	<td colspan="3">
	           	<div class="iradio_wrap">
	              <div class="iradio mar">
	                 <input type="radio" name="useYn" id="z1" value="Y">
	                 <label for="z1">사용</label>
	              </div>
	              <div class="iradio">
	                 <input type="radio" name="useYn" id="z3" value="N">
	                 <label for="z3">정지</label>
	              </div>
	            </div>
           	</td>
           </tr>
           <tr>
           	<th>메모</th>
           	<td colspan="3"><input type="text" class="input wfull" id="userMemo" name="userMemo"></td>
           </tr>    
        </table>
        </form>
        
        <div class="btn_submit mb50">
           <button id="userSaveBtn" class="submit" style="cursor: pointer" >
	         등록
	       </button>
        </div>
		
		<!-- 사용자 SearchForm -->
    	<form id="uSearchForm" method="post">
			<input type="hidden" id="pageNumber" name="pageNumber"/>
			<input type="hidden" id="rowsPerPage" name="rowsPerPage"/>
			<input type="hidden" id="totalPage" name="totalPage"/>
		</form>
        <table class="d_table mb30" id="listTab">
           <colgroup>
              <col width="25%">
              <col width="25%">
              <col width="25%">
              <col width="25%">
           </colgroup>
           <thead>
           <tr>
              <th class="tcenter">아이디</th>
              <th class="tcenter">소속</th>
              <th class="tcenter">이름</th>
              <th class="tcenter">관리</th>
           </tr>
           </thead>
           <tbody></tbody>
        </table>
        <div class="page_wrap">
           <div class="page_nation">
           </div>
        </div>
     </div>
  </div>
  
  <div class="half">
     <div class="panel h685">
        <div class="sub_stit">접속 허용 아이피</div>
        
        <form id="ipForm" method="post">
        <input type="hidden" id="iMode"  name="iMode" />
         <input type="hidden" id="ipNo"  name="ipNo" />
        <table class="d_table mb30">
           <colgroup>
              <col width="150">
              <col width="*">
           </colgroup>
           <tr>
              <th>아이피</th>
              <td colspan="3">
                 <div class="four_wrap">
                    <div class="fw"><input type="text" class="input wfull" id="ipAddr1" name="ipAddr1" maxlength="3"  oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" /></div>
                    <div class="fw"><input type="text" class="input wfull" id="ipAddr2" name="ipAddr2" maxlength="3"  oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" /></div>
                    <div class="fw"><input type="text" class="input wfull" id="ipAddr3" name="ipAddr3" maxlength="3"  oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" /></div>
                    <div class="fw"><input type="text" class="input wfull" id="ipAddr4" name="ipAddr4" maxlength="3"  oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" /></div>
                 </div>
              </td>
           </tr>
           <tr>
              <th>사용자</th>
              <td><input type="text" class="input wfull" id="ipUser" name="ipUser" /></td>
           </tr>
           <tr>
              <th>메모</th>
              <td><input type="text" class="input wfull" id="ipMemo" name="ipMemo" /></td>
           </tr>
        </table>
        </form>
        <div class="btn_submit mb50">
           <button id="ipSaveBtn" class="submit" style="cursor: pointer" >
	         등록
	       </button>
        </div>

		<!-- 사용자 SearchForm -->
    	<form id="searchForm" method="post">
			<input type="hidden" id="pageNumber2" name="pageNumber2"/>
			<input type="hidden" id="rowsPerPage2" name="rowsPerPage2"/>
			<input type="hidden" id="totalPage2" name="totalPage2"/>
		</form>
		
        <table class="d_table mb30" id="listTab2">
           <colgroup>
              <col width="33%">
              <col width="33%">
              <col width="34%">
           </colgroup>
            <tr>
               <th class="tcenter">아이피</th>
               <th class="tcenter">사용자</th>
               <th class="tcenter">관리</th>
            </tr>
            <tr>
               <td class="tcenter">swindow005262</td>
               <td class="tcenter">swindow005262</td>
               <td class="tcenter">
                  <div class="btn_wrap">
                     <a href="#" class="btn-purple mar">메모</a>
                     <a href="#" class="btn-blue mar">수정</a>
                     <a href="#" class="btn-red mar">삭제</a>
                  </div>
               </td>
            </tr>
            <tr>
               <td class="tcenter">swindow005262</td>
               <td class="tcenter">swindow005262</td>
               <td class="tcenter">
                  <div class="btn_wrap">
                     <a href="#" class="btn-purple mar">메모</a>
                     <a href="#" class="btn-blue mar">수정</a>
                     <a href="#" class="btn-red mar">삭제</a>
                  </div>
               </td>
            </tr>
         </table>
         <div class="page_wrap">
            <div class="page_nation2">
            </div>
         </div>
      </div>
   </div>
</div>

<!-- content -->

<script type="text/javascript">
$(document).ready(function(){
	$("#saveBtn").click(function(e){
      if (window.confirm("저장 하시겠습니까?")) {
      	saveSystem();
      }
      e.preventDefault();
	});	
	$("#userSaveBtn").click(function(e){
	  let checkVal = handleValidation();
      if(checkVal){
        if (window.confirm("저장 하시겠습니까?")) {
        	saveUser();
        }
      }
      e.preventDefault();
	});
	$("#ipSaveBtn").click(function(e){
	   let checkVal = handleValidation2();
       if(checkVal){
        if (window.confirm("저장 하시겠습니까?")) {
        	saveIp();
        }
       }
       e.preventDefault();
	});
	$("#chkBtn").click(function(e){
		 dbCheck();
		 e.preventDefault();
	});

	init();
});

function init(){
	$(":radio[name='userGb'][value='G']").attr('checked', true);
	$(":radio[name='useYn'][value='Y']").attr('checked', true);
	
	// 사용자관리
	pageNumber = $("#uSearchForm [name='pageNumber']").val();
	rowsPerPage = $("#uSearchForm [name='rowsPerPage']").val();
	
	if(pageNumber=="")  $("#uSearchForm [name='pageNumber']").val("1");
	if(rowsPerPage=="") $("#uSearchForm [name='rowsPerPage']").val("10");
	selectUserList();	
	
	// 접속아이피
	pageNumber2 = $("#searchForm [name='pageNumber2']").val();
	rowsPerPage2 = $("#searchForm [name='rowsPerPage2']").val();
	
	if(pageNumber2=="")  $("#searchForm [name='pageNumber2']").val("1");
	if(rowsPerPage2=="") $("#searchForm [name='rowsPerPage2']").val("10");
	selectIpList();	
}

//시스템정보 저장
function saveSystem(){
	var formArrays = $('#paramForm').serializeArray(); // form의 입력데이터를 배열의 Object형태로 만들어준다.
    var params = formArrays;
    console.log("// / " +params);

	var url= "/api/system/saveSystem.json";  
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
        	window.location.reload();
		}
	});
}

//아이디 중복확인
function dbCheck(){
	let userID = $('#userForm').find("#userID").val();
	if($('#userForm').find("#userID").val().length == 0){
		alert("아이디를 입력해주세요.");
		$('#userForm').find("#userID").focus();
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
	if($('#userForm').find("#userID").val().length == 0){
        alert("아이디를 입력해주세요.");
        formIsValid = false;
        $('#userForm').find("#userID").focus();
        return false;
    }
	if($('#userForm').find("#chkID").val().length == 0){
        alert("아이디 중복확인을 해주세요.");
        formIsValid = false;
        $('#userForm').find("#userID").focus();
        return false;
    } 
	if($('#userForm').find("#chkID").val() == "N"){
        alert("사용할수 없는 아이디입니다.");
        formIsValid = false;
        $('#userForm').find("#userID").focus();
        return false;
    }
	/* 	
	let passwd = $('#userForm').find("#passwd").val();
	let passwordChk = $('#userForm').find("#passwordChk").val();
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
     
	if($('#userForm').find("#userNm").val().length == 0){
        alert("이름을 입력해주세요.");
        formIsValid = false;
        $('#userForm').find("#userNm").focus();
        return false;
    }
	if($('#userForm').find("#userOrg").val().length == 0){
        alert("소속을 입력해주세요.");
        formIsValid = false;
        $('#userForm').find("#userOrg").focus();
        return false;
    }
	return formIsValid;
}

function saveUser(){
   	if($('#userForm').find("#userNo").val().length == 0){
   		$('#userForm').find("#uMode").val("N");
   	}else{
   		$('#userForm').find("#uMode").val("U");
   	}
   	
   	var formArrays = $('#userForm').serializeArray(); // form의 입력데이터를 배열의 Object형태로 만들어준다.
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
        	window.location.reload();
		}
	});
}

function selectUserList(){
	let params = $("#uSearchForm").serializeObject();
	params.DEL_YN = "N"
	console.log(params);
	$.ajax({
		url:'/api/user/selectUserList.json',
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
            
            var rsParams = data.mResultMap.params;
            var rsData = data.mResultMap.result;
            var rsPaging = data.mResultMap.page;

            tableList(rsData, rsPaging);		
            if(rsPaging.totalPage == 0){
				rsPaging.totalPage = 1;
			}
            getCommPaging(rsPaging.pageNumber, rsPaging.rowsPerPage, rsPaging.totalPage, "callBackFnUser");

		}
	});	
}

function callBackFnUser(){
	selectUserList();
}

function tableList(rsData, rsPaging){
	$("#listTab").find("tbody").html("");
	let html = [], h = -1;
	
	if(rsData.length == 0){
		html[++h] = "<tr><td colspan='4'>등록된 게시물이 없습니다.</td></tr>";
	}else{
	
		var totalRows = (rsPaging.totalCnt - rsPaging.startPage)+1;
		for (var i in rsData) {
			rsData[i].NO = totalRows;
    		totalRows--;
    		
    		let row = rsData[i];
    		html[++h] = "<tr>";
    		html[++h] = "<td class='tcenter'>"+rsData[i].USER_ID+"</td>";
    		html[++h] = "<td class='tcenter'>"+rsData[i].USER_ORG+"</td>";
    		html[++h] = "<td class='tcenter'>"+rsData[i].USER_NM+"</td>";
    		html[++h] = "<td class='tcenter'>";
    		html[++h] = "<div class='btn_wrap'>";
    		html[++h] = "<a href='#' class='btn-purple mar'>메모</a>";
    		html[++h] = "<a href='javascript: editUser(" + JSON.stringify(row) + ");' class='btn-blue mar'>수정</a>";
    		html[++h] = "<a href='javascript: delUser("+rsData[i].USER_NO+");' class='btn-red mar'>삭제</a>";
    		html[++h] = "</div>";
    		html[++h] = "</td>";
    		html[++h] = "</tr>";
	
		}
	}
	$("#listTab").find("tbody").html(html.join(''));		
}

function editUser(data){
	$("#chkBtn").hide();
	$('#userForm').find("#userNo").val(data.USER_NO);
	$('#userForm').find("#userID").val(data.USER_ID);
	$('#userForm').find("#userID").attr("disabled",true); 
	$('#userForm').find("#userNm").val(data.USER_NM);
	$('#userForm').find("#userOrg").val(data.USER_ORG);
	$(":radio[name='userGb'][value='"+data.USER_GB+"']").prop('checked', true);
	$(":radio[name='useYn'][value='"+data.USE_YN+"']").prop('checked', true);
	
	$('#userForm').find("#userMemo").val(data.USER_MEMO);
	$("#chkID").val("Y");
}

function delUser(No){
	let params ={};
	params.userNo = No;
	console.log("// / " +params);
   	
	var url= "/api/user/deleteUser.json";  
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
        	alert("삭제되었습니다.");
        	window.location.reload();
		}
	});
}


function handleValidation2(){
	let formIsValid = true;
	if($('#ipForm').find("#ipAddr1").val().length == 0){
        alert("아이피를 입력해주세요.");
        formIsValid = false;
        $('#ipForm').find("#ipAddr1").focus();
        return false;
    }
	if($('#ipForm').find("#ipAddr2").val().length == 0){
        alert("아이피를 입력해주세요.");
        formIsValid = false;
        $('#ipForm').find("#ipAddr2").focus();
        return false;
    }
	if($('#ipForm').find("#ipAddr3").val().length == 0){
        alert("아이피를 입력해주세요.");
        formIsValid = false;
        $('#ipForm').find("#ipAddr3").focus();
        return false;
    }
	if($('#ipForm').find("#ipAddr4").val().length == 0){
        alert("아이피를 입력해주세요.");
        formIsValid = false;
        $('#ipForm').find("#ipAddr4").focus();
        return false;
    }
	if($('#ipForm').find("#ipUser").val().length == 0){
        alert("사용자명을 입력해주세요.");
        formIsValid = false;
        $('#ipForm').find("#ipUser").focus();
        return false;
    }
	return formIsValid;
}

// 접속아이피 저장
function saveIp(){
   	if($('#ipForm').find("#ipNo").val().length == 0){
   		$('#ipForm').find("#iMode").val("N");
   	}else{
   		$('#ipForm').find("#iMode").val("U");
   	}
   
   	var formArrays = $('#ipForm').serializeArray(); // form의 입력데이터를 배열의 Object형태로 만들어준다.
    var params = formArrays;
   	console.log("// / " +params);
   	
	var url= "/api/system/saveIp.json";  
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
        	window.location.reload();
		}
	});
}

function selectIpList(){
	let params = $("#searchForm").serializeObject();
	params.IP_DELYN = "N"
	console.log(params);

	$.ajax({
		url:'/api/system/selectIpList.json',
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
            
            var rsParams = data.mResultMap.params;
            var rsData = data.mResultMap.result;
            var rsPaging = data.mResultMap.page;

            tableList2(rsData, rsPaging);		
            if(rsPaging.totalPage == 0){
				rsPaging.totalPage = 1;
			}
            getCommPaging2(rsPaging.pageNumber, rsPaging.rowsPerPage, rsPaging.totalPage, "callBackFnIp");

		}
	});	
}

function callBackFnIp(){
	selectIpList();
}

function tableList2(rsData, rsPaging){
	$("#listTab2").find("tbody").html("");
	let html = [], h = -1;
	
	if(rsData.length == 0){
		html[++h] = "<tr><td colspan='3'>등록된 게시물이 없습니다.</td></tr>";
	}else{
	
		var totalRows = (rsPaging.totalCnt - rsPaging.startPage)+1;
		for (var i in rsData) {
			rsData[i].NO = totalRows;
    		totalRows--;
    		
    		let row = rsData[i];
    		let ipAddr = rsData[i].IP_ADDR1 + "." + rsData[i].IP_ADDR2 + "." + rsData[i].IP_ADDR3 + "." + rsData[i].IP_ADDR4;
    		console.log(row); 
 
    		html[++h] = "<tr>";
    		html[++h] = "<td class='tcenter'>"+ipAddr+"</td>";
    		html[++h] = "<td class='tcenter'>"+rsData[i].IP_USER+"</td>";
    		html[++h] = "<td class='tcenter'>";
    		html[++h] = "<div class='btn_wrap'>";
    		html[++h] = "<a href='#' class='btn-purple mar'>메모</a>";
    		html[++h] = "<a href='javascript: editIp(" + JSON.stringify(row) + ");' class='btn-blue mar'>수정</a>";
    		html[++h] = "<a href='javascript: delIp("+rsData[i].IP_NO+");' class='btn-red mar'>삭제</a>";
    		html[++h] = "</div>";
    		html[++h] = "</td>";
    		html[++h] = "</tr>";

		}
	}
	$("#listTab2").find("tbody").html(html.join(''));		
}

function editIp(data){
	$('#ipForm').find("#ipNo").val(data.IP_NO);
	$('#ipForm').find("#ipAddr1").val(data.IP_ADDR1);
	$('#ipForm').find("#ipAddr2").val(data.IP_ADDR2);
	$('#ipForm').find("#ipAddr3").val(data.IP_ADDR3);
	$('#ipForm').find("#ipAddr4").val(data.IP_ADDR4);
	$('#ipForm').find("#ipUser").val(data.IP_USER);
	$('#ipForm').find("#ipMemo").val(data.IP_MEMO);
}

function delIp(No){
	let params ={};
	params.ipNo = No;
	
	var url= "/api/system/deleteIp.json";  
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
        	alert("삭제되었습니다.");
        	window.location.reload();
		}
	});
	
}





</script>




