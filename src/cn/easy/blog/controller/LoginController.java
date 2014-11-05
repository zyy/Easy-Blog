package cn.easy.blog.controller;

import cn.easy.aop.ClearInterceptor;
import cn.easy.aop.ClearLayer;
import cn.easy.core.ActionKey;
import cn.easy.core.Controller;

public class LoginController extends Controller {
	@ClearInterceptor(ClearLayer.ALL)
	@ActionKey("/login")
	public void index() {
		render("login/index.ftl");
	}
	
}
