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
   	  <div>Vedio</div>
    </div>

  
    <video
	    id="my-player"
	    class="video-js vjs-big-play-centered"
	    controls
	    preload="auto"
	    poster="//vjs.zencdn.net/v/oceans.png"
	    data-setup='{"controls": true, "fluid": true, "autoplay": false, "muted": true, "playbackRates": [0.5, 1, 1.5, 2]}'>
	  <!-- <source src="//vjs.zencdn.net/v/oceans.webm" type="video/webm"></source> -->
	  <source src="/upload/4k/sample-10s.mp4" type="video/mp4"></source>
	
	</video>
    


    
   </div>
</div>


<script type="text/javascript">
$(document).ready(function(){
		
	
	//init();
});






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


function init(){
	alert();
	//const res = await fetch("/upload/4k/sample_960x400_ocean_with_audio.wmv");
	//const buf = await res.arrayBuffer();
	//console.log(res);
	//console.log(buf);
	
	const urlToObject= async()=> {
		
		const response = await fetch("/upload/4k/sample_960x400_ocean_with_audio.wmv");
	  	// here image is url/location of image
	  	const blob = await response.blob();
	  	const file = new File([blob], '', {type: blob.type});
	  	console.log(file);
	}
}
</script>







