package cn.easy.blog.common;

import java.io.File;
import java.util.Properties;

import cn.easy.blog.controller.AdminController;
import cn.easy.blog.controller.IndexController;
import cn.easy.blog.controller.PostController;
import cn.easy.blog.controller.TagController;
import cn.easy.blog.model.Category;
import cn.easy.blog.model.Comment;
import cn.easy.blog.model.Post;
import cn.easy.blog.model.PostTag;
import cn.easy.blog.model.Tag;
import cn.easy.blog.model.User;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class BlogConfig extends JFinalConfig {
	public void configConstant(Constants constants) {
		constants.setDevMode(true);
	}

	public void configRoute(Routes routes) {
		routes.add("/", IndexController.class, "/index");
		routes.add("/admin", AdminController.class, "/admin");
		routes.add("/post", PostController.class, "/post");
		routes.add("/tag", TagController.class, "/tag");
	}

	public void configPlugin(Plugins plugins) {
		// 加载数据库和连接池相关的配置文件
		// 使用c3p0插件
		Properties properties = loadPropertyFile("config" + File.separator
				+ "c3p0.properties");
		C3p0Plugin c3p0Plugin = new C3p0Plugin(properties);
		plugins.add(c3p0Plugin);
		ActiveRecordPlugin aPlugin = new ActiveRecordPlugin(c3p0Plugin);
		aPlugin.setDialect(new MysqlDialect());
		plugins.add(aPlugin);

		aPlugin.addMapping("category", Category.class);
		aPlugin.addMapping("comment", Comment.class);
		aPlugin.addMapping("post", Post.class);
		aPlugin.addMapping("posttag", PostTag.class);
		aPlugin.addMapping("tag", Tag.class);
		aPlugin.addMapping("user", User.class);
	}

	public void configInterceptor(Interceptors interceptors) {
		interceptors.add(new SessionInViewInterceptor(true));
		// interceptors.add(new LoginInterceptor());
	}

	public void configHandler(Handlers handlers) {
	}

}