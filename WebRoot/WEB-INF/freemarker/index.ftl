<#import "common.ftl" as common>
<!DOCTYPE html>
<html>
<head>
    <@common.preventIE />
	<@common.headerReference />
	<title>欢迎访问我的博客</title>
	<meta name="Keywords" content="EasyBlog">
	<meta name="description" content="EasyBlog - 简洁易用的博客系统系统">
</head>
<body>
	<@common.topNavbar />
	<#include "post/posts.ftl" > 
</body>
</html>