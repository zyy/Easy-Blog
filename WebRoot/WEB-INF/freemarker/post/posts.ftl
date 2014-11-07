<#if posts?? && posts?size gt 0>
	<style type="text/css">
		.div-task{
			border-bottom:1px dashed gray;
			padding-bottom:5px;
			padding-top:10px;
		}
		.div-task .title{
			font-weight:bold;
			font-size:16px;
		}
	</style>
	<#list posts as post>
	<div class="div-task">
        <div class="post-date-ribbon">
            <div class="corner">${post.post_date}</div>
        </div>
        <header>
            <h2 class="title">
                <a href="${ctx}blog/${post.id}" rel="bookmark">${post.title}</a>
            </h2>
            <div class="post-info">
            <div class="author_mt hp_meta"><span class="mt_icon"></span>
                 ${post.user_id}
            </div>
            <div class="cat_mt hp_meta">${post.category_id}</div>
            <div class="time_mt hp_meta"><span class="time_icon"></span>${post.post_date}</div>
            <div class="view_mt hp_meta"><span class="view_icon"> </span>${post.content}</div>
        </header>
        <div class="readMore">
            <a href="${ctx}blog/${post.id}" title="${post.title}" rel="more">阅读全部</a>
        </div>
    	</div>
    </div>
	</#list>			  
</#if>