package cn.easy.blog.demo;

import cn.easy.config.Constants;
import cn.easy.config.EasyConfig;
import cn.easy.config.Handlers;
import cn.easy.config.Interceptors;
import cn.easy.config.Plugins;
import cn.easy.config.Routes;

public class DemoConfig extends EasyConfig {
	public void configConstant(Constants me) {
		me.setDevMode(true);
	}

	public void configRoute(Routes me) {
		me.add("/hello", HelloController.class);
	}

	public void configPlugin(Plugins me) {
	}

	public void configInterceptor(Interceptors me) {
	}

	public void configHandler(Handlers me) {
	}

}