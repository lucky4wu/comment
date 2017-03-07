<%--
  Created by IntelliJ IDEA.
  User: acc
  Date: 2017/3/3
  Time: 下午11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>匿名版</title>
    <jsp:include page="${ctx}/WEB-INF/pages/common/baseui.jsp" />
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <p class="navbar-text">Acc's 匿名版，欢迎灌水！</p>
                <form class="navbar-form navbar-right" role="search">
                    <div class="form-group " >
                        <input type="text" name="comment" class="form-control" id="searchTitle" placeholder="搜索..."/>
                        <button type="button" class="btn btn-default" style="border-color: #ffffff;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</nav>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <p class="navbar-text"></p>
            <form class="navbar-form navbar-right" role="search">
                <div class="form-group " >
                    <input type="text" name="comment" class="form-control"  placeholder="搜索..."/>
                    <button type="button" class="btn btn-default" style="border-color: #ffffff;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
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
    <div class="row">
        <div class="col-md-12">
            <h3>路过留下 <span class="label label-info">爪印</span></h3>
        </div>
        <div class="col-md-10">
            <form class="form-horizontal"  action="${ctx}/comment/addComment" id="addForm" method="post">
                <div class="form-group">
                    <label class="col-sm-2 control-label">标题</label>
                    <div class="col-sm-10">
                        <input type="text" name="title" class="form-control" id="txtTitle" placeholder="请输入标题"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">内容</label>
                    <div class="col-sm-10">
                        <textarea type="text" name="comment" class="form-control" id="txtComment" placeholder="请输入内容" style="height: 180px;"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary" style="width: 100px;">提交</button>
                    </div>
                </div>

            </form>
        </div>
        <div class="col-md-2">
            <table class="table">
                <tr>
                    <th>昵称</th>
                    <td>${user.nickName}</td>
                </tr>
                <tr>
                    <th>ID</th>
                    <td>${userNo}</td>
                </tr>
                <tr>
                    <img src="${ctx}/static/image/${user.imageUrl}" class="img-responsive img-circle" alt="头像" style="height: 140px;width: 140px;">
                </tr>
            </table>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="progress">
                <div class="progress-bar progress-bar-warning progress-bar-striped active" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
                    正在施工
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <img src="${ctx}/static/image/zhifubao.jpg" class="img-responsive" alt="支付二维码" title="一分钱也是爱" style="height: 480px;width: 320px;">
        </div>
        <div class="col-md-12">
            <img src="${ctx}/static/image/zhifubao-2.jpg" class="img-responsive" alt="支付二维码" title="捐助开发者团队" style="height: 480px;width: 320px;">
        </div>
    </div>
</div>


<script type="text/javascript" >
   $(function(){
        var html = "";
        html += "<tr><th class='col-md-10'></th><th class='col-md-2'></th></tr>";
        $.ajax({ url: "${ctx}/comment/listPage",
            type:"get",
            async:true,
            dataType:"json",
            success: function(data){
                $.each(data, function (i, item) {
                    html += "<tr>";
                    html += "<td><a href='${ctx}/comment/contentList/"+ item.id + "' target='_blank' >" + item.title + "</a></td>";
                    html += "<td><h5><small>" + item.createUser + "</small></h5>";
                    html += "<h6><small>" + date2str(item.createTime) + "</small></h6></td>";
                    html += "</tr>";
                });

                $("#ajaxListPage").html(html);
            }
        });
    });

   function date2str(date) {
       return date.substr(0,20)
   }

   var checkflag = {"title":false, "comment":false};
   $("#txtTitle").blur(function () {
       if ($("#txtTitle").val() == ""){
            checkflag.title = false;
       }else {
           checkflag.title = true;
       }
   });

   $("#txtComment").blur(function () {
       if ($("#txtComment").val() == ""){
           checkflag.comment = false;
       }else {
           checkflag.comment = true;
       }
   });
    $("#addForm").submit(function () {
        $("#txtTitle").blur();
        $("#txtComment").blur();
        return  checkflag.title && checkflag.comment;
    });
</script>
<jsp:include page="${ctx}/WEB-INF/pages/common/footer.jsp" />
</body>
</html>
