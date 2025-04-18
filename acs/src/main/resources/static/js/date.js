/**
 *  creator : kim haejin
 *  new date : 2018.06.26
 *  file name : date.js
 *  file description : date 관련 공통 함수
 */

/*
 *  YYYYMMDD 와 같은 형식의 문자를 yyyy-MM-dd 으로 변경
 */
function hipenDate(date_str)
{
    var sYear = date_str.substring(0,4);
    var sMonth = date_str.substring(4,6);
    var sDate = date_str.substring(6,8);
    var rtnStr = sYear + sMonth + sDate;
    rtnStr = sYear +"-"+ sMonth +"-"+ sDate;

    return rtnStr;
} 

/*
 *  HHMMSS 와 같은 형식의 문자를 HH:MM:SS 으로 변경
 */
function hipenTime(time_str)
{
    var hh = time_str.substring(0,2);
    var mm = time_str.substring(2,4);
    var dd = time_str.substring(4,6);
    var rtnStr = hh + mm + dd;
    rtnStr = hh +":"+ mm +":"+ dd;

    return rtnStr;
} 

/*
 *  yyyy/MM/dd 와 같은 형식의 문자를 yyyyMMdd 으로 변경
 */
function delFix(date_str)
{
    var yyyyMMdd = String(date_str);
    var sYear = yyyyMMdd.substring(0,4);
    var sMonth = yyyyMMdd.substring(5,7);
    var sDate = yyyyMMdd.substring(8,10);
    var rtnStr = sYear + sMonth + sDate;
    rtnStr = rtnStr.split("/").join("");
    rtnStr = rtnStr.split("-").join("");
    rtnStr = rtnStr.split(" ").join("");

    return rtnStr;
} 

/**
 * 현재 날짜형 반환 (년)
 * @param
 * @returns YYYY
 */
function getDateYY()
{
	//날짜형식 yyyy 에 맞게 출력
	var obj_date = new Date();
	return obj_date.getFullYear();
}

/**
 * 현재 날짜형 반환 (년월일)
 * @param ch_set
 * @returns YYYY-MM-DD
 */
function getDateYYMMDD(ch_set)
{
	//날짜형식 yyyy.mm.dd(ch_set) 에 맞게 출력
	var obj_date = new Date();
	var year = obj_date.getFullYear();
	var month = obj_date.getMonth() + 1;
	var date = obj_date.getDate();
	
	if (ch_set != ".")
		ch_set = '-';
	
	var ret = year;
	if(month.toString().length == 1)
		ret += ch_set + "0" + month;
	else
		ret += ch_set + month;

	if(date.toString().length == 1)
		ret += ch_set + "0" + date;
	else
		ret += ch_set + date;

	return ret;
}


/**
 * moment()를 이용한 현재 날짜 반환
 * @param 
 * @returns getDate YYYY-MM-DD
 */
function getDate(){
	var curDate = moment().format('YYYY[-]MM[-]DD');
	return curDate;
}

/**
 * YYYY-MM-DD->YYYYMMDD 형식으로 변환
 * @param strDate(YYYY-MM-DD)
 * @returns YYYMMDD
 */
function getFormatDate(strDate){
	var strDate = moment(strDate);
	return strDate.format("YYYYMMDD");
}

/**
 * 시작날짜와 종료날짜 데이터 검증
 * @param gubun : 시작날짜 or 종료날짜, strDate : 캘린더에 노출된 날짜
 * @returns 
 */
function getDateVerification(gubun, strDate, critDate, strRealDate){
	var strDateFormat = getFormatDate(strDate);
	var criterionDate = getFormatDate(critDate);
	var curDate = moment().format('YYYYMMDD');
//	console.log(gubun + " :: " + strDateFormat + " :: " + criterionDate + " :: " + curDate + " :: " + strRealDate);
	switch(gubun){
		case "sdate" :
			if(strDateFormat > criterionDate){
				alert("종료일자보다 큰값이 올 수 없습니다.");
//				$("#sdate").val(strRealDate);
				return false;
			}
		break;
		case "edate" :
			if(strDateFormat < criterionDate){
				alert("시작일자보다 작은값이 올 수 없습니다.");
//				$("#edate").val(strRealDate);
				return false;
			}
			break;
	}
}

/*
 * 주어진 날짜(yyyyMMdd, yyyyMM) 그 달의 1일
 */
function first_date(date_str){
	var strDate = date_str.replace("-", "");
	
    var yyyyMMdd = strDate.toString();
    var year = yyyyMMdd.substring(0,4);
    var month = yyyyMMdd.substring(4,6);

    return year + "-" + month + "-" + "01";
}


/* ----------------------------------------------------------------------------
 * 특정 날짜에 대해 지정한 값만큼 가감(+-)한 날짜를 반환

 * 

 * 입력 파라미터 -----
 * pInterval : "yyyy" 는 연도 가감, "m" 은 월 가감, "d" 는 일 가감
 * pAddVal  : 가감 하고자 하는 값 (정수형)
 * pYyyymmdd : 가감의 기준이 되는 날짜
 * pDelimiter : pYyyymmdd 값에 사용된 구분자를 설정 (없으면 "" 입력)

 * 

 * 반환값 ----

 * yyyymmdd 또는 함수 입력시 지정된 구분자를 가지는 yyyy?mm?dd 값
 *

 * 사용예 ---

 * 2008-01-01 에 3 일 더하기 ==> add_date("d", 3, "2008-08-01", "-");

 * 20080301 에 8 개월 더하기 ==> addDate("m", 8, "20080301", "");
 --------------------------------add_date------------------------------------------ */
function add_date(pInterval, pAddVal, pYyyymmdd, pDelimiter)
{
 var yyyy;
 var mm;
 var dd;
 var cDate;
 var oDate;
 var cYear, cMonth, cDay;
 
 if (pDelimiter != "") {
  pYyyymmdd = pYyyymmdd.replace(eval("/\\" + pDelimiter + "/g"), "");
 }
 

 yyyy = pYyyymmdd.substr(0, 4);
 mm  = pYyyymmdd.substr(4, 2);
 dd  = pYyyymmdd.substr(6, 2);
 
 if (pInterval == "yyyy") {
  yyyy = (yyyy * 1) + (pAddVal * 1); 
 } else if (pInterval == "m") {
  mm  = (mm * 1) + (pAddVal * 1);
 } else if (pInterval == "d") {
  dd  = (dd * 1) + (pAddVal * 1);
 }
 

 cDate = new Date(yyyy, mm - 1, dd); // 12월, 31일을 초과하는 입력값에 대해 자동으로 계산된 날짜가 만들어짐.
 cYear = cDate.getFullYear();
 cMonth = cDate.getMonth() + 1;
 cDay = cDate.getDate();
 
 cMonth = cMonth < 10 ? "0" + cMonth : cMonth;
 cDay = cDay < 10 ? "0" + cDay : cDay;

 

 if (pDelimiter != "") {
  return cYear + pDelimiter + cMonth + pDelimiter + cDay;
 } else {
  return cYear + cMonth + cDay;
 }
 
}


function searchDate(s){
	const d = new Date();
	const year = d.getFullYear(); // 년
	const month = d.getMonth();   // 월
	const day = d.getDate();      // 일
	
	let strDate = "";
	switch(s) {
	  // 어제 날짜 구하기
	  case '1':  strDate = new Date(year, month, day - 1).toLocaleDateString();;
		  	     break;
	  // 일주일 전 구하기
	  case '2':  strDate = new Date(year, month, day - 7).toLocaleDateString();
  	     		 break;
  	  // 한달 전 구하기
	  case '3':  strDate = new Date(year, month - 1, day).toLocaleDateString();
  	     		 break;
	  default:
  	     		break;
	}
	
	let arrDate = strDate.split(".", -1);
	let arrYear = $.trim(arrDate[0]);
	let arrMonth = $.trim(arrDate[1]);
	let arrDay = $.trim(arrDate[2])
	
	arrMonth = arrMonth < 10 ? "0" + arrMonth : arrMonth;
	arrDay = arrDay < 10 ? "0" + arrDay : arrDay;
	
	let resultDate =  arrYear + "-" +  arrMonth + "-" + arrDay;
	return resultDate;
}



















