<#if posts?? && posts?size gt 0>
	<#list posts as post>
		<div class="post excerpt">
	        <div class="post-date-ribbon">
	            <div class="corner"></div>${post.post_date}
	        </div>
	        <header>
	            <h2 class="title">
	                <a href="${ctx}blog/${post.id}" rel="bookmark">${post.title}</a>
	            </h2>
	            <div class="post-info">
	            <div class="author_mt hp_meta"><span class="mt_icon"></span>
	                 ${post.user_id}
	            </div>
	            <div class="cat_mt hp_meta"><span class="mt_icon"></span>${post.category_id}</div>
	            <div class="time_mt hp_meta"><span class="time_icon"></span>${post.post_date}</div>
	            <div class="view_mt hp_meta"><span class="view_icon"> </span>36</div>
	        </header>
	        <div class="post-content image-caption-format-1">${post.content}</div>
	        <div class="readMore">
	            <a href="${ctx}blog/${post.id}" title="${post.title}" rel="more">阅读全部</a>
	        </div>
	    	</div>
		</div>		  
	</#list>	
</#if>