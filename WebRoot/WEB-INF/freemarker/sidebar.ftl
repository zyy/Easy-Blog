<ul class="sidebar_list">
    <div id="tabber">
        <ul class="tabs">
            <li>
                <a href="#popular-posts" class="selected">热门话题</a>
            </li>
            <li class="tab-recent-posts">
                <a href="#recent-posts">最新博文</a>
            </li>
        </ul>
        <!-- end .tabs-->
        <div class="clear"></div>
        <div class="inside">
            <div id="popular-posts">
                <ul>
                    #for (Blog blog : topBlogs())
                    <li>
                        <div class="info">
                            <p class="entry-title">
                                <a title="${blog.get('title')}" href="${BASE_PATH }blog/${blog.get('id')}">${blog.get('title')}</a>
                            </p>
                            <div class="meta">
                                <span>${format(blog.get('update_time')) }</span><span>-  ${blog.get('view_count')}浏览量</span>
                            </div>
                        </div>
                        <!-- end .info-->
                        <div class="clear"></div>
                    </li>
                    #end
                </ul>
            </div>
            <!-- end #popular-posts-->
            <div id="recent-posts">
                <ul>
                    #for (Blog blog : lateBlogs())
                    <li>
                        <div class="info">
                            <p class="entry-title">
                                <a title="${blog.get('title')}" href="${BASE_PATH }blog/${blog.get('id')}">${blog.get('title')}</a>
                            </p>
                            <div class="meta">
                                <span>${format(blog.get('update_time')) }</span>
                                <span>-</span>
                                <span data-thread-key="${blog.get('id')}" data-count-type="comments" class="ds-thread-count"></span>
                            </div>
                        </div>
                        <!-- end .info-->
                        <div class="clear"></div>
                    </li>
                    #end
                </ul>
            </div>
            <!-- end #recent-posts-->
            <div style="display: none;" class="clear"></div>
        </div>
        <!-- end .inside-->
        <div class="clear"></div>
    </div>
    <li class="widget widget-sidebar">
        <form id="searchform" method="post" action="${BASE_PATH }search" _lpchecked="1" class="search-form">
            <fieldset>
                <input id="s" type="text" name="s" x-webkit-speech="x-webkit-speech" placeholder="搜索..."/>
                <input id="search_submit" type="submit" value="好手氣"/>
            </fieldset>
        </form>
    </li>
    <li class="widget widget-sidebar">
        <div style="width:auto" class="rssbook light">
            <p class="info">
                填写您的邮件地址:
            </p>
            <div class="mailInput">
                <form action="http://list.qq.com/cgi-bin/qf_compose_send" target="_blank" method="post">
                    <input type="hidden" name="t" value="qf_booked_feedback"/>
                    <input type="hidden" name="id" value="fbb30074540693f33c4c8ef64765c6cd79d6957e68d752c5"/>
                    <input id="to" name="to" type="text" placeholder="请输入邮箱..." class="rsstxt"/>
                    <div class="rssbutton">
                        <input type="submit" value="订阅" style="width: 100%;"/>
                    </div>
                </form>
            </div>
        </div>
    </li>
    <li class="widget widget-sidebar">
        <h3>最新回复：</h3>
        <ul data-num-items="10" class="ds-recent-comments"></ul>
    </li>
    <li class="widget widget-sidebar">
        <h3>赞助商：</h3>
        <div>
            <a href="https://portal.qiniu.com/signup?code=3lhz6lzh2821d" title="七牛云存储 - 移动时代的云存储服务商！" target="_blank" rel="nofollow">
                <img alt="七牛云存储 - 移动时代的云存储服务商！" width="300" src="${options.get('cdn_path')}/images/boltfree.png"/>
            </a>
        </div>
    </li>
    <li class="widget widget-sidebar">
        <h3>最近访客：</h3>
        <div>
            <ul data-num-items="14" class="ds-recent-visitors"></ul>
        </div>
    </li>
</ul>