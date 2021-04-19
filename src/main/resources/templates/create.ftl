<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>支付</title>
</head>
<body>
  <!--这里使用了jquery的二维码生成插件jquery.qrcode.js: http://jeromeetienne.github.io/jquery-qrcode/-->
  <!--cdn来自bootcdn.cn-->
  <script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
  <script src="https://cdn.bootcdn.net/ajax/libs/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>

  <div id="myQrcode"></div>
  <script>
    jQuery('#myQrcode').qrcode({
      text	: "${codeUrl}"
    });
  </script>
</body>
</html>