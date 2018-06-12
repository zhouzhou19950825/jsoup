var objAll = "";
var groups = "";
var content = "";
var tempId="";
$(function() {
	getAllSearch();

	// $("#searchOption").onChange(function() {
	// $("#searchOption option:selected").attr("webName")
	// })
})

function getAllSearch() {
	$.ajax({
		type : "GET", // 提交方式
		url : "/getAllSearch",// 路径
		dataType : "json",
		success : function(result) {// 返回数据根据结果进行相应的处理
			// 分装下拉框和单选框
			objAll = result;
			group(objAll);
			// getByGroupName(objAll);
			addSearchOption(groups);
		}
	})
}
function getByIdAndValues(id, values) {
	tempId=id;
	$.ajax({
		type : "GET", // 提交方式
		url : "/searchAll",// 路径
		data : {
			"id" : id,
			"values" : values
		},// 数据，这里使用的是Json格式进行传输
		dataType : "json",
		success : function(result) {// 返回数据根据结果进行相应的处理
			// 遍历列表
			if(result==null||result==""){
//				alert("抓取失败");
				return ;
			}
			var htmls="";
			for(var i=0;i<result.length;i++){
				htmls+='<li><p class="cl-newsTime">'+result[i].pubTime+' 来源： '+result[i].source+'</p>';
				htmls+='<p class="cl-newsTitle">'+result[i].title+'</p> <a href="'+result[i].newsUrl+'" target="_blank">原文链接</a><div>';
				htmls+='<button class="layui-btn layui-btn-xs layui-btn-normal" onClick=getByUrl("'+result[i].newsUrl+'","'+tempId+'")>抓取</button></div></li>';
			}
			$("#content").html(htmls);
		}
	})
}

function getByUrl(url,id) {
	$.ajax({
		type : "POST", // 提交方式
		url : "/getNews",// 路径
		data : {
			"url" : url,
			"id":id
		},// 数据，这里使用的是Json格式进行传输
		dataType : "json",
		success : function(result) {// 返回数据根据结果进行相应的处理
			// 获取内容
			$('#title').val(result.title);
			editor.txt.html(result.text);
		}
	})
}

// 分组
function group(search) {
	if (search == null) {
		return;
	}
	groups = new Object();
	for (var i = 0; i < search.length; i++) {
		eval("groups." + search[i].webName + "=1");
	}
}

// 根据组名获取类型
function getByGroupName(webName) {
	content = new Array();
	// for (i in groups) {
	for (var i = 0; i < objAll.length; i++) {
		if (webName === objAll[i].webName) {
			content.push(objAll[i]);
		}
	}
	return content;
	// }
}

function addSearchOption(result) {
	var searchOptionHtml = '';

	// for (var i = 0; i < result.length; i++) {
	// searchOptionHtml += '<option webName="' + result[i].webName + '"
	// value="">' + result[i].webName + '</option>'; // What's
	// webName?这个不懂webName到底是什么？
	// }
	var m=0;
	searchOptionHtml+='<select name="interest" lay-filter="aihao" id="searchOption">';
	for (i in groups) {
		if(m==0){
			readyRadio(i);
		}
		searchOptionHtml += '<option webName="' + i + '" value="">' + i
				+ '</option>'; // What's webName?这个不懂webName到底是什么？
		m++;
	}
	searchOptionHtml+='</select>';
	$("#searchOptionHtm").html(searchOptionHtml);
	$("#searchOption").change(function() {
		var webName = $("#searchOption option:selected").attr("webName")
		readyRadio(webName);
	})
}
/**
 * select改变后准备radio
 * @param webName
 * @returns
 */
function readyRadio(webName) {
	var searchOptionHtml = '';
	var typeObjectList = getByGroupName(webName);
	for (var i = 0; i < typeObjectList.length; i++) {
		if(i==0){
			searchOptionHtml+=typeObjectList[i].type+'<input type="radio" name="information" value="'+typeObjectList[i].type+'"title="'+typeObjectList[i].type+'"  id="'+typeObjectList[i].id+'" checked="true">';
			getByIdAndValues(typeObjectList[i].id);
			continue;
		}
		searchOptionHtml+=typeObjectList[i].type+'<input type="radio" name="information" value="'+typeObjectList[i].type+'" id="'+typeObjectList[i].id+'" >';
	}
	
	$("#typeradio").html(searchOptionHtml);
	changeRadio();
	
}
/**
 * radio change 事件
 * @returns
 */
function changeRadio(){
	$('input[type=radio][name=information]').change(function() {
       var getId=$(this).attr("id");
       getByIdAndValues(getId);
    });
}

