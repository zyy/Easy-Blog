package cn.easy.blog.common;

import java.io.File;
import java.util.Properties;

import cn.easy.blog.controller.IndexController;
import cn.easy.blog.controller.LoginController;
import cn.easy.blog.interceptor.LoginInterceptor;
import cn.easy.blog.model.Category;
import cn.easy.blog.model.Comment;
import cn.easy.blog.model.Post;
import cn.easy.blog.model.PostTag;
import cn.easy.blog.model.Tag;
import cn.easy.blog.model.User;
import cn.easy.config.Constants;
import cn.easy.config.EasyConfig;
import cn.easy.config.Handlers;
import cn.easy.config.Interceptors;
import cn.easy.config.Plugins;
import cn.easy.config.Routes;
import cn.easy.core.EasyFramework;
import cn.easy.plugin.activerecord.ActiveRecordPlugin;
import cn.easy.plugin.activerecord.dialect.MysqlDialect;
import cn.easy.plugin.c3p0.C3p0Plugin;
import cn.easy.render.FreeMarkerRender;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;

public class BlogConfig extends EasyConfig {
	public void configConstant(Constants constants) {
		constants.setDevMode(true);
		constants.setBaseViewPath("/WEB-INF/freemarker");

		Configuration freeMarkerConfig = FreeMarkerRender.getConfiguration();
		try {
			// 将contentPath设置为freemarker共享变量
			freeMarkerConfig.setSharedVariable("ctx", EasyFramework
					.getInstance().getServletContext().getContextPath());
		} catch (TemplateModelException e) {
			throw new IllegalStateException(e);
		}
	}

	public void configRoute(Routes routes) {
		routes.add("/", IndexController.class);
		routes.add("/login", LoginController.class);
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

		aPlugin.addMapping("Category", Category.class);
		aPlugin.addMapping("Comment", Comment.class);
		aPlugin.addMapping("Post", Post.class);
		aPlugin.addMapping("PostTag", PostTag.class);
		aPlugin.addMapping("Tag", Tag.class);
		aPlugin.addMapping("User", User.class);
	}

	public void configInterceptor(Interceptors interceptors) {
		interceptors.add(new LoginInterceptor());
	}

	public void configHandler(Handlers handlers) {
	}

}