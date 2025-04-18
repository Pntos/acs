/**
 * common calendar
 * ex) openCalendar("search_start_date", "search_end_date");
 * ex) openCalendar("search_start_date", "search_end_date", "2018-07-10");
 * ex) openCalendar("search_start_date", "search_end_date", "2018-07-10", "2018-07-13");
 */
function openCalendar(startId, endId, startDt, endDt){
	var Tday = new Date();
	var Fday = add_date("d", 0, getDate(), "-")
	
	if(startDt == undefined){
		startDt = Fday;
	}
	if(endDt == undefined){
		endDt = Tday;
	}
	
	$("#"+startId).datepicker({
		showMonthAfterYear:true,
		changeYear:true,
		changeMonth:true,
		dateFormat: 'yy-mm-dd',
		yearSuffix:'년',
		monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNamesMin:['일','월','화','수','목','금','토'],
		onClose: function( selectedDate ) {
			// 시작일(fromDate) datepicker가 닫힐때
			// 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
			$("#"+endId).datepicker( "option", "minDate", selectedDate );
		}
	}).datepicker('setDate', startDt);
	$("#"+endId).datepicker({
		showMonthAfterYear:true,
		changeYear:true,
		changeMonth:true,
		dateFormat: 'yy-mm-dd',
		yearSuffix:'년',
		monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNamesMin:['일','월','화','수','목','금','토'],
		onClose: function( selectedDate ) {
			// 시작일(fromDate) datepicker가 닫힐때
			// 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
			 $("#"+startId).datepicker( "option", "maxDate", selectedDate );
		}
	}).datepicker( 'setDate', endDt );
}

function openCalendarSingle(startId, startDt){
	var Tday = new Date();
	
	if(startDt == undefined){
		startDt = Tday;
	}else{
		startDt = dateChk(startDt);
	}
	
	$("#"+startId).datepicker({
		showMonthAfterYear:true,
		changeYear:true,
		changeMonth:true,
		dateFormat: 'yy-mm-dd',
		yearSuffix:'년',
		monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNamesMin:['일','월','화','수','목','금','토'],
		onClose: function( selectedDate ) {
		}
	}).datepicker('setDate', startDt);
}

function dateChk(day){
	if(day.length < 10){
		var stYYYY = day.substring(0,4);
		var sTMM = day.substring(4,6);
		var stDD = day.substring(6,8);
		day = stYYYY+'-'+sTMM+'-'+stDD;
	}
	return day;
}

