<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>支局随机PK</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css">

    <style>
        *{padding:0; margin:0;}
        body{
            background:url('${contextPath}/image/pk.jpg') no-repeat;
            background-size:cover;
        }
        .container{width:400px; height:200px; position:fixed; top:50%; left:50%; margin:-100px 0 0 -200px; text-align:center; }
        .result-box{ background-color:#fff; text-align:center; line-height:60px; font-size:34px;}
        button{ width:200px; height:50px; line-height:50px; margin-top:30px; border:none; color:#fff; font-size:24px;}
        button:focus{outline:none;}
        .start{ background-color:#428bca;}
        .end{ background-color:#d9534f;}
    </style>

</head>
<body>

<div class="container">
    <div class="result-box" id="res1">
        ****
    </div>
    <div class="result-box">
        VS
    </div>
    <div class="result-box" id="res2">
        ****
    </div>
    <button class="start" onClick="start()">开始抽取</button>
</div><!--container-->
<select id="group">
    <option value="组名">组名</option>
</select>
<select id="member">
    <option value="支局">支局</option>
</select>
<form id="uploadForm" style="float: right">
        <input class="file" type="file" name="file" id="upfile" onchange="toUpload()"/>
</form>
<script type="text/javascript" src="${contextPath}/js/jquery-1.8.0.js" ></script>
<script type="text/javascript" src="${contextPath}/js/pk.js"></script>
<script type="text/javascript">
    var contextPath = '${contextPath}';
</script>

</body>
</html>

