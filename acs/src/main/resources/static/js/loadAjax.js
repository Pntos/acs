$(document).ajaxStart(function() {
		loadMask(true);
	}).ajaxComplete(function() {
		loadMask(false);
	}).ajaxError(function() {
		console.log('ajax 전송 실패');
		loadMask(false);
	});
	
	function loadMask(isShow, msg, _backImgPath, _backOptionStyle, _msgOptionStyle){
		if(isShow){
			var msgOnOff = true;
			if(msg == "" || msg == null)
				msgOnOff = false;
			
			if($("#ajaxScreen").length == 0) {
				
				/* 배경 이미지 및 STYLE 정의 (인자가 넘오오지 않는경우 default 적용)*/
				var backImgPath = "/image/rolling.svg";
				if(_backImgPath != null) {
					backImgPath = _backImgPath
				}

				// 고정 스타일(변경불가)
				var backFixedStyle = {
					"position": "fixed",
					"width": "100%",
					"height": "100%"				
				}
				
				var msgFixedStyle = {
					"position": "absolute"
				}			
				
				// 옵션스타일(변경가능)
				var backOptionStyle = {				
					"opacity": "0.5",
					"z-index": "100000"
				}
				
				if(_backOptionStyle != null) {
					backOptionStyle = _backOptionStyle;
				}			
				
				var msgOptionStyle = {
					"color": "white",
					"width": "250px",
					"text-align": "center",
					"z-index": "100001"
				}
				
				if(_msgOptionStyle != null) {
					msgOptionStyle = _msgOptionStyle;
				}
				/* 배경 이미지 및 STYLE 정의 끝*/

				/* 화면에 붙이기 */ 
				$('body').prepend("<div id='ajaxScreen'></div>");
				$("#ajaxScreen").append("<div id='ajaxBack'></div>");
				$("#ajaxBack").css(backFixedStyle).css(backOptionStyle);
				
				if(msgOnOff) {
					$("#ajaxScreen").append("<span id='ajaxMsg' style=''>"+msg+"</span>");
					$("#ajaxMsg").css(msgFixedStyle).css(msgOptionStyle);
				}
				/* 화면에 붙이기 끝 */
				
				/* 메세지 중앙 정렬 하기 */
				msgWidth = $("#ajaxMsg").width();
				msgHeight = $("#ajaxMsg").height();
				msgLeft = msgWidth / 2;
				msgTop = (msgHeight / 2) - 40;
				
				$("#ajaxMsg").css({
					"left" : "calc(50% - " + msgLeft + "px)",
					"top" : "calc(50% - " + msgTop + "px)"
				});
				/* 메세지 중앙 정렬 하기 끝 */
				
				/* 로딩 이미지 불러오기 */
				$('<img/>').attr('src', backImgPath).on('load', function() {
					$(this).remove();
					$("#ajaxBack").css({"background": "url('" + backImgPath + "') no-repeat black 50% 50%"});
				}).on('error', function() {
					$(this).remove();
					$("#ajaxBack").css({"background-color": "white"});
					$("#ajaxBack").append('<?xml version="1.0" encoding="utf-8"?><svg width="30px" height="30px" style="position:absolute; top:calc(50% - 15px); left:calc(50% - 15px);" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100" preserveAspectRatio="xMidYMid" class="uil-ring"><rect x="0" y="0" width="100" height="100" fill="none" class="bk"></rect><circle cx="50" cy="50" r="40" stroke-dasharray="163.36281798666926 87.9645943005142" stroke="#cec9c9" fill="none" stroke-width="20"><animateTransform attributeName="transform" type="rotate" values="0 50 50;180 50 50;360 50 50;" keyTimes="0;0.5;1" dur="1s" repeatCount="indefinite" begin="0s"></animateTransform></circle></svg>');
//					console.log('Image Load Error : ' + backImgPath + ' 위치에서 이미지를 불러올 수 없습니다. svg로 대체합니다.');
				});
				/* 로딩 이미지 불러오기 끝 */
			}
		}else{		
			$("#ajaxScreen").remove();
		}
	}
	
	jQuery.fn.serializeObject = function() {
	    var obj = null;
	    try {
	        if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
	            var arr = this.serializeArray();
	            if (arr) {
	                obj = {};
	                jQuery.each(arr, function() {
	                    obj[this.name] = this.value;
	                });
	            }//if ( arr ) {
	        }
	    } catch (e) {
	        alert(e.message);
	    } finally {
	    }
	 
	    return obj;
	};

	//화면 메세지를 띄움
	function msgBlockUI(msg) {
		if( msg!=null && msg!=undefined && msg!="" ) {
			$("#blockUI").html("<p><img src='/image/common/ajax-loader.gif'/> "+msg+"</p>");
		} else {
			$("#blockUI").html("<p><img src='/image/common/ajax-loader.gif'/> 잠시만 기다려 주세요.</p>");
		}
	
		$('#blockUI').css('position', 'absolute');
		$('#blockUI').css('left', $("#container").width()/2 + 170);
		$('#blockUI').css('top',  $("#container").height()/2 - 120);
	
		$('#blockUI').css('background-color', 'rgba(187, 187, 187, 0.3)');
		$('#blockUI').css('border-radius', '7px');
		$('#blockUI').css('height', '50px');
		$('#blockUI').css('width', '200px');
		$('#blockUI').css('text-align', 'center');
		$('#blockUI').css('line-height', '50px');
	
		$("#blockUI").fadeIn(100);
	}
	
	// 화면 메세지를 숨김
	function msgUnblockUI() {
	    $("#blockUI").fadeOut(100);
	}
