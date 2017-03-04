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
</head>
<body>
<p>Acc's 匿名版，欢迎灌水！</p>
<table id="ajaxListPage"></table>

<form action="/comment/addComment" id="addForm" method="post">
    <table>
        <tr>
            <th>标题</th>
            <td>
                <input name="title" id="txtTitle" />
            </td>

        </tr>
        <tr>
            <th>内容</th>
            <td>
                <textarea name="comment" id="txtComment" style="width: 700px;height: 200px;"></textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="提交" style="height: 45px;width: 120px;color: dodgerblue"/>
            </td>
        </tr>
    </table>
<div>
    <p>请愉快的向穷苦的开发者捐款加快施工进度</p>
    <p><em>一分钱也是爱</em></p>
    <img src="${ctx}/static/image/zhifubao.jpg" alt="" style="height: 480px;width: 320px;"/>
    <p><em>更多的爱</em></p>
    <img src="${ctx}/static/image/zhifubao-2.jpg" alt="" style="height: 480px;width: 320px;"/>
</div>

</form>
<script type="text/javascript" src="${ctx}/static/js/jquery-1.4.3.js" ></script>
<script type="text/javascript" >
   $(function(){
        var html = "";
        html += "<tr><th style='width: 200px;'>标题</th><th style='width: 500px;'>内容</th><th>用户</th><th>时间</th></tr>";
        $.ajax({ url: "${ctx}/comment/listPage",
            type:"get",
            async:true,
            dataType:"json",
            success: function(data){
                $.each(data, function (i, item) {
                    html += "<tr>";
                    html += "<td>" + item.title + "</td>";
                    html += "<td>" + item.comment + "</td>";
                    html += "<td>" + item.createUser + "</td>";
                    html += "<td>" + item.createTime + "</td>";
                    html += "</tr>";
                });

                $("#ajaxListPage").html(html);
            }
        });
    });

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
</body>
</html>
