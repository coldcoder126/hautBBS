# hautBBS
<img src="https://github.com/coldcoder126/git_images/blob/master/hautBBS.JPG" width="200" height="200" alt="微信扫一扫可体验" align=center>
一款校园论坛小程序,旨在打造一个校园内活动宣传和二手物品发布的平台。  
服务器为Tomcat,后台使用SSM框架+MySQL，前端使用微信小程序，阿里云oss存储图片，solr作为搜索引擎，Nginx做反向代理服务器
功能有： 

### 用户
- 注册：微信自动获取  
- 登录：使用Guava cache存放登录token  
- 索引：可分类索引  
- 修改个人信息：包括头像，简介等  
- 私信：具有已读回执功能  
- 发帖：按模块发帖  
- 积分：回复者-1积分，被回复者+1积分   
- 管理帖子：删除帖子或更改其状态  

### 管理员
- 登录
- 管理帖子
- 管理用户
