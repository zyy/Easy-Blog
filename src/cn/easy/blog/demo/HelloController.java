package cn.easy.blog.demo;

import com.jfinal.core.Controller;

public class HelloController extends Controller {
	public void index() {
		renderText("Hello EasyFramework World!");
	}
}