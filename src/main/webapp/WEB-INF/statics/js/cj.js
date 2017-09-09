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
var members = new Array();

function selectGroup(group){
    $.ajax({
        url: contextPath + "/getMemberData?group="+group,
        dataType: "json",
        type: "get",
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if(data != null && data != undefined){
                var json = eval(data);
                var html = '<option value="支局">支局</option>';
                for(var i=0; i<json.length; i++) {
                    html += '<option value="'+json[i]+'">'+json[i]+'</option>'
                    members[i] = json[i];
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
            refresh();
        }
    });
}

//var xinm = new Array();
//xinm[0] = "白金香"
//xinm[1] = "白应梅"
//xinm[2] = "柏仁燕"
//xinm[3] = "包颜琳"
//xinm[4] = "鲍学梅"
//xinm[5] = "鲍  颖"
//xinm[6] = "蔡玲芳"
//xinm[7] = "蔡  艳"
//xinm[8] = "蔡  玉"
//xinm[9] = "曹发敏"
//
//var phone = new Array();
//phone[0] = "千萧玉"
//phone[1] = "励曜儿"
//phone[2] = "素自强"
//phone[3] = "区湘云"
//phone[4] = "年坚秉"
//phone[5] = "京静槐"
//phone[6] = "那流婉"
//phone[7] = "司空俊艾"
//phone[8] = "业盼香"
//phone[9] = "脱新筠"

var nametxt = $('.name');
var phonetxt = $('.phone');
var pcount = members.length;//参加人数
var runing = true;
var num1 = 0;
var num2 = 0;
var t;
//开始停止
function start() {
    console.log(members)
    if (runing) {
        runing = false;
        $('#btntxt').removeClass('start').addClass('stop');
        $('#btntxt').html('停止');
        startNum()
    } else {
        runing = true;
        $('#btntxt').removeClass('stop').addClass('start');
        $('#btntxt').html('开始');
        stop();
        zd();//内定中奖
    }
}
//循环参加名单
function startNum() {
    num1 = Math.floor(Math.random() * pcount);
    num2 = Math.floor(Math.random() * pcount);
    if(num1 == num2){
        if(num2 != 0){
            num2 = num2 - 1;
        }else{
            num2 = num2 + 1;
        }
    }
    nametxt.html(members[num1]);
    phonetxt.html(members[num2]);
    t = setTimeout(startNum, 0);
}
//停止跳动
function stop() {
    pcount = members.length-2;
    clearInterval(t);
    t = 0;
}
//从一等奖开始指定前3名
function zd() {
    //if(td <= 3){
    //    if (td == 1) {
    //        nametxt.html('周一一');
    //        phonetxt.html('15112345678');
    //        $('.list').prepend("<p>"+td+' '+"周一一 -- 15112345678</p>");
    //    }
    //    if (td == 2) {
    //        nametxt.html('李二二');
    //        phonetxt.html('151000000000');
    //        $('.list').prepend("<p>"+td+' '+"李二二 -- 151000000000</p>");
    //    }
    //    if (td == 3) {
    //        nametxt.html('张三三');
    //        phonetxt.html('1511111111');
    //        $('.list').prepend("<p>"+td+' '+"张三三 -- 1511111111</p>");
    //    }
    //}else if(td > 3){
        //打印中奖者名单
        $('.list').prepend("<p>"+members[num1]+" -- "+members[num2]+"</p>");
        if(pcount <= 0){
            alert("投票结束");
        }
        //将已中奖者从数组中"删除",防止二次中奖
        members.splice($.inArray(members[num1], members), 1);
        members.splice($.inArray(members[num2], members), 1);
        console.log(members)
        console.log("-------------------------------")
    //}
}

