<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html";  charset="UTF-8">
    <title>抢红包测试</title>
    <script src="https://code.jquery.com/jquery-3.2.0.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#grap").click(function () {
                console.log("开始执行");
                var max = 30000;
                for (var i = 1; i < max; i++) {
                    console.log(i);
                    $.post({
                        url: "./grapRedPacket.do?redPacketId=1&userId=" + i,
                        //成功后的方法
                        success: function (result) {}
                    });
                }
            });
        });
    </script>
</head>
<body>
    <button id="grap">开始抢红包</button>
</body>
</html>