package cn.easy.blog.controller;

import java.util.List;

import cn.easy.blog.model.Category;
import cn.easy.blog.model.Post;
import cn.easy.blog.model.PostTag;
import cn.easy.blog.model.Tag;

import com.jfinal.core.Controller;

public class TagController extends Controller {
	public void index() {
		StringBuilder sql = new StringBuilder("select * from post where id in (");
		List<PostTag> list = PostTag.dao.getPostIdByTagId(getPara());
		for (int i = 0; i < list.size(); i++) {
			if (i != 0)
				sql.append(",");
			sql.append(list.get(i).getInt("post_id"));
		}
		sql.append(")");
		setAttr("posts",Post.dao.find(sql.toString()));
		setAttr("tags", Tag.dao.getAll());
		setAttr("categorys", Category.dao.getAllCategorys());
		render("../index/index.html");
	}
}
