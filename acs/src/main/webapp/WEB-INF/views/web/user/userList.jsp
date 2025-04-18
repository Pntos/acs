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
      <div class="ct-btn">
      	<a href="javascript: add();" class="btn btn-xs btn-blue w100">
           등록
        </a>
      </div>
    </div>
    <div class="d-cont">
    	<!-- SearchForm -->
    	<form id="paramForm" method="post">
			<input type="hidden" id="pageNumber" name="pageNumber"/>
			<input type="hidden" id="rowsPerPage" name="rowsPerPage"/>
			<input type="hidden" id="totalPage" name="totalPage"/>
			<input type="hidden" id="userId" name="empNo" value="${sessionScope.SESSION_MAP.USER_ID}"/>
			<input type="hidden" id="mode"  name="mode"/>
			<input type="hidden" id="userNo"  name="userNo"/>
		</form>

		<div class="search-box">
      		<div class="field">
           	<div class="th">검색</div>
               <div class="td xmar">
               	<select id="serachGb" name="serachGb" style="margin-right: 10px;">
               		<option value="">선택</option>
               		<option value="USER_ID">아이디</option>
               		<option value="USER_NM">이름</option>
               	</select>
               	<input type="text" class="input w590" id="searchTxt" value="" />
               </div>
               <button class="btn btn-xs btn-blue w100 mar" id="searchBtn" style="cursor: pointer" >
               	검색
               </button>
                  
           	</div>    
       		</div>


        <div>
        	<table id="listTab" class="col_table wfull">
        		<colgroup>
		          <col width="8%" />
		          <col width="10%" />
		          <col width="10%" />
		          <col width="20%" />
		          <col width="10%" />
		          <col width="10%" />
		          <col width="10%" />
		          <col width="*" />
		        </colgroup>
		        <thead>
		          <tr>
		            <th class="t_center">번호</th>
		            <th class="t_center">아이디</th>
		            <th class="t_center">이름</th>
		            <th class="t_center">소속</th>
		            <th class="t_center">구분</th>
		            <th class="t_center">등록일</th>
		            <th class="t_center">사용여부</th>
		            <th class="t_center">관리</th>
		          </tr>
		        </thead>
        		<tbody></tbody>
        	</table>
         	<!-- 페이징 버튼 영역 시작 -->
            <div class="btn_row">
                <div class="pager"></div>
            </div>
            <!-- 페이징 버튼 영역 끝 -->
        </div>

    </div>
   </div>
 </div>
 
<script type="text/javascript">
$(document).ready(function(){
	$("#searchBtn").click(function(){
		selectUserList();
	});
	init();
});

function init(){
	pageNumber = $("#paramForm [name='pageNumber']").val();
	rowsPerPage = $("#paramForm [name='rowsPerPage']").val();
	
	if(pageNumber=="")  $("#paramForm [name='pageNumber']").val("1");
	if(rowsPerPage=="") $("#paramForm [name='rowsPerPage']").val("10");
	
	selectUserList();	
}

function add(){
	let url = "/user/userEdit.do";
	$('#paramForm').find("#mode").val("N");
	$('#paramForm').attr("action",url);
	$('#paramForm').submit();
}


function selectUserList(){
	let params = $("#paramForm").serializeObject();
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
            getCommPaging(rsPaging.pageNumber, rsPaging.rowsPerPage, rsPaging.totalPage, "callBackFn");

		}
	});	
}

function tableList(rsData, rsPaging){
	$("#listTab").find("tbody").html("");
	let html = [], h = -1;
	
	if(rsData.length == 0){
		html[++h] = "<tr><td colspan='8'>등록된 게시물이 없습니다.</td></tr>";
	}else{
	
		var totalRows = (rsPaging.totalCnt - rsPaging.startPage)+1;
		for (var i in rsData) {
			rsData[i].NO = totalRows;
    		totalRows--;
			
			html[++h] = '<tr >';
			html[++h] = "<td>"+rsData[i].RNUM+"</td>";
			html[++h] = "<td>"+rsData[i].USER_ID+"</td>";
			html[++h] = "<td>"+rsData[i].USER_NM+"</td>";
			html[++h] = "<td>"+rsData[i].USER_ORG+"</td>";
			html[++h] = "<td>"+rsData[i].USER_GB+"</td>";
			html[++h] = "<td>"+rsData[i].ISRT_DTM+"</td>";
			html[++h] = "<td>"+rsData[i].USE_YN+"</td>";
			html[++h] = "<td>";
			html[++h] = "<div class='btn btn-xs btn-inline btn-default w70' onClick='javascript: itemDelete(item.memberNo)' style='cursor: pointer; margin-right: 5px;'>memo</div>";
			html[++h] = "<div class='btn btn-xs btn-inline btn-default w70' onClick='javascript: editUser("+rsData[i].USER_NO+")' style='cursor: pointer; margin-right: 5px;'>수정</div>";
			html[++h] = "<div class='btn btn-xs btn-inline btn-default w70' onClick='javascript: deleteUser("+rsData[i].USER_NO+")' style='cursor: pointer'>삭제</div>";
			html[++h] = "</td>";

			html[++h] = "</tr>";
		}
	}
	$("#listTab").find("tbody").html(html.join(''));		
}

function callBackFn(){
	selectUserList();
}

function editUser(No){
	let url = "/user/userEdit.do";
	$('#paramForm').find("#mode").val("E");
	$('#paramForm').find("#userNo").val(No);
	$('#paramForm').attr("action",url);
	$('#paramForm').submit();	
}

function deleteUser(No){
	let params={};
	params.userNo = No;
	params.delYn = "Y";
	
	$.ajax({
		url:'/api/user/deleteUser.json',
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

            alert("사용자가 삭제되었습니다.");
            selectUserList();
		}
	});	
}
</script>
 
 
 
 