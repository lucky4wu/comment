<%--
  Created by IntelliJ IDEA.
  User: CrazyFive
  Date: 2017/3/26
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>Easy Way 旅游</title>
    <%@ include file="/WEB-INF/pages/common/baseui.jsp" %>
</head>
<body onresize="init()">
<%@ include file="/WEB-INF/pages/common/top.jsp" %>
<div class="container-fluid main-container" >
    <div class="row">
        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
            <!-- Indicators -->
            <ol class="carousel-indicators">
                <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                <li data-target="#carousel-example-generic" data-slide-to="2"></li>
            </ol>

            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox">
                <div class="item active">
                    <img src="${ctx}/static/image/motianlun.jpg" alt="摩天轮" style="height: 450px;width: 1024px;">
                    <div class="carousel-caption">
                        摩天轮
                    </div>
                </div>
                <div class="item">
                    <img src="${ctx}/static/image/qingshuisi.jpg" alt="清水寺" style="height: 450px;width: 1024px;">
                    <div class="carousel-caption">
                        清水寺
                    </div>
                </div>
                <div class="item">
                    <img src="${ctx}/static/image/nailianggongyuan.jpg" alt="奈良公园" style="height: 450px;width: 1024px;">
                    <div class="carousel-caption">
                        奈良公园
                    </div>
                </div>
            </div>

            <!-- Controls -->
            <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
        <div class="col-md-12">
            </br>
            <div class="jumbotron">
                <h1>马上订购!</h1>
                <p>198即可体验日本夏日祭，所有服务都在携程预订，订单下达后可与携程比价，100%贵就退，马上联系客服↓</p>
                <p><a class="btn btn-primary btn-lg" href="#" role="button" data-toggle="modal" data-target="#myModal">了解更多</a></p>
            </div>
            <table class="table">
                <tr class="col-xs-12 col-md-6">
                    <th>客服小蔡:</th>
                    <td><img src="${ctx}/static/image/caie_wechat.jpg" alt="微信二维码" style="width: 320px;height: 420px;"/></td>
                </tr>
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
        <div class="page-header">
            <h1>现在预订即享五折优惠，原价<s>399</s>，现价198<small>带您领略不同的大阪行</small></h1>
            <p>用困难模式在日本大阪自由行，所有机票和酒店都在携程预订，客户可以自由选择客服帮你直接预订或者自己下订单，
                198即可享受亲自一对一辅导日本旅行，解决您在订购机票酒店签证中遇到的困难，另外还附赠7天客服支持，日期
                从你出行日期前一天开始计算。
            </p>
            <p>免费享受24*7的完全支持，从现在就开始扫码预订吧！</p>
            <p>费用包含：</p>
            <ul>
                <li>198服务费</li>
                <li>机票、酒店、签证等费用（根据订单实际金额计算，请联系客服沟通）</li>
                <li>24*7的完全支持，保障您安心自由行，完全免费哦亲</li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript" >
    $(function() {
        init();

        activeMenu();
    });

    function activeMenu() {
        $('#main-menu li').removeAttr("class");
        $('#main-menu li').next().attr("class", "active");
    }
</script>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">订购</h4>
            </div>
            <div class="modal-body">
                <p>扫描客服微信二维码，暗号<mark>Hard Way</mark></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>
