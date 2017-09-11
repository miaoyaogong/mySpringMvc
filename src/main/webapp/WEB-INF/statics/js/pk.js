/**
 * Created by 1 on 2017/9/7.
 */

$(function(){
    refresh();
    $("#upfiledo").on('click', function () {
        var formData = new FormData($("#uploadForm")[0]);
        toUpload(formData);
    });
    $('#group').change(function(){
        var select = $(this).children('option:selected').val();
        selectGroup(select)
        $("button").show();
        $("#res1").text('****');
        $("#res2").text('****');
    })
})

function refresh(){
    $.ajax({
        url: contextPath + "/getGroupData",
        dataType: "json",
        type: "post",
        data: "",
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if(data != null && data != undefined){
                var json = eval(data);
                var html = '<option value="组名">组名</option>';
                for(var i=0; i<json.length; i++) {
                    html += '<option value="'+json[i]+'">'+json[i]+'</option>'
                }
                $("#group").html(html);
            }
        }
    });
}
var data = new Array();

function selectGroup(group){
    $.ajax({
        url: contextPath + "/getMemberData?group="+group,
        dataType: "json",
        type: "get",
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (result) {
            if(result != null && result != undefined){
                var json = eval(result);
                var html = '<option value="支局">支局</option>';
                for(var i=0; i<json.length; i++) {
                    html += '<option value="'+json[i]+'">'+json[i]+'</option>'
                    data[i] = json[i];
                }
                $("#member").html(html);
            }
        }
    });
}

function toUpload(formData) {
    var formData = new FormData($("#uploadForm")[0]);
    $.ajax({
        url: contextPath + "/upload",
        dataType: "json",
        type: "post",
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            location.reload()
            alert("数据上传成功");
        }
    });
}


var btn=true; //按钮状态未开始还是结束
var key1=0; //中奖下标
var key2=0; //中奖下标
var time=0; //定时器

function runTime(){
    //$("button").show();
    clearInterval(time);
    time=setInterval('trunNum()',10);
}

//点击按钮
function start(){
    console.log(data)
    if(btn){
        if(data.length < 2){
            $("button").hide();
            alert('所有PK已结束！！！');
        }else{
            btn=false;
            $("button").removeClass("start").addClass("end").text("结束PK");
            startTrun();
        }

    }else{
        btn=true;
        $("button").removeClass("end").addClass("start").text("开始PK");
        endTrun();
    }
}

function trunNum(){
    if(data.length > 2){
        key1=Math.floor(Math.random()*(data.length-1));
        key2=Math.floor(Math.random()*(data.length-1));
        if(key1 == key2){
            if(key2 == 0){
                key2++;
            }else{
                key2--;
            }
        }
    }
    if(data.length == 2){
        key1=0;
        key2=1;
    }
    var res1 = data[key1].toString()
    var res2 = data[key2].toString();
    $("#res1").text(res1);
    $("#res2").text(res2);
}

//开始转动数字
function startTrun(){
    runTime();
}

//停止转动数字
function endTrun(){
    clearInterval(time);
    console.log('总参与人员数量：'+data.length);
    data.splice(key1,1);
    data.splice(key2,1);


}
