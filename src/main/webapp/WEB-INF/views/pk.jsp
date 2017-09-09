<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<!-- saved from url=(0114)http://www.17sucai.com/preview/331792/2016-05-24/%E5%A7%93%E5%90%8D%E7%94%B5%E8%AF%9D%E6%8A%BD%E5%A5%96/index.html -->
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>支局随机PK</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css">
    <style id="style-1-cropbar-clipper">/* Copyright 2014 Evernote Corporation. All rights reserved. */
    .en-markup-crop-options {
        top: 18px !important;
        left: 50% !important;
        margin-left: -100px !important;
        width: 200px !important;
        border: 2px rgba(255,255,255,.38) solid !important;
        border-radius: 4px !important;
    }

    .en-markup-crop-options div div:first-of-type {
        margin-left: 0px !important;
    }
    </style></head>
<body>
<div class="bg">
    <%--<img src="${contextPath}/image/bg.jpg">--%>
</div>
<div class="box">
    <div class="jz">
        <p><b>支局随机PK活动</b></p>
        <div style="display:flex;">
            <select class="select">
                <option value="组名">组名</option>
            </select>
            <span class="name">A方</span>
        </div>
        <div style="display:flex;">
            <select class="select">
                <option 　value="支局">支局</option>
            </select>
            <span class="phone">B方</span><br>
        </div>

        <div >
            <div class="start" id="btntxt" onclick="start()">开始</div>

            <form id="uploadForm">
                <div class="refresh" ><input class="" type="file" name="file" id="upfile" onchange="toUpload()"/></div>
            </form>
        </div>
    </div>
    <div class="zjmd">
        <p class="p1">对战者名单</p>
        <div class="list">

        </div>
    </div>
</div>
<script type="text/javascript" src="${contextPath}/js/jquery-1.8.0.js" ></script>
<script type="text/javascript" src="${contextPath}/js/cj.js"></script>
<script type="text/javascript">
    var contextPath = '${contextPath}';
</script>

</body></html>