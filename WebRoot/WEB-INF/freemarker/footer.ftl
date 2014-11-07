<footer>
    <div class="container">
        <div class="footer-widgets">
            <div class="f-widget f-widget-1">
                <div class="widget">
                    <h3>网站相关</h3>
                    <ul>
                        <li>
                            <a title="Rss 订阅" target="_blank" href="${ctx}rss.xml">Rss 订阅</a>
                        </li>
                        <li>
                            <a title="Git地址" target="_blank" rel="nofollow" href="">本站源码</a>
                        </li>
                        <li>
                            <a title="新浪微博" target="_blank" rel="nofollow" href="">新浪微博</a>
                        </li>
                        <li>
                            <a title="腾讯微博" target="_blank" rel="nofollow" href="">腾讯微博</a>
                        </li>
                    </ul>
                    QQ : <a target="_blank" rel="nofollow" href="http://wpa.qq.com/msgrd?v=3&amp;uin=596392912&amp;site=qq&amp;menu=yes">596392912</a>
                </div>
            </div>
            <div class="f-widget f-widget-2">
                <div class="widget">
                    <h3>热门标签</h3>
                    <div class="tagcloud">
                        <#if tags?? && tags?size gt 0>
						   <#list tags as tag>
                            <a title="${tag.name}" href="${ctx}tags/${tag.name}">
                                ${tag.name}
                                <span style="color:tan;">&nbsp;${tag.count}</span>
                            </a>
                           </#list>	
						</#if>
                    </div>
                </div>
            </div>
        </div><!--.footer-widgets-->
    </div>
</footer>