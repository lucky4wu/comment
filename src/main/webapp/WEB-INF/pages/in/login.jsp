<%--
  Created by IntelliJ IDEA.
  User: acc
  Date: 2017/3/9
  Time: 下午9:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/common/baseui.jsp" %>
    <title>登录</title>
</head>
<body>
<div class="container" style="width: 480px;">
    <div class="row">
        <div class="col-xs-12 col-md-12">
            <form class="form-horizontal"  action="${ctx}/in/login" id="loginForm" method="post">
                <div class="form-group">
                    <h2 class="col-sm-offset-2">登录评论后台</h2>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-10">
                        <input type="text" name="name" class="form-control" id="txtName" placeholder="请输入用户名"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-10">
                        <input type="password" name="password" class="form-control" id="txtPassword" placeholder="请输入密码"/>
                    </div>
                </div>
                <div class="form-group">
                    <span class="col-sm-offset-2 label label-danger">${errorMsg}</span>
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
</body>
</html>
