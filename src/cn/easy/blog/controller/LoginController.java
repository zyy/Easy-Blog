package cn.easy.blog.controller;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

public class LoginController extends Controller {
	@ClearInterceptor(ClearLayer.ALL)
	@ActionKey("/login")
	public void index() {
		render("login/index.ftl");
	}
	
}
