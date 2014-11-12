package cn.easy.blog.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import cn.easy.blog.common.WebKeys;
import cn.easy.blog.model.Category;
import cn.easy.blog.model.Comment;
import cn.easy.blog.model.Post;
import cn.easy.blog.model.PostTag;
import cn.easy.blog.model.Tag;
import cn.easy.blog.model.User;
import cn.easy.blog.util.PageKit;
import cn.easy.blog.validator.LoginValidator;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

public class AdminController extends Controller {

	@ClearInterceptor(ClearLayer.ALL)
	public void index() {
		render("login.html");
	}

	@ClearInterceptor(ClearLayer.ALL)
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
		setCookie(WebKeys.COOKIE_USER_ID, String.valueOf(user.getInt("id")),
				60 * 60 * 24 * 30);

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
		posts();
	}

	public void posts() {
		setAttr("categorys", Category.dao.getAllCategorys());
		Page<Post> page = Post.dao.paginate(PageKit.getPn(this), 10,
				"SELECT p.id,p.title,p.content,u.name,p.post_date,p.views ",
				"FROM post as p join user as u where p.user_id = u.id");
		setAttr("pageLink", PageKit.generateHTML("/admin/posts", page));
		setAttr("posts", page.getList());
		setAttr("tags", Tag.dao.getAll());
		setAttr("menu", "post");
		render("post.html");
	}

	public void addPost() {
		setAttr("categorys", Category.dao.getAllCategorys());
		setAttr("tags", Tag.dao.getAll());
		setAttr("menu", "post");
		render("add_post.html");
	}

	public void categorys() {
		setAttr("categorys", Category.dao.getAllCategorys());
		setAttr("menu", "category");
		render("category.html");
	}

	public void tags() {
		setAttr("tags", Tag.dao.getAll());
		setAttr("menu", "tag");
		render("tag.html");
	}

	public void comments() {
		setAttr("comments", Comment.dao.getAll());
		setAttr("menu", "comment");
		render("comment.html");
	}

	public void users() {
		setAttr("users", User.dao.getAll());
		setAttr("menu", "user");
		render("user.html");
	}

	public void savePost() {
		User sessionUser = getSessionAttr(WebKeys.SESSION_USER);
		Post post = getModel(Post.class);
		post.set("user_id", sessionUser.get("id"));
		post.set("post_date", new Date());
		System.out.println(post.toJson());
		post.save();
		String[] tags = getParaValues("tagId");
		if (tags != null) {
			for (String id : tags) {
				PostTag pt = new PostTag();
				pt.set("post_id", post.get("id"));
				pt.set("tag_id", id);
				pt.save();
			}
		}

		posts();
	}
}
