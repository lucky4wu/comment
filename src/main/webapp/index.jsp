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


</form>
<script type="text/javascript" src="static/js/jquery-migrate-1.4.1.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        var html = "";
        html += "<tr><th style='width: 500px;'>标题</th><th>用户</th><th>时间</th></tr>";
        $.ajax({ url: "http://localhost:9193/comment/listPage",
            type:"get",
            async:false,
            dataType:"json",
            success: function(data){
                $.each(data, function (i, item) {
                    html += "<tr>";
                    html += "<td>" + item[i].title + "</td>";
                    html += "<td>" + item[i].createUser + "</td>";
                    html += "<td>" + item[i].createTime + "</td>";
                    html += "</tr>";
                });

                $("#ajaxListPage").html(html);
            }
        });
    });

    $("#addForm").submit(function () {
        var flag = true;
        if ($("#txtTitle").val() == "" ){
            flag = false;
        }
        if ($("#txtComment").val() == ""){
            flag = false;
        }
        return  flag;
    });
</script>
</body>
</html>
