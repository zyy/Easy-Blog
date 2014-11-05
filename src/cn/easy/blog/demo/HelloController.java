package cn.easy.blog.demo;

import cn.easy.core.Controller;

public class HelloController extends Controller {
	public void index() {
		renderText("Hello EasyFramework World!");
	}
}