package cn.easy.blog.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.jfinal.plugin.activerecord.tx.Tx;

public class AdminController extends Controller {

	@ClearInterceptor(ClearLayer.ALL)
	public void index() {
		if (getSessionAttr(WebKeys.SESSION_USER) != null) {
			posts();
		} else {
			render("login.html");
		}
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
		session.setAttribute(WebKeys.SESSION_USER, user);
		Object referer = session.getAttribute(WebKeys.SESSION_REFERER);
		if (referer != null) {
			session.removeAttribute(WebKeys.SESSION_REFERER);
			redirect(String.valueOf(referer));
			return;
		}
		posts();
	}

	@ClearInterceptor(ClearLayer.ALL)
	public void logout() {
		getSession().invalidate();
		removeCookie(WebKeys.COOKIE_USER_ID);
		index();
	}

	public void posts() {
		setAttr("categorys", Category.dao.getAllCategorys());
		Page<Post> page = Post.dao.paginate(PageKit.getPn(this), 10,
				"SELECT p.id,p.title,p.content,c.name as cate_name,u.name,p.post_date,p.views ",
				"FROM post as p join user as u join category as c where p.user_id = u.id and "
				+ "c.id = p.category_id order by p.id asc");
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

	@Before(Tx.class)
	public void savePost() {
		User sessionUser = getSessionAttr(WebKeys.SESSION_USER);
		Post post = getModel(Post.class);
		post.set("user_id", sessionUser.get("id"));
		post.set("post_date", new Date());
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

	@Before(Tx.class)
	public void deletePost() {
		String postId = getPara();
		PostTag.dao.delByPostId(postId);
		Comment.dao.delByPostId(postId);
		Post.dao.deleteById(postId);
		posts();
	}

	public void showPost() {
		String postId = getPara();
		setAttr("post", Post.dao.findById(postId));
		List<String> tagIds = new ArrayList<String>();
		for (PostTag pt : PostTag.dao.getPostTagsByPostId(postId)) {
			tagIds.add(String.valueOf(pt.getInt("tag_id")));
		}
		setAttr("tagIds", tagIds);
		System.out.println();
		setAttr("categorys", Category.dao.getAllCategorys());
		setAttr("tags", Tag.dao.getAll());
		setAttr("menu", "post");
		render("edit_post.html");
	}
	
	@Before(Tx.class)
	public void updatePost() {
		String postId = getPara();
		Post post = getModel(Post.class);
		post.set("id", postId);
		
		// 删除标签关联
		PostTag.dao.delByPostId(postId);
		
		// 保存新的标签关联
		String[] tags = getParaValues("tagId");
		if (tags != null) {
			for (String id : tags) {
				PostTag pt = new PostTag();
				pt.set("post_id", post.get("id"));
				pt.set("tag_id", id);
				pt.save();
			}
		}
		
		// 更新标题，内容，分类
		post.update();
		setAttr("msg", "修改成功!");
		redirect("/admin/showPost/" + postId);;
	}
	
	public void addTag() {
		setAttr("categorys", Category.dao.getAllCategorys());
		setAttr("tags", Tag.dao.getAll());
		setAttr("menu", "tag");
		render("add_tag.html");
	}
	
	public void saveTag() {
		Tag tag = getModel(Tag.class);
		tag.save();
		tags();
	}
}
