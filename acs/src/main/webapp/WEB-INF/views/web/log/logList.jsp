<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="com.acs.util.Function"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@ page session="true"%>
 
<div class="sub_tit">시스템로그</div>
<!-- <div class="content">
            <div class="content-tab">
              <div class="tab" id="tab1" onClick="javascript: pageTab(1);" style="cursor: pointer;">전체</div>    
              <div class="tab" id="tab2" onClick="javascript: pageTab(2);" style="cursor: pointer;">접속</div>   
              <div class="tab" id="tab3" onClick="javascript: pageTab(3);" style="cursor: pointer;">영상처리</div>   
              <div class="tab" id="tab4" onClick="javascript: pageTab(4);" style="cursor: pointer;">전문처리</div>   
            </div>
        </div> -->
<div class="search_box">
   <form id="paramForm" method="post">
	<input type="hidden" id="pageNumber" name="pageNumber"/>
	<input type="hidden" id="rowsPerPage" name="rowsPerPage"/>
	<input type="hidden" id="totalPage" name="totalPage"/>
	<input type="hidden" id="userId" name="empNo" value="${sessionScope.SESSION_MAP.USER_ID}"/>
	<input type="hidden" id="key"  name="key"/>
	<input type="hidden" id="logGb"  name="logGb"/>
			
   <div class="item">
      <div class="th">기간<span class="bar"></span></div>
      <div class="td">
         <div class="calendar_box">
            <div class="calendar_wrap">
               <input type="text" class="input" id="fromDate" name="fromDate">
               <a href="#" class="cal"><img src="/img/ico_calendar.png" alt=""></a>
            </div>
            <span class="line">~</span>
            <div class="calendar_wrap">
               <input type="text" class="input" id="toDate" name="toDate">
               <a href="#" class="cal"><img src="/img/ico_calendar.png" alt=""></a>
            </div>
         </div>
      </div>
      <div class="btn">
         <input type="submit" class="submit mar" value="검색">
         <button type="button" class="submit reset">초기화</button>
      </div>
   </div>
   </form>
</div>

<div class="panel">
   <table class="d_table lg" id="listTab">
      <colgroup>
         <col width="5%">
         <col width="10%">
         <col width="*">
         <col width="15%">
      </colgroup>
      <thead>
      	<tr>
            <th class="tcenter">NO</th>
            <th class="tcenter">분류</th>
            <th class="tcenter">내역</th>
            <th class="tcenter">등록</th>
         </tr>
      </thead>
      <tbody></tbody>
   </table>

   <div class="page_wrap">
      <div class="page_nation">
      </div>
   </div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	$("#searchBtn").click(function(){
		selectLogList();
	});
	
	//openCalendar("fromDate", "fromDate", "", "");
	//openCalendar("toDate", "toDate", "", "");
	
	init();
});

function init(){
	pageNumber = $("#paramForm [name='pageNumber']").val();
	rowsPerPage = $("#paramForm [name='rowsPerPage']").val();
	
	if(pageNumber=="")  $("#paramForm [name='pageNumber']").val("1");
	if(rowsPerPage=="") $("#paramForm [name='rowsPerPage']").val("10");
	
	//$("#tab1").trigger('click')
	selectLogList();	
}

function pageTab(s){
	$("#key").val(s);
	$(".content-tab div").attr("class", "tab");
	$("#tab"+s).attr("class", "tab on");
	
	switch (s){
	    case 1 :
	    	$("#logGb").val("");
	        break;
	    case 2 :
	    	$("#logGb").val("LIO");
	        break;
	    case 3 :
	    	$("#logGb").val("VRM");
	        break;
	    case 4 :
	    	$("#logGb").val("API");
	        break;
	    default : 
	        break;
	}
  	selectLogList();		
}

function selectLogList(){
	let params = $("#paramForm").serializeObject();
	console.log(params);
	$.ajax({
		url:'/api/log/selectLogList.json',
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
		html[++h] = "<tr><td colspan='4'>등록된 게시물이 없습니다.</td></tr>";
	}else{
		var totalRows = (rsPaging.totalCnt - rsPaging.startPage)+1;
		for (var i in rsData) {
			rsData[i].NO = totalRows;
    		totalRows--;
    		let logGbTxt = "";
    		switch(rsData[i].LOG_GB) {
	  		  case 'LIO':  logGbTxt = "로그인";
	  			  	     break;
	  		  case 'LGO':  logGbTxt = "로그아웃";
			  	     		 break;
	  		  case 'C':  logGbTxt = "<div class='state cancel'>Cancle</div>";
			  	     		 break;
	  		  default:
			  	     		break;
	  		}
			
			html[++h] = '<tr >';
			html[++h] = "<td class='tcenter'>"+rsData[i].RNUM+"</td>";
			html[++h] = "<td class='tcenter'>로그인/로그아웃</td>";
			html[++h] = "<td class='tcenter'>"+rsData[i].LOG_MEMO+"</td>";
			html[++h] = "<td class='tcenter'>"+rsData[i].ISRT_DTM+"</td>";
			html[++h] = "</tr>";			
		}
	}
	$("#listTab").find("tbody").html(html.join(''));		
}

function callBackFn(){
	selectLogList();
}
</script>






