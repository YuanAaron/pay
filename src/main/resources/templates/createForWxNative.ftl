<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>支付</title>
</head>
<body>
  <div id="myQrcode"></div>
  <div id="orderId" hidden>${orderId}</div>
  <div id="returnUrl" hidden>${returnUrl}</div>

  <!--这里使用了jquery的二维码生成插件jquery.qrcode.js: http://jeromeetienne.github.io/jquery-qrcode/-->
  <!--cdn来自bootcdn.cn-->
  <script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
  <script src="https://cdn.bootcdn.net/ajax/libs/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>

  <script>
    jQuery('#myQrcode').qrcode({
      text	: "${codeUrl}"
    });

    $(function () {
      //定时器轮询
      setInterval(function () {
        console.log("开始查询支付日志...")
        $.ajax({
          url: '/pay/payStatus',
          data: {
            'orderId': $('#orderId').text()
          },
          success: function (res) {
            console.log(res);
            if (res.platformStatus != null && res.platformStatus === 'SUCCESS') {
              location.href = $('#returnUrl').text()
            }
          },
          error: function (res) {
            alert(res);
          }
        });
      },2000)
    });
  </script>
</body>
</html>