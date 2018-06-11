var objAll = "";
var groups = "";
var content = "";
$(function () {
    getAllSearch();

    $("#searchOption").onChange(function () {
        $("#searchOption option:selected").attr("webName")
    })
})

function getAllSearch() {
    $.ajax({
        type: "GET", // 提交方式
        url: "/getAllSearch",// 路径
        dataType: "json",
        success: function (result) {// 返回数据根据结果进行相应的处理
            // 分装下拉框和单选框
            objAll = result;
        }
    })
}
function getByIdAndValues(id, values) {
    $.ajax({
        type: "GET", // 提交方式
        url: "/searchAll",// 路径
        data: {
            "id": id,
            "values": values
        },// 数据，这里使用的是Json格式进行传输
        dataType: "json",
        success: function (result) {// 返回数据根据结果进行相应的处理
            // 遍历列表
        }
    })
}

function getByUrl(url) {
    $.ajax({
        type: "POST", // 提交方式
        url: "/getNews",// 路径
        data: {
            "url": url
        },// 数据，这里使用的是Json格式进行传输
        dataType: "json",
        success: function (result) {// 返回数据根据结果进行相应的处理
            // 获取内容
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
function getByGroupName(search) {
    content = new Array();
    for (i in groups) {
        for (var i = 0; i < search.length; i++) {
            if (i === search[i].webName) {
                content.push(search[i]);
            }
        }
    }
}

function addSearchOption(result) {
    var searchOptionHtml = '';

    for (var i = 0; i < result.length; i++) {
        searchOptionHtml += '<option webName="' + result[i].webName + '" value="">' + result[i].webName + '</option>'; // What's webName?这个不懂webName到底是什么？
    }

    $("#searchOption").html(searchOptionHtml);
}