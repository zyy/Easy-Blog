package cn.easy.blog.controller;

import java.util.List;

import cn.easy.blog.model.Category;
import cn.easy.blog.model.Post;
import cn.easy.blog.model.PostTag;
import cn.easy.blog.model.Tag;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

@ClearInterceptor(ClearLayer.ALL)
public class TagController extends Controller {
	public void index() {
		StringBuilder sql = new StringBuilder("SELECT p.id,p.title,p.content,u.name,p.post_date,p.views "
						+ "FROM post as p join user as u where p.user_id = u.id and p.id in (");
		List<PostTag> list = PostTag.dao.getPostIdByTagId(getPara());
		for (int i = 0; i < list.size(); i++) {
			if (i != 0)
				sql.append(",");
			sql.append(list.get(i).getInt("post_id"));
		}
		sql.append(")");
		List<Record> posts = Db.find(sql.toString());
		for (Record post : posts) {
			System.out.println(Post.dao.getTagsHtml(post.getInt("id")));
			post.set("tags", Post.dao.getTagsHtml(post.getInt("id")));
			System.out.println(">>>>>>>>>>>" + post.toJson());
		}
		setAttr("posts", posts);
		setAttr("tags", Tag.dao.getAll());
		setAttr("categorys", Category.dao.getAllCategorys());
		render("../index/index.html");
	}
}
