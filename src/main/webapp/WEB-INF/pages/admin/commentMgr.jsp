<%--
  Created by IntelliJ IDEA.
  User: acc
  Date: 2017/3/10
  Time: 上午12:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>评论管理系统</title>
    <%@ include file="/WEB-INF/pages/common/baseui.jsp" %>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <p class="navbar-text">评论管理系统</p>
                <form class="navbar-form navbar-right" role="search">
                    <div class="form-group " >
                        <select id="searchStatus" name="status" class="form-control">
                            <option value="">--无--</option>
                            <c:forEach var="status" items="${statusList}" varStatus="varStatus">
                                <option value="${status.value}">${status.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group " >
                        <input type="text" name="comment" class="form-control" id="searchTitleTxt" placeholder="搜索..."/>
                        <button id="searchTitleBtn" type="button" class="btn btn-default" style="border-color: #ffffff;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</nav>
<div class="container-fluid" >
    <div class="row">
        <div class="col-md-12">
            <p class="navbar-text"></p>
            <form class="navbar-form navbar-right" role="search">
                <div class="form-group " >
                    <input type="hidden" name="comment" class="form-control"  placeholder="搜索..."/>
                    <button type="button" class="btn btn-default" style="display:none;border-color: #ffffff;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12" >
            <h3>  </h3>
            <table class="table table-hover" id="ajaxListPage" >

            </table>
        </div>
    </div>
</div>
<script type="text/javascript" >

    $(function(){

        searchListPgae();

    });

    $("#searchTitleBtn").click(function () {
        searchListPgae();
    });

    function searchListPgae() {
        var html = "";
        html += "<tr><th class='col-md-2'>标题</th><th class='col-md-5'>内容</th><th class='col-md-1'>类型</th>";
        html += "<th class='col-md-1'>状态</th><th class='col-md-1'>留言人 & 创建时间</th>";
        html += "<th class='col-md-1'>操作</th></tr>";

        $.ajax({ url: "${ctx}/admin/getCommentList",
            type:"get",
            async:true,
            dataType:"json",
            data:{
                "title": $('#searchTitleTxt').val(),
                "status": $('#searchStatus').val()
            },
            success: function(data){
                $.each(data, function (i, item) {
                    html += "<tr>";
                    html += "<td><a href='${ctx}/comment/contentList/"+ item.id + "' target='_blank' >" + item.title + "</a></td>";
                    html += "<td>" + item.comment +"";
                    if (item.imageUrl != '' && item.imageUrl != undefined){
                        html += "<img src='${ctx}"+ item.imageUrl+"' alt='留言图片' class='img-responsive' />";
                    }
                    html += "</td>";
                    html += "<td>" + item.type +"</td>"
                    html += "<td>" + statusFormat(item.status) +"</td>"
                    html += "<td><h5><small>" + item.createUser + "</small></h5>";
                    html += "<h6><small>" + (item.createTime) + "</small></h6></td>";
                    html += "<td><a href='${ctx}/admin/check/"+ item.id + "/version/"+ item.versions +"' >"  +"审核通过&nbsp;"+ "</a>";
                    html += "<a href='${ctx}/admin/delete/"+ item.id + "/version/"+ item.versions +"' >删除"  +"</a></td>"
                    html += "</tr>";
                });

                $("#ajaxListPage").html(html);
            }
        });
    }

    function statusFormat(val){
        var list = ${statusListJson};
        for (var i = 0; i < list.length; i++) {
            if (list[i].value == val) {
                return list[i].name;
            }
        }
    }
</script>
</body>
</html>
