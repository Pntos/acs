<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="com.acs.util.Function"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@ page session="true"%>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<c:set var="date" value="<%=new Date() %>"/>
<fmt:formatDate value="${date}" var="toDate" pattern="yyyy-MM-dd"/>
  
<!-- content -->
<div class="state_box">  
	<div class="item item1">
	   <div>
	      <div class="t1">전체</div>
	      <div class="t2" id="total"></div>
	   </div>
	</div>
	<div class="item item2">
	   <div>
	      <div class="t1">정상</div>
	      <div class="t2" id="sNo"></div>
	   </div>
	</div>
	<!-- 
	<div class="item item3">
	   <div>
	      <div class="t1">Remove</div>
	      <div class="t2" id="rNo"></div>
	   </div>
	</div>
	 
	<div class="item item4">
	   <div>
	      <div class="t1">Cancel</div>
	      <div class="t2" id="cNo"></div>
	   </div>
	</div>
	-->
</div>


<form id="paramForm" method="post">
<input type="hidden" id="pageNumber" name="pageNumber"/>
<input type="hidden" id="rowsPerPage" name="rowsPerPage"/>
<input type="hidden" id="totalPage" name="totalPage"/>
<input type="hidden" id="userId" name="empNo" value="${sessionScope.SESSION_MAP.USER_ID}"/>
<input type="hidden" id="mode"  name="mode"/>
<input type="hidden" id="VO_NO"  name="VO_NO"/>

<!-- Search FORM -->
<div class="search_box">		
   <!-- 
   <div class="item mar">
      <div class="th">상태<span class="bar"></span></div>
      <div class="td">
         <div class="iradio_wrap">
         	<div class="iradio mar">
               <input type="radio" id="z1" name="voState" value="">
               <label for="z1">ALL</label>
            </div>
            <div class="iradio mar">
               <input type="radio" id="z2" name="voState" value="S">
               <label for="z2">정상</label>
            </div>
        
            <div class="iradio mar">
               <input type="radio" id="z3" name="voState" value="R">
               <label for="z3">Remove</label>
            </div>
   
            <div class="iradio">
               <input type="radio" id="z4" name="voState" value="C">
               <label for="z4">Cancel</label>
            </div>
         </div>
      </div>
   </div>
   //-->
   <div class="item mar">
      <div class="th">오더타입<span class="bar"></span></div>
      <div class="td">
         <div class="iradio_wrap">
         	<div class="iradio mar">
               <input type="radio" id="x1" name="orderType" value="">
               <label for="x1">ALL</label>
            </div>
            <div class="iradio mar">
               <input type="radio" id="x2" name="orderType" value="SHP">
               <label for="x2">정상</label>
            </div>
            <div class="iradio mar">
               <input type="radio" id="x3" name="orderType" value="RTN">
               <label for="x3">반품</label>
            </div>
         
         </div>
      </div>
   </div>
   <div class="item mar">
      <div class="th">기간<span class="bar"></span></div>
      <div class="td">
         <div class="calendar_box">
            <div class="calendar_wrap">
               <input type="text" class="input" id="fromDate" name="fromDate">
               <span class="cal" id="js-calendar-from" ><img src="/img/ico_calendar.png" alt=""></span>
            </div>
            <span class="line">~</span>
            <div class="calendar_wrap">
               <input type="text" class="input" id="toDate" name="toDate">
               <a href="#" class="cal"><img src="/img/ico_calendar.png" alt=""></a>
            </div>
         </div>
      </div>
   </div>
   <div class="item">
      <div class="th">오더번호<span class="bar"></span></div>
      <div class="td">
         <input type="text" class="dinput w300" id="orderNo" name="orderNo">
      </div>
   </div>
   <div class="btn">
      <!-- <input type="submit" class="submit mar" value="검색"> -->
      <button type="button" id="searchBtn" class="submit mar">검색</button>
      <button type="button" id="resetBtn" class="submit reset">초기화</button>
   </div>
</div>
<!-- Search FORM -->
</form>

<div class="row">
   <div class="col col-left">
      <div class="vod_list_scroll">
         <div class="vod_list" id="listTab">
         </div>
      </div>
      <div class="page_wrap">
         <div class="page_nation">
         </div>
      </div>
   </div>
   <div class="col col-right">
      <div class="vod_area">
      	<video
		    id="my-player"
		    class="video-js vjs-big-play-centered"
		    autoplay loop controls
		    preload="auto"
		    poster="/img/vod.png"
		    data-setup='{"controls": true, "fluid": true, "autoplay": false, "muted": true, "playbackRates": [0.5, 1, 1.5, 2]}'>
		</video>   
      </div>
      <div class="vod_info_wrap">
         <div class="vod_info" id="viewInfo"></div>   
      </div>
   </div>
</div>
<!-- content -->
  
<script type="text/javascript">
	var options = {};
	
	var player = videojs('my-player', options, function onPlayerReady() {
	  videojs.log('Your player is ready!');
	  // In this context, `this` is the player that was created by Video.js.
	  this.play();
	  // How about an event listener?
	  this.on('ended', function() {
	    videojs.log('Awww...over so soon?!');
	  });
	});

	$(document).ready(function(){
		$.datepicker.setDefaults({
            dateFormat: 'yy-mm-dd' //Input Display Format 변경
            ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
            ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
            ,changeYear: true //콤보박스에서 년 선택 가능
            ,changeMonth: true //콤보박스에서 월 선택 가능                
            ,showOn: "focus" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
            ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
            ,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
            ,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
            ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
            ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
            ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
            ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
            ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
            //,minDate: "-1M" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
            //,maxDate: "+1M" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)                    
        });
		
		$("#fromDate").datepicker(); 
		$("#toDate").datepicker(); 
		
		$("#vBtn").click(function(){
			let url = "/order/orderVedio.do";
			$('#paramForm').attr("action",url);
			$('#paramForm').submit();	
		});
		
		$("#searchBtn").click(function(){
			$("#paramForm [name='pageNumber']").val("1");
			$("#paramForm [name='rowsPerPage']").val("10");
			
			const player = videojs('my-player');
			player.hasStarted(false);
			player.currentTime(0);
			player.trigger("ready");
			
			$("#viewInfo").html("");
			selectOrderList();
		});
		
		$("#resetBtn").click(function(){
			$('#paramForm')[0].reset();
		
			let today = "${toDate}";
			let mSerachDate = searchDate('3'); // date.js
			
			$("#fromDate").val(mSerachDate);
			$("#toDate").val(today);
			
		/* 
			var params = {};
			var url= "/api/ffmpeg/selectMpegTest2.json";  
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
		        	console.log(data);
		        	
				}
			});
		 */
			
			
		});
		init();
	});
	
	function init(){
		let today = "${toDate}";
		let mSerachDate = searchDate('3'); // date.js
		
		$("#fromDate").val(mSerachDate);
		$("#toDate").val(today);
		
		pageNumber = $("#paramForm [name='pageNumber']").val();
		rowsPerPage = $("#paramForm [name='rowsPerPage']").val();
		if(pageNumber=="")  $("#paramForm [name='pageNumber']").val("1");
		if(rowsPerPage=="") $("#paramForm [name='rowsPerPage']").val("10");
		
		//$(":radio[name='voState'][value='']").attr('checked', true);
		$(":radio[name='orderType'][value='']").attr('checked', true);
		selectOrderList();	
	}
	
	function selectOrderList(){
		let params = $("#paramForm").serializeObject();
		params.DEL_YN = "N";
		$.ajax({
			url:'/api/order/selectOrderList.json',
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
	            
	            let rsCnt = data.mResultMap.cntMap;
	            $("#total").text(addCommas(rsCnt.TOTAL));
	            $("#sNo").text(addCommas(rsCnt.SNO));
	            //$("#rNo").text(addCommas(rsCnt.RNO));
	            $("#cNo").text(addCommas(rsCnt.CNO));
	            
	            let rsParams = data.mResultMap.params;
	            let rsData = data.mResultMap.result;
	            let rsPaging = data.mResultMap.page;
	
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
			html[++h] = "<div class='item'><font color='white'>등록된 게시물이 없습니다.</font></div>";
		}else{
			let totalRows = (rsPaging.totalCnt - rsPaging.startPage)+1;
			for (var i in rsData) {
				rsData[i].NO = totalRows;
	    		totalRows--;
	    		let voWmvNo = rsData[i].VO_WMS_NO
	    		//S: 정상 / R: REMOVE / C: CANCLE
	    		let voState = rsData[i].VO_STATE;
	    		let stateTxt = "<div class='state normal'>정상</div>";
	    		/*
	    		switch(voState) {
	    		  case 'S':  stateTxt = "<div class='state normal'>정상</div>";
	    			  	     break;
	    		  case 'R':  stateTxt = "<div class='state remove'>Remove</div>";
			  	     		 break;
	    		  case 'C':  stateTxt = "<div class='state cancel'>Cancle</div>";
			  	     		 break;
	    		  default:
			  	     		break;
	    		}
	    		*/
	    		let voNo = rsData[i].VO_NO;
	    		let vwNo = rsData[i].VW_NO;
				
				html[++h] = "<div class='item' data-id='" + i + "' orderNo='"+rsData[i].ORDER_NO+"' >";
				html[++h] = stateTxt;
				html[++h] = "<div class='tit'>" + rsData[i].ORDER_NO + "</div>";
				html[++h] = "<div class='time'>";
				html[++h] = "<div class='i_sc'><span>S</span>" + rsData[i].START_DATE + "</div>";
				html[++h] = "<div class='i_sc'><span>C</span>" + rsData[i].END_DATE + "</div>";
				html[++h] = "</div>";
				
				html[++h] = '<div class="play">';
				
				if($.trim(voNo).length == 0){
					html[++h] = '<div class="play"><a href="javascript:alert(\'해당 영상이 없습니다.\');"><img src="/img/ico_play.png" alt="play">';	
					html[++h] = '&nbsp;&nbsp;&nbsp;<a href="javascript:alert(\'해당 영상이 없습니다.\');"><img src="/image/ico_file.png" alt="file"></a></div>';
				}else{
					//html[++h] = '<a href="javascript: watchVdo(\''+vwNo+'\')"><img src="/img/ico_play.png" alt="play"></a>';
					html[++h] = '<a href="javascript: watchVdo(\''+voWmvNo+'\',\''+rsData[i].ORDER_NO+'\',\''+rsData[i].START_DATE+'\',\''+rsData[i].END_DATE+'\',\''+rsData[i].ISRT_DTM+'\',\''+voState+'\')"><img src="/img/ico_play.png" alt="play"></a>';
					html[++h] = '&nbsp;&nbsp;&nbsp;<a href="javascript: commonFileDown(\''+voNo+'\')"><img src="/image/ico_file.png" alt="file"></a>';
				}
				
				html[++h] = '</div>';
				
				html[++h] = "</div>";
			}
		}
		$("#listTab").html(html.join(''));	
	}
	
	function callBackFn(){
		selectOrderList();
	}
	
	function watchVdo(voWmvNo, orderNo, sdate ,eDate, rdate, voState){
		/* let stateTxt2 = "";
		switch(voState) {
   		  case 'S':  stateTxt2 = "<span class='normal'>정상</span>";
   			  	     break;
   		  case 'R':  stateTxt2 = "<div class='remove'>Remove</div>";
	  	     		 break;
   		  case 'C':  stateTxt2 = "<div class='cancel'>Cancle</div>";
	  	     		 break;
   		  default:
	  	     		break;
   		} */
		
		$("#viewInfo").html("");
		let html = [], h = -1;	
		html[++h] = "<div class='item bar'>";
		html[++h] = "<div>";
		html[++h] = "<div class='t1'>오더 번호</div>";
		//html[++h] = "<div class='t3'>"+stateTxt2+"</div>";
		html[++h] = "<div class='t2'>"+orderNo+"</div>";
		html[++h] = "</div>";
		html[++h] = "</div>";
		html[++h] = "<div class='item bar'>";
		html[++h] = "<div>";
		html[++h] = "<div class='t1'>Start 시점</div>";
		html[++h] = "<div class='t2'>"+sdate+"</div>";
		html[++h] = "</div>";
		html[++h] = "</div>";
		html[++h] = "<div class='item bar'>";
		html[++h] = "<div>";
		html[++h] = "<div class='t1'>Complete 시점</div>";
		html[++h] = "<div class='t2'>"+eDate+"</div>";
		html[++h] = "</div>";
		html[++h] = "</div>";
		html[++h] = "<div class='item'>";
		html[++h] = "<div>";
		html[++h] = "<div class='t1'>등록일</div>";
		html[++h] = "<div class='t2'>"+rdate+"</div>";
		html[++h] = "</div>";
		html[++h] = "</div>";
		html[++h] = '<a href="javascript: goPlay(\''+voWmvNo+'\');" class="view">Player<br>보기</a>';
		html[++h] = "</div>";
		$("#viewInfo").html(html.join(''));	
		
		//파일가져오기
		let params = {}
		params.voWmvNo = voWmvNo;
		var url= "/api/common/commonOrgFileInfo.json";  
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
	        	
	        	let rs = data.mResultMap.result;
	        	const splitResult = rs.VO_FILEPATH.split('/');
	        	
	        	let moviPath = "/" + splitResult[2] + "/" + splitResult[3] + "/" + rs.VO_FILENAME;
	        	const player = videojs('my-player');
	    		player.src(moviPath);
	    		player.play();
	    		
			}
		});
	}
	
	function goPlay(voWmvNo){
		//파일가져오기
		let params = {}
		params.voWmvNo = voWmvNo;
		var url= "/api/common/commonOrgFileInfo.json";  
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
	        	
	        	let rs = data.mResultMap.result;
	        	const splitResult = rs.VO_FILEPATH.split('/');
	        	
	        	let moviPath = "/" + splitResult[2] + "/" + splitResult[3] + "/" + rs.VO_FILENAME;
	        	const player = videojs('my-player');
	    		player.src(moviPath);
	    		player.play();
	    		
			}
		});
		//const player = videojs('my-player');
		//player.src("/upload/sample-mp4-file.mp4");
		//player.play();
	}
	
	
	
</script>
 
 
 
 