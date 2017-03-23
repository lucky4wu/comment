<%--
  Created by IntelliJ IDEA.
  User: acc
  Date: 2017/3/7
  Time: 下午8:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>${title}</title>
    <%@ include file="/WEB-INF/pages/common/baseui.jsp" %>
</head>
<body onresize="init()">
<%@ include file="/WEB-INF/pages/common/top.jsp" %>
<div class="container-fluid main-container">
    <div class="row">
        <div class="col-md-12">
            <input type="hidden" id="commentId" value="${id}"/>
            <table class="table" id="ajaxContentList">

            </table>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <form class="form-horizontal"  action="${ctx}/comment/replyContent/${id}" id="addForm" method="post" enctype="multipart/form-data">

                <div class="form-group">
                    <label class="col-sm-2 control-label">回复</label>
                    <div class="col-sm-10">
                        <textarea type="text" name="comment" class="form-control" id="txtComment" placeholder="请输入内容" style="height: 180px;"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">上传图片</label>
                    <div class="col-sm-10">
                        <div class="col-sm-10">
                            <input type="file" name="imageFile" class="form-control" id="txtImageFile"  />
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary" style="width: 100px;">提交</button>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
<script type="text/javascript" >
    $(function(){
        init();

        var html = "";
        html += "<tr><th class='col-md-2'></th><th class='col-md-10'></th></tr>";
        $.ajax({ url: "${ctx}/comment/getParentTitleAndContent/" + $('#commentId').val(),
            type:"get",
            async:true,
            dataType:"json",
            success: function(data){
                $.each(data, function (i, item) {
                    if (i==0){
                        html += "<tr><td>" + item.createUser + "</td><td><h4>" +item.title + "</h4>";
                        html += "<p>"+ item.comment + "</p>";
                    }else{
                        html += "<tr><td>" + item.createUser + "</td><td><p>" + item.comment + "</p>";
                    }
                    if (item.imageUrl != '' && item.imageUrl != undefined){
                        html += "<img src='${ctx}"+ item.imageUrl+"' alt='留言图片' class='img-responsive' />";
                    }
                    html += date2str(item.createTime) + "</td></tr>"
                });

                $("#ajaxContentList").html(html);
            }
        });
    });

    function date2str(date) {
        return date.substr(0,20)
    }

    var checkflag = { "comment":false};

    $("#txtComment").blur(function () {
        if ($("#txtComment").val() == ""){
            checkflag.comment = false;
        }else {
            checkflag.comment = true;
        }
    });
    $("#addForm").submit(function () {
        $("#txtComment").blur();
        return  checkflag.comment;
    });
</script>
<%@include file="/WEB-INF/pages/common/googleAnalytics.jsp"%>
</body>
</html>
