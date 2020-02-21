<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hautbbs Manage</title>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.css">
    <script src="../static/js/jquery-3.3.1.js" type="text/javascript"></script>
    <script src="../static/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../static/js/myUtils.js" type="text/javascript"></script>
</head>
<style>
    .form-inline {
        margin-top: 60px;
    }

</style>

<body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <div class="navbar-brand">Hautbbs Manage</div>
            </div>
            <div id="navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="active"> <a href="#">帖子</a></li>
                    <li><a href="">商品</a></li>
                    <li><a href="">用户</a></li>
                </ul>
            </div>
        </div>
    </nav>


    <div class="container">
        <div class="row">

            <div class="col-md-4 col-md-offset-4">
                <button type="button" class="btn btn-info current-topic ">当前帖子</button>
                <button type="button" class="btn btn-info deleted-topic ">已删帖子</button>
                <form class="form-inline">


                    <label for="exampleInputEmail1" hidden=true>Account</label>
                    <input type="text" class="form-control" id="exampleInputEmail1" placeholder="按关键字搜索">

                    <button type="submit" class="btn btn-primary">搜索
                    </button>

                </form>
            </div>
            <div class="col-md-8 col-md-offset-2">

            </div>

        </div>
        <p>
            <div class="row">
                <div class="col-md-8 col-md-offset-2">
                    <table style="table-layout:fixed" class="table table-hover" id="topic_table">
                        <thead>
                            <tr>
                                <th style="width: 10%">帖子id</th>
                                <th style="width: 15%">发帖人</th>
                                <th style="width: 50%">内容 </th>
                                <th style="width: 15%">发布时间</th>
                                <th style="width: 10%">操作</th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>

            <!-- 显示分页信息 -->

            <div class="row">
                <div class="col-md-6" id="page_info_area">
                    <%--此处显示页数信息--%>
                </div>

                <div class="col-md-6" id="page_nav_area">
                    <nav aria-label="Page navigation" id="nav_label">
                        <%--此处显示分页导航条信息--%>
                    </nav>
                </div>

            </div>
    </div>
    <script type="text/javascript">
        $(function () {
            //先去首页
            to_page(1);
        });

        function to_page(pn) {
            $.ajax({
                url: "${APP_PATH}/manage/topic_product/get_topicList.do",
                data: {
                    pageNum: pn,
                    topicType: 1
                },
                type: "GET",
                success: function (result) {
                    //console.log(result.pagemsg.pageinfo.total);
                    build_topic_table(result);
                    build_page_info(result);
                    build_page_nav(result);
                }
            })
        }

        //解析并显示topic数据
        function build_topic_table(result) {
            //因为使用ajax,需要构建数据前先清空当前数据
            $("#topic_table tbody").empty();

            var topics = result.data.list;
            $.each(topics, function (index, item) {
                var topicId = $("<td></td> ").append(item.id);
                var fromUserName = $("<td></td>").append(item.basicUser.nickName);
                var content = $("<td></td>").addClass("content").append(item.content);
                var time = $("<td></td>").append(timeStamp2String(parseInt(item.createTime)));
                var deleBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete ").attr("uid",item.basicUser.uid).attr("tid",item.id).append("删除");
                var btnTd = $("<td></td>").append(deleBtn);
                $("<tr></tr>").append(topicId).append(fromUserName).append(content).append(time).append(btnTd).appendTo("#topic_table tbody");
            });
        }

        //解析并显示分页信息
        function build_page_info(result) {
            //构建前先清空
            $("#page_info_area").empty();

            $("#page_info_area").append("当前第" + result.data.pageNum + "页，总" + result.data.pages + "页，总" + result.data.total + "条记录")
        }

        //解析显示分页条数据(参照list.jsp)
        function build_page_nav(result) {
            //构建前先清空
            $("#nav_label").empty();

            var ul = $("<ul></ul>").addClass("pagination");
            var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
            var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;").attr("href", "#"));
            if (!result.data.hasPreviousPage) {
                prePageLi.addClass("disabled");
            }
            //添加点击事件
            firstPageLi.click(function () {
                to_page(1);
            });
            prePageLi.click(function () {
                to_page(result.data.pageNum - 1);
            })

            var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;").attr("href", "#"));
            var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"));
            if (!result.data.hasNextPage) {
                nextPageLi.addClass("disabled");
            }
            // 添加点击事件
            nextPageLi.click(function () {
                to_page(result.data.pageNum + 1);
            });
            lastPageLi.click(function () {
                to_page(result.data.pages)
            });

            ul.append(firstPageLi).append(prePageLi);
            //遍历中间的页并添加到<li>中
            $.each(result.data.navigatepageNums, function (index, item) {
                var numLi = $("<li></li>").append($("<a></a>").append(item).attr("href", "#"));
                if ((result.data.pageNum == item)) {
                    numLi.addClass("active");
                }
                ul.append(numLi);
                numLi.click(function () {
                    to_page(item);
                })
            })
            ul.append(nextPageLi).append(lastPageLi).appendTo("#nav_label");
        }

        // 删除帖子
        $(document).on("click", ".delete", function() {// 为每个编辑按钮绑定事件弹出提示框
            var reason=prompt("删帖原因")
            reason = "您的帖子被删除，原因可能为："+reason
            delete_topic($(this).attr("tid"))
            send_notice($(this).attr("uid"),reason)
        })

        function delete_topic(tid) {
            $.ajax({
                url: "${APP_PATH}/manage/topic_product/delete_topic.do",
                data: {
                    id:tid
                },
                type: "POST"
            })
        }

        function send_notice(toUid,msg) {
            $.ajax({
                url: "${APP_PATH}/manage/topic_product/notice_owner.do",
                data: {
                    toUid:toUid,
                    msg:msg
                },
                type: "POST"
            })
        }





    </script>
    <style>
        .content{
        overflow:hidden;
        white-space:nowrap;
        text-overflow:ellipsis;
        }
    </style>
</body>

</html>