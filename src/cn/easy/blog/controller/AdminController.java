/**
 * Copyright (c) 2011-2014, yycoder 692895299@qq.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

	/**
	 * 登录界面
	 */
	@ClearInterceptor(ClearLayer.ALL)
	public void index() {
		if (getSessionAttr(WebKeys.SESSION_USER) != null) {
			posts();
		} else {
			render("login.html");
		}
	}

	/**
	 * 登录验证
	 */
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

	/**
	 * 登出
	 */
	@ClearInterceptor(ClearLayer.ALL)
	public void logout() {
		getSession().invalidate();
		removeCookie(WebKeys.COOKIE_USER_ID);
		index();
	}

	/**
	 * 博文页面
	 */
	public void posts() {
		setAttr("categorys", Category.dao.getAllCategorys());
		Page<Post> page = Post.dao
				.paginate(
						PageKit.getPn(this),
						10,
						"SELECT p.id,p.title,p.content,c.name as cate_name,u.name,p.post_date,p.views ",
						"FROM post as p join user as u join category as c where p.user_id = u.id and "
								+ "c.id = p.category_id order by p.id asc");
		setAttr("pageLink", PageKit.generateHTML("/admin/posts", page));
		setAttr("posts", page.getList());
		setAttr("tags", Tag.dao.getAll());
		setAttr("menu", "post");
		render("post.html");
	}

	/**
	 *添加博文重定向
	 */
	public void addPost() {
		setAttr("categorys", Category.dao.getAllCategorys());
		setAttr("tags", Tag.dao.getAll());
		setAttr("menu", "post");
		render("add_post.html");
	}

	/**
	 * 分类页面
	 */
	public void categorys() {
		setAttr("categorys", Category.dao.getAllCategorys());
		setAttr("menu", "category");
		render("category.html");
	}

	/**
	 * 标签页面
	 */
	public void tags() {
		setAttr("tags", Tag.dao.getAll());
		setAttr("menu", "tag");
		render("tag.html");
	}

	/**
	 * 评论
	 */
	public void comments() {
		setAttr("comments", Comment.dao.getAll());
		setAttr("menu", "comment");
		render("comment.html");
	}

	/**
	 * 用户界面
	 */
	public void users() {
		setAttr("users", User.dao.getAll());
		setAttr("menu", "user");
		render("user.html");
	}

	/**
	 * 保存博文
	 */
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

	/**
	 * 删除博文
	 */
	@Before(Tx.class)
	public void deletePost() {
		String postId = getPara();
		PostTag.dao.delByPostId(postId);
		Comment.dao.delByPostId(postId);
		Post.dao.deleteById(postId);
		posts();
	}

	/**
	 * 更新博文重定向
	 */
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

	/**
	 * 跟新博文
	 */
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
		redirect("/admin/showPost/" + postId);
	}

	/**
	 * 添加标签重定向
	 */
	public void addTag() {
		setAttr("categorys", Category.dao.getAllCategorys());
		setAttr("tags", Tag.dao.getAll());
		setAttr("menu", "tag");
		render("add_tag.html");
	}

	/**
	 * 更新标签重定向
	 */
	public void showTag() {
		setAttr("categorys", Category.dao.getAllCategorys());
		setAttr("tags", Tag.dao.getAll());
		setAttr("menu", "tag");
		setAttr("tag", Tag.dao.findById(getPara()));
		render("edit_tag.html");
	}

	/**
	 * 更新标签
	 */
	public void updateTag() {
		String tagId = getPara();
		Tag tag = getModel(Tag.class);
		tag.set("id", tagId);
		tag.update();
		tags();
	}

	/**
	 * 保存标签
	 */
	public void saveTag() {
		Tag tag = getModel(Tag.class);
		tag.save();
		tags();
	}

	/**
	 * 添加分类action
	 */
	public void addCategory() {
		setAttr("categorys", Category.dao.getAllCategorys());
		setAttr("tags", Tag.dao.getAll());
		setAttr("menu", "category");
		render("add_category.html");
	}
	
	/**
	 * 保存分类
	 */
	public void saveCategory() {
		Category category = getModel(Category.class);
		category.save();
		categorys();
	}
}
