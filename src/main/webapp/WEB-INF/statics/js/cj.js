/**
 * Created by 1 on 2017/9/7.
 */

$(function(){
    $("#upfiledo").on('click', function () {
        var formData = new FormData($("#uploadForm")[0]);
        toUpload(formData);
    });
})

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
            alert(data);
        }
    });
}

var xinm = new Array();
xinm[0] = "白金香"
xinm[1] = "白应梅"
xinm[2] = "柏仁燕"
xinm[3] = "包颜琳"
xinm[4] = "鲍学梅"
xinm[5] = "鲍  颖"
xinm[6] = "蔡玲芳"
xinm[7] = "蔡  艳"
xinm[8] = "蔡  玉"
xinm[9] = "曹发敏"

var phone = new Array();
phone[0] = "千萧玉"
phone[1] = "励曜儿"
phone[2] = "素自强"
phone[3] = "区湘云"
phone[4] = "年坚秉"
phone[5] = "京静槐"
phone[6] = "那流婉"
phone[7] = "司空俊艾"
phone[8] = "业盼香"
phone[9] = "脱新筠"

var nametxt = $('.name');
var phonetxt = $('.phone');
var pcount = xinm.length-1;//参加人数
var runing = true;
var td = 10;//内定中奖,从最小奖开始，共10个名额
var num = 0;
var t;
//开始停止
function start() {
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
    num = Math.floor(Math.random() * pcount);
    nametxt.html(xinm[num]);
    phonetxt.html(phone[num]);
    t = setTimeout(startNum, 0);
}
//停止跳动
function stop() {
    pcount = xinm.length-1;
    clearInterval(t);
    t = 0;
}
//从一等奖开始指定前3名
function zd() {
    if(td <= 3){
        if (td == 1) {
            nametxt.html('周一一');
            phonetxt.html('15112345678');
            $('.list').prepend("<p>"+td+' '+"周一一 -- 15112345678</p>");
        }
        if (td == 2) {
            nametxt.html('李二二');
            phonetxt.html('151000000000');
            $('.list').prepend("<p>"+td+' '+"李二二 -- 151000000000</p>");
        }
        if (td == 3) {
            nametxt.html('张三三');
            phonetxt.html('1511111111');
            $('.list').prepend("<p>"+td+' '+"张三三 -- 1511111111</p>");
        }
    }else if(td > 3){
        //打印中奖者名单
        $('.list').prepend("<p>"+td+' '+xinm[num]+" -- "+phone[num]+"</p>");
        if(pcount <= 0){
            alert("投票结束");
        }
        //将已中奖者从数组中"删除",防止二次中奖
        xinm.splice($.inArray(xinm[num], xinm), 1);
        phone.splice($.inArray(phone[num], phone), 1);
    }
    td = td - 1;
}

