<%--
  Created by IntelliJ IDEA.
  User: acc
  Date: 2017/3/23
  Time: 下午8:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3 col-sm-3 col-xs-4">
                <p class="navbar-text">Easy Way 旅游</p>
            </div>

            <div class="col-md-5 col-sm-5 col-xs-8">
                <ul class="nav navbar-nav nav-pills">
                    <li class="active"><a href="${ctx}/">首页 <span class="sr-only">(current)</span></a></li>
                    <li><a href="#">待续</a></li>
                </ul>
            </div>

            <div class="col-md-4 col-sm-4 col-xs-12">
                <form class="navbar-form navbar-right form-inline" role="search">
                    <div class="form-group">
                        <label class="sr-only" for="searchTitleTxt">Search</label>
                        <div class="input-group " >
                            <input type="text" name="title" class="form-control" id="searchTitleTxt" placeholder="搜索..."/>
                            <div class="input-group-btn" id="searchTitleBtn">
                                <button type="button" class="btn btn-default" aria-label="Search">
                                    <span class="glyphicon glyphicon-search"
                           style="padding-left: 3px;padding-right: 3px; padding-bottom: 3px;padding-top: 3px;" ></span>
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

        </div>
    </div>
</nav>
<script type="text/javascript">
    $("#searchTitleBtn").click(function () {
        searchListPgae(1);
    });
</script>
</body>
</html>
