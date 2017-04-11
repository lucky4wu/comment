<%--
  Created by IntelliJ IDEA.
  User: CrazyFive
  Date: 2017/3/5
  Time: 23:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>
    <%--<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">--%>
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css"/>
    <%--<link rel="stylesheet" href="${ctx}/static/css/bootstrap-theme.min.css"/>--%>

</head>
<body>
<%--<script type="text/javascript" src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>--%>
<%--<script type="text/javascript" src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>--%>
<script type="text/javascript" src="${ctx}/static/js/jquery.1.12.4.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/iscroll-probe.js"></script>

<script type="text/javascript">
    function formatterdate(val, row) {
        if (val != '' && val != null)
        {
            var date = new Date(val);
            var month = date.getMonth() + 1;
            if(month > 0 && month < 10) {
                month = "0" + month;
            }
            var day = date.getDate();
            if(day > 0 && day < 10) {
                day = "0" + day;
            }
            var hour = date.getHours();
            if(hour >= 0 && hour < 10) {
                hour = "0" + hour;
            }
            var minutes = date.getMinutes();
            if(minutes >= 0 && minutes < 10) {
                minutes = "0" + minutes;
            }
            var seconds = date.getSeconds();
            if(seconds >= 0 && seconds < 10) {
                seconds = "0" + seconds;
            }
            return date.getFullYear() + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
        }

    }

    function init(){
        var width = $(window).width();
        if (width > 1024){
            $('.main-container').attr("style", "width:1024px;");
        } else {
            $('.main-container').attr("style", "width:100%;");
        }

        var height = $('nav').height();
        var style = $('.main-container').attr("style");
        $('.main-container').attr("style", "margin-top:"+height+"px;"+style);
    }

    function convertStrTo200(str) {
        var reStr = '';
        if (str.length > 200){
            reStr += str.substr(0,200) + "...";
        }else {
            reStr += str.substr(0,200);
        }
        return reStr;
    }
</script>
</body>
<%@ include file="/WEB-INF/pages/common/googleAnalytics.jsp" %>
</html>