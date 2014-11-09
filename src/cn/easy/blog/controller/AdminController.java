package cn.easy.blog.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import cn.easy.blog.common.WebKeys;
import cn.easy.blog.model.Category;
import cn.easy.blog.model.Post;
import cn.easy.blog.model.Tag;
import cn.easy.blog.model.User;
import cn.easy.blog.validator.LoginValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class AdminController extends Controller {

	public void index() {
		render("login.html");
	}

	@Before(LoginValidator.class)
	public void login() {
		String account = getPara("account");
		String pwd = getPara("pwd");

		User user = User.dao.getByAccount(account);

		if (user == null) {
			setAttr("msg", "帐号或者密码错误");
			index();
			return;
		}

		System.out.println(">>>>>>>>>> pwd " + DigestUtils.md5Hex(pwd));

		if (!user.getStr("password").equals(DigestUtils.md5Hex(pwd))) {
			setAttr("msg", "帐号或者密码错误");
			index();
			return;
		}

		// 添加cookie
		setCookie(WebKeys.COOKIE_USER_ID, String.valueOf(user.getInt("id")), 60 * 60 * 24 * 30);

		// 如果有引用链接，回到登录前的页面，没有就去首页
		HttpSession session = getSession();
		System.out.println(">>>>>>>>." + session);
		session.setAttribute(WebKeys.SESSION_USER, user);
		Object referer = session.getAttribute(WebKeys.SESSION_REFERER);
		if (referer != null) {
			session.removeAttribute(WebKeys.SESSION_REFERER);
			redirect(String.valueOf(referer));
			return;
		}
		render("index.html");
	}
	
	public void posts() {
		setAttr("categorys", Category.dao.getAllCategorys());
		setAttr("posts", Post.dao.getIndexPosts());
		setAttr("tags", Tag.dao.getAll());
		render("post.html");
	}
}
