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
<body id="blog" class="home blog main cat-32-id cat-80-id">
	<@common.topNavbar />
	<div class="main-container">
	    <div id="page">
	      <div class="content">
			<article class="article">
		        <div id="content_box" class="home_page">
					<#include "post/posts.ftl" > 
			    </div>
		    </article>
		    <aside class="sidebar c-4-12">
		        <div id="sidebars" class="g">
		            <div class="sidebar">
		                
		            </div>
		        </div>
		    </aside>
		 </div>
		</div>
     </div>
     <#include "footer.ftl" > 
</body>
</html>