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
    <title>Easy Way 旅游</title>
    <%@ include file="/WEB-INF/pages/common/baseui.jsp" %>
</head>
<body onresize="init()" onload="loaded()">
<%@ include file="/WEB-INF/pages/common/top.jsp" %>

<div class="container-fluid main-container" id="wrapper">

    <div class="row">
        <div class="col-md-12">
            <p id="pullDownContinue" style="display: none;" class="text-center">继续下拉可刷新</p>
            <p id="pullDownRelease" style="display: none;" class="text-center">松开即可刷新</p>
        </div>
        <div class="col-md-12" >
            <table class="table table-hover" id="ajaxListPage" >

            </table>
            <nav aria-label="Page navigation">
                <ul class="pagination" id="pagination">

                </ul>
            </nav>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <h3>路过留下 <span class="label label-info">爪印</span></h3>
        </div>
        <div class="col-md-10">
            <form class="form-horizontal"  action="${ctx}/comment/addComment" id="addForm" method="post" enctype="multipart/form-data">
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
                    <label class="col-sm-2 control-label">上传图片</label>
                    <div class="col-sm-10">
                        <input type="file" name="imageFile" class="form-control" id="txtImageFile"  />
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
                    <td id="txtUserNickName">${user.nickName}</td>
                </tr>
                <tr>
                    <th>ID</th>
                    <td><span id="txtUserNo"></span></td>
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
                <div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width: 50%">
                    正在施工
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="table">
                <tr class="col-xs-12 col-md-6">
                    <th>客服小A:</th>
                    <td><img src="${ctx}/static/image/my_wechat.jpg" alt="微信二维码" style="width: 320px;height: 420px;"/></td>
                </tr>
                <tr class="col-xs-12 col-md-6">
                    <th>客服小伟（微信号）:</th>
                    <td>994665236</td>
                </tr>
            </table>
        </div>
    </div>

    <div id="using_json_2">

    </div>

</div>


<script type="text/javascript" >

    var pageSize = 20;
   $(function(){
       init();

        searchListPgae(1);

        $.ajax({ url: "${ctx}/index/getUserNo",
            type:"get",
            async: true,
            dataType:"text",
            success: function (data) {
                $("#txtUserNo").html(data);
            }
        })

    });

   //*********屏幕滚动刷新begin**********
    var myScroll;
    function loaded() {
        var height = -80;
        var touchY = 60;
        myScroll = new IScroll('#wrapper', {
            probeType: 1,
            bounce: true,
            preventDefault:false
        });

        myScroll.on('scrollEnd', function () {
            var scrollTop = $(document).scrollTop();
            if (this.distY > touchY && scrollTop <= height){
                setTimeout('pullDownRefresh()',2000);
            }
            $('#pullDownContinue').hide();
            myScroll.refresh();
        });

        myScroll.on('scroll', function () {
            var scrollTop = $(document).scrollTop();
            if (scrollTop > height && scrollTop < 0 ){
                $('#pullDownContinue').show();
            }
            if (scrollTop <= height){
                $('#pullDownContinue').hide();
                $('#pullDownRelease').show();
            }
            if (scrollTop == 0){
                $('#pullDownRelease').hide();
            }
        });
        document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);
    }

    function pullDownRefresh(){
        $('#pullDownRelease').hide();
        searchListPgae(1);
    }
    //*********屏幕滚动刷新end**********


   function searchListPgae(curPage) {
       var html = "";
       html += "<tr><th class='col-md-12'></th></tr>";
       var pageHtml = "";

       $.ajax({ url: "${ctx}/comment/listPage",
           type:"get",
           async:true,
           dataType:"json",
           data:{
            "title": $('#searchTitleTxt').val(),
               "page": curPage,
               "pageSize":pageSize
            },
           success: function(data){
               var treeArr = new Array();
               $.each(data.pageList, function (i, item) {
                   html += "<tr>";
                   html += "<td><div class='col-md-12'><a href='${ctx}/comment/contentList/"+ item.id + "' >" + item.title + "</a></div>";
                   if (item.imageUrl != '' && item.imageUrl != undefined){
                       html += "<div class='col-md-4 col-xs-12'>";
                       html += "<a href='${ctx}"+ item.imageUrl+"' class='thumbnail' >";
                       html += "<img src='${ctx}"+ item.imageUrl+"' alt='留言图片' class='img-responsive' />";
                       html += "</a></div>";
                       html += "<div class='col-md-8 col-xs-12'>" + convertStrTo200(item.comment) + "</div>";
                   }else{
                       html += "<div class='col-md-12 col-xs-12'>" + convertStrTo200(item.comment) + "</div>";
                   }
                   html += "<div class='col-md-12'><h6><small>" + item.createUser + "</small>&nbsp;";
                   html += "<small>" + date2str(item.createTime) + "</small></h6></div>";
                   html += "</td></tr>";
                   treeArr[i] = { "id" : item.id+"", "parent" : "#", "text" : text };
               });

               $('#using_json_2').jstree({ 'core' : {
                   'data' : treeArr
               },
                   "plugins" : [ "wholerow" ]
               });

               $("#ajaxListPage").html(html);

               pageHtml += "<li><a href='#' onclick='searchListPgae("+data.prevPage+")' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>";
               if (data.pageCount > 5){
                   pageHtml += "<li><a href='#' onclick='searchListPgae("+(data.currentPage-2)+")'>"+(data.currentPage-2)+"<span class='sr-only'>(current)</span></span></a></li>";
                   pageHtml += "<li><a href='#' onclick='searchListPgae("+(data.currentPage-1)+")'>"+(data.currentPage-1)+"<span class='sr-only'>(current)</span></span></a></li>";
                   pageHtml += "<li class='active'><>"+data.currentPage+"<span class='sr-only'>(current)</span></span></li>";
                   pageHtml += "<li><a href='#' onclick='searchListPgae("+(data.currentPage+1)+")'>"+(data.currentPage+1)+"<span class='sr-only'>(current)</span></span></a></li>";
                   pageHtml += "<li><a href='#' onclick='searchListPgae("+(data.currentPage+2)+")'>"+(data.currentPage+2)+"<span class='sr-only'>(current)</span></span></a></li>";
               }else{
                    for (var i=1;i<=data.pageCount;i++){
                        if(i==data.currentPage){
                            pageHtml += "<li class='active'><span>"+ data.currentPage+"<span class='sr-only'>(current)</span></span></li>";
                        }else{
                            pageHtml += "<li><a href='#' onclick='searchListPgae("+i+")'>"+i+"<span class='sr-only'>(current)</span></a></li>";
                        }
                    }
               }

               pageHtml += "<li><a href='#' onclick='searchListPgae("+data.nextPage+")' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>";
               $("#pagination").html(pageHtml);
           }
       });
   }

   function date2str(date) {
       return formatterdate(date, 0);
   }

   var checkflag = {"title":false, "comment":false};
   $("#txtTitle").blur(function () {
       if ($("#txtTitle").val() == "" || $("#txtTitle").val().length > 150){
            checkflag.title = false;
       }else {
           checkflag.title = true;
       }
   });

   $("#txtComment").blur(function () {
       if ($("#txtComment").val() == "" || $("#txtComment").val().length > 1000){
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
<%@ include file="/WEB-INF/pages/common/footer.jsp" %>
</body>
</html>
