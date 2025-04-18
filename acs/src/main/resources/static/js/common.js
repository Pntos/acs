// window resize

// $(window).on('load',function(){
//     windowSize();
// });
// $(window).resize(function(){
//     windowSize();
// });
// $(window).trigger('resize');

// $(window).bind("pageshow", function(event) {
//     if (event.originalEvent.persisted) {
//         document.location.reload();
//     }
// });

$("#wrapper").css("min-height",$("body").height());
$(window).resize(function() {
	$("#wrapper").css("min-height",$("body").height());
});

//3자리 단위마다 콤마 생성
function addCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
 
//모든 콤마 제거
function removeCommas(x) {
    if(!x || x.length == 0) return "";
    else return x.split(",").join("");
}

function windowSize(){
    let size = $(window).width();
    // 해상도가 1200인 경우 메뉴 'hidden'
    if(size > 1200){
        $('.wrap').find('.background').remove();
        $('.side_nav').addClass('on');
    }else{
        $('.wrap').find('.background').remove();
        $('.side_nav').removeClass('on');
    } 
}
    
/**
 * 게시판 페이지 이동
 * @param gubun:페이지 이동 구분, page: 이동될페이지, totalPage:총페이지
 * @returns
 */
function fnCommGoPage(callBackFn, gubun, page, totalPage){
	page = parseInt(page);
	totalPage = parseInt(totalPage);
	
	switch (gubun) {
	    case "first" :
	        break;
	    case "last" :
	    	page = totalPage;
	    	break;
	    case "prev" :
	    	if(page>1)
	    		page= page-1;
	    	break;
	    case "next" :
	    	if(page<totalPage)
	    		page= page+1;
	    	break;
	    default :
	}
	$("#pageNumber").val(page);
	
	if(typeof callBackFn === 'function'){
		callBackFn();
	}
}

/**
 * 게시판 목록 페이징 초기화
 * @param pageNumber:현재페이지, rowsPerPage:한개의페이지에 보여즐 row 갯수, totalPage:총페이지
 * @returns PAGE Dynamic HTML
 */
function getCommPaging(pageNumber, rowsPerPage, totalPage, callBackFn){
	var pageCnt = 10;
	var page = Math.ceil(pageNumber/pageCnt);
	
	var lastPg = page * pageCnt;
		if(lastPg > totalPage){
			lastPg = totalPage;
		}
		
	var firstPg = lastPg - (pageCnt-1);
		if(firstPg < 1){
			firstPg = 1;
		}else if(lastPg == totalPage){
			//firstPg = (lastPg+1) - (lastPg % 10);
		}
	
	var strClass_On = " class=\'active\' ";
	var strClass_Off = " ";
	
	var strHtml = "";
	strHtml +='<a href="javascript: fnCommGoPage('+callBackFn+',\'first\',\'1\');" class="active">First</a>';	
	strHtml +='<a href="javascript: fnCommGoPage('+callBackFn+',\'prev\',\''+pageNumber+'\',\''+totalPage+'\');" class="prev active"></a>';
	
	for(var i=firstPg; i<=lastPg; i++){
		if(i==pageNumber){
			strHtml +='<a href="javascript: fnCommGoPage('+callBackFn+',\'cur\',\''+i+'\');" '+strClass_On+'>'+i+'</a>';
		}else{
			strHtml +='<a href="javascript: fnCommGoPage('+callBackFn+',\'cur\',\''+i+'\');" '+strClass_Off+'>'+i+'</a>';
		}
	}
	strHtml +='<a href="javascript: fnCommGoPage('+callBackFn+',\'next\',\''+pageNumber+'\',\''+totalPage+'\');" class="arrow next"></a>';
    strHtml +='<a href="javascript: fnCommGoPage('+callBackFn+',\'last\',\''+pageNumber+'\',\''+totalPage+'\');" class="active">Last</a>';
	
	$(".page_nation").html(strHtml);
}

function getCommPaging2(pageNumber, rowsPerPage, totalPage, callBackFn){
	var pageCnt = 10;
	var page = Math.ceil(pageNumber/pageCnt);
	
	var lastPg = page * pageCnt;
		if(lastPg > totalPage){
			lastPg = totalPage;
		}
		
	var firstPg = lastPg - (pageCnt-1);
		if(firstPg < 1){
			firstPg = 1;
		}else if(lastPg == totalPage){
			//firstPg = (lastPg+1) - (lastPg % 10);
		}
	
	var strClass_On = " class=\'active\' ";
	var strClass_Off = " ";
	
	var strHtml = "";
	strHtml +='<a href="javascript: fnCommGoPage('+callBackFn+',\'first\',\'1\');" class="active">First</a>';	
	strHtml +='<a href="javascript: fnCommGoPage('+callBackFn+',\'prev\',\''+pageNumber+'\',\''+totalPage+'\');" class="prev active"></a>';
	
	for(var i=firstPg; i<=lastPg; i++){
		if(i==pageNumber){
			strHtml +='<a href="javascript: fnCommGoPage('+callBackFn+',\'cur\',\''+i+'\');" '+strClass_On+'>'+i+'</a>';
		}else{
			strHtml +='<a href="javascript: fnCommGoPage('+callBackFn+',\'cur\',\''+i+'\');" '+strClass_Off+'>'+i+'</a>';
		}
	}
	strHtml +='<a href="javascript: fnCommGoPage('+callBackFn+',\'next\',\''+pageNumber+'\',\''+totalPage+'\');" class="arrow next"></a>';
     strHtml +='<a href="javascript: fnCommGoPage('+callBackFn+',\'last\',\''+pageNumber+'\',\''+totalPage+'\');" class="active">Last</a>';
	
	$(".page_nation2").html(strHtml);
}
	
function getWindowWidth() {
   return window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
}

function getWindowHeight() {
  return window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
}

function commonFileDown(fileSeq){
	if(fileSeq.length==0){
		alert("파일정보가 없습니다.");
		return false;
	}
	$("#VO_NO").val(fileSeq);
	$("#paramForm").attr("action","/web/common/commonFileDown.json");
	$("#paramForm").submit();	
}

const datePicker = document.getElementById('datepicker');
const calendar = document.getElementById('popCalendar');

//datepicker 공통 옵션
$.datepicker.setDefaults({
    dayNamesMin: ['일','월', '화', '수', '목', '금', '토']
    ,monthNames: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12']
    ,showMonthAfterYear: true 
    ,dateFormat: "yy-mm-dd"
    ,yearSuffix: "."
});

const background = document.querySelector('.pop_bg');


//팝업 닫기 동작
function popupClose(close){
    $(close).parents('.pop_wrap').removeClass('active');
    background.classList.remove('active');
}

let calendarButton;

//팝업 공통 영역
let popup = {
    alert : function(message){     
        popupInit();      
        let html = '';
        html += '<div class="pop_wrap active" id="alertPopup">'
                    + '<div class="popup_layer alert">'
                       + '<div class="close" onclick="popupClose(this);"><img src="/image/ico_close1.png" alt="" /></div>'
                        + '<div class="hidden">'
                            + '<div class="pop_top">'
                                + '<h2>' + message + '</h2>'
                            + '</div>'
                            + '<div class="pop_btn_area">'
                                + '<input type="button" class="btn_type1 col1" value="확인" onclick="popupClose(this);" />'
                            + '</div>'
                        + '</div>'
                    + '</div>'
                + '</div>';
        $('body').append(html);
        return false;
    }
    ,confirm : function(message, callback){     
        popupInit();
        let html = '';
        html += '<div class="pop_wrap active" id="alertPopup">'
                    + '<div class="popup_layer alert">'
                       + '<div class="close" onclick="popupClose(this);"><img src="/image/ico_close1.png" alt="" /></div>'
                        + '<div class="hidden">'
                            + '<div class="pop_top">'
                                + '<h2>' + message + '</h2>'
                            + '</div>'
                            + '<div class="pop_btn_area">'
                                + '<input type="button" class="btn_type2 col2" value="아니오" onclick="popupClose(this);" />'
                                + '<input type="button" class="btn_type1 col2" value="예" onclick="'+ callback +'" />'
                            + '</div>'
                        + '</div>'
                    + '</div>'
                + '</div>';
        $('body').append(html);
        return false;
    }
    ,afterSave : function(list,result){          
        popupInit();
        let html = '';
        html += '<div class="pop_wrap active" id="alertPopup">'
                    + '<div class="popup_layer alert">'
                        + '<div class="close" onclick="popupClose(this);"><img src="/image/ico_close1.png" alt=""/></div>'
                            + '<div class="hidden">'
                            + '<div class="pop_top">'
                                + '<h2>저장되었습니다.</h2>' 
                            + '</div>'
                            + '<div class="pop_btn_area">'
                                + '<input type="button" class="btn_type2 col2" value="리스트로 돌아가기" onclick="location.href=&#39;' + list + '&#39;"/>'
                                + '<input type="button" class="btn_type1 col2" value="결과 확인하기" onclick="location.href=&#39;' + result + '&#39;"/>'
                            + '</div>'
                        + '</div>'
                    + '</div>'
                + '</div>';
        $('body').append(html);
        return false;
    }
    //싱글 달력 영역
    ,calendar : function(button){
        $('#popCalendar').remove();
        background.classList.add('active');
        let html = '';
        html +=    '<div class="pop_wrap active" id="popCalendar">'
                    +    '<div class="popup_layer calendar">'
                    +        '<div class="close" onclick="popupClose(this);"><img src="/image/ico_close1.png" alt=""/></div>'
                    +        '<div class="hidden">'
                    +           '<div class="pop_top">'
                    +                '<h2>캘린더</h2>'
                    +           '</div>'
                    +            '<div class="pop_contents">'
                    +                '<div id="datepicker"></div>'
                    +            '</div>'
                    +        '</div>'
                    +    '</div>'
                    +'</div>';
        $('body').append(html);
        calendarButton = button;
        /* datepicker */
        $( "#datepicker" ).datepicker({
            onSelect : function(selected){
                $(calendarButton).prev('input').val(selected);
                $('#popCalendar').removeClass('active');   
                $('.pop_bg').removeClass('active');
            }
        });
    }
    //기간 설정 달력 영역
    ,rangeCalendar : function(type){
        let calendarLength = $('#popCalendar').length;
        background.classList.add('active');

        if(calendarLength == 0){
            let html = '';
            html +=    '<div class="pop_wrap active" id="popCalendar">'
                        +    '<div class="popup_layer calendar">'
                        +        '<div class="close" onclick="popupClose(this);"><img src="/image/ico_close1.png" alt=""/></div>'
                        +        '<div class="hidden">'
                        +           '<div class="pop_top">'
                        +                '<h2>캘린더</h2>'
                        +           '</div>'
                        +            '<div class="pop_contents">'
                        +                '<div id="datepicker"></div>'
                        +            '</div>'
                        +        '</div>'
                        +    '</div>'
                        +'</div>';
            $('body').append(html);
        }
       $('#popCalendar').addClass('active');

        const dateCalendar = document.getElementById('datepicker');
        dateCalendar.setAttribute('data', type);
        if(type == 'from'){
            // 달력의 Data 값이 from 인 경우 시작일 초기화 동작
            $( "#datepicker").datepicker("option", "minDate", ''); 
        }
        if(type == 'to'){
            // 달력의 Data 값이 to 인 경우 종료일 초기화 동작
            $( "#datepicker").datepicker("option", "maxDate", ''); 
        }
        const dateFrom = document.getElementById('js-from');
        const dateTo = document.getElementById('js-to');

        /* datepicker */
        $( "#datepicker" ).datepicker({
            onSelect: function(selected){
                let getType =  dateCalendar.getAttribute('data');

                    // 시작일 선택 동작 minDate값  
                    if(getType == 'from'){
                        dateFrom.value = selected;
                        $( "#datepicker").datepicker("option", "minDate", selected);
                    }else{
                        // 종료일 선택 동작 minDate값  
                        dateTo.value = selected;
                        $( "#datepicker").datepicker("option", "maxDate", selected); 
                    }    
                    document.getElementById('popCalendar').classList.remove('active');   
                    background.classList.remove('active');   
                }
        });

    }
}
