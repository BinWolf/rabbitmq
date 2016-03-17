# rabbitmq 在mac中安装rabbitmq
下载安装包：
1、官网链接:http://www.rabbitmq.com/install-standalone-mac.html
2、解压
3、启用插件，开启浏览器访问
sbin/rabbitmq-plugins enable rabbitmq_management
4、启动服务
 sbin/rabbitmq-server 

              RabbitMQ 3.6.1. Copyright (C) 2007-2016 Pivotal Software, Inc.
  ##  ##      Licensed under the MPL.  See http://www.rabbitmq.com/
  ##  ##
  ##########  Logs: /Users/wolf/soft/rabbitmq_server-3.6.1/var/log/rabbitmq/rabbit@wolf.log
  ######  ##        /Users/wolf/soft/rabbitmq_server-3.6.1/var/log/rabbitmq/rabbit@wolf-sasl.log
  ##########
              Starting broker... completed with 6 plugins.
              
  成功！
4、浏览器中访问
http://127.0.0.1:15672/
Username:guest
Password:guest

http访问端口：15672
tcp访问端口：5672

