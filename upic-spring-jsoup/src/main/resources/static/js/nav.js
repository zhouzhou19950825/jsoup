
	function allDrawTriangle(){
		for(var i =0;i<9;i++){
			var tx = 't'+i;
	var cav = document.getElementById(tx);
	var ctx = cav.getContext('2d');
	ctx.moveTo(150,0);
	ctx.lineTo(135,100);
	ctx.lineTo(165,100);
	ctx.fillStyle = "#FFF";
	ctx.fill();
}
}

allDrawTriangle();

    var css1 = {
    	position:"relative",
    	fontFamily:"PingFang-SC-Regular",
    	fontSize:"18px",
    	width:"125px",
    	height:"25px",
    	textAlign:"center",
    	marginLeft:"5px",
    	marginTop:"20px",
    	display:"inline-block",
    	color:"#000"
    }
    
  $(document).on("click",".nav li",function(){
  	         var f =  $(this).attr('id');
  	         var tx = '#t'+f[f.length-1];
  	         for(var i=0; i<9; i++)
  	         {
                var t1 = '#t'+i;
                $(t1).css("opacity","0");
  	         }

  	         $(tx).css("opacity","1");
  	         $("#subNavBar").children().remove();
          
  	         if(f == 'item0'){
  	         	$("#subNavBar").append("<a href = 'html/mediaInformation.html' target = 'external-frame'><p>媒体资讯篇</p></a> <a href = 'html/academicTrends.html' target = 'external-frame'><p>学术动态篇</p></a>");
  	         	$("#subNavBar p").css(css1);
  	         }


  	         else if(f == 'item1'){
  	         	$("#subNavBar").append("<a href = 'html/silkRoadChina.html' target = 'external-frame'><p>丝路中国</p></a> <a href = 'html/silkRoadInternation.html' target = 'external-frame'><p>丝路国际</p></a>");
  	         	$("#subNavBar p").css(css1);
  	         }


  	          else if(f == 'item2'){
  	         	$("#subNavBar").append("<a href = 'html/investmentGuide.html' target = 'external-frame'><p>投资指南</p></a>");
  	         	$("#subNavBar p").css(css1);
  	         }  

  	          else if(f == 'item3'){
  	         	$("#subNavBar").append("<a href = 'html/nationalLibrary.html' target = 'external-frame'><p>沿线国家库</p></a> <a href = 'html/nationalLibrary.html' target = 'external-frame'><p>国家导航</p></a>");
  	         	$("#subNavBar p").css(css1);
  	         } 

  	          else if(f == 'item4'){
  	         	$("#subNavBar").append("<a href = 'html/industrialDevelopment.html' target = 'external-frame'><p>产业发展篇</p></a> <a href = '#'><p>基础设施篇</p></a>");
  	         	$("#subNavBar p").css(css1);
  	         }

  	         else if(f == 'item5'){
  	         	$("#subNavBar").append("<a href = 'html/silkRoadDB.html' target = 'external-frame'><p>海丝指数</p></a> <a href = '#'><p>发展指数</p></a> <a href = '#'><p>投资指数</p></a>");
  	         	$("#subNavBar p").css(css1);
  	         }  

  	         else if(f == 'item6'){
  	         	$("#subNavBar").append("<a href = 'html/expertLibrary.html' target = 'external-frame'><p>专家库</p></a>");
  	         	$("#subNavBar p").css(css1);
  	         }

             else if(f == 'item7'){

             } 

             else if(f == 'item8'){
                $("#subNavBar").append("<a href = 'html/memorabilia.html' target = 'external-frame'><p>大事记</p></a>");
              $("#subNavBar p").css(css1);
             }    
})

  $(document).ready(function(){
    $('#t0').css("opacity","1");
    $("#subNavBar").append("<a href = 'html/mediaInformation.html' target = 'external-frame'><p>媒体资讯篇</p></a> <a href = 'html/academicTrends.html' target = 'external-frame'><p>学术动态篇</p></a>");
              $("#subNavBar p").css(css1);
  })
