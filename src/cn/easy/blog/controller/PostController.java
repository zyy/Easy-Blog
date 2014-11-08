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

import cn.easy.blog.model.Category;
import cn.easy.blog.model.Post;
import cn.easy.blog.model.Tag;

import com.jfinal.core.Controller;

public class PostController extends Controller {

	public void postInfo() {
		Post post = Post.dao.findById(getPara());
		post.set("views", (Integer)post.get("views") + 1);
		post.update();
		setAttr("post", post);
		System.out.println(Post.dao.findById(getPara()).toJson());
		setAttr("tags", Tag.dao.getAll());
		setAttr("categorys", Category.dao.getAllCategorys());
		Post.dao.getIndexPosts();
		render("post.html");
	}

	public void getPostsByCategory() {
		setAttr("posts", Post.dao.find("select * from post where category_id = " + getPara()));
		setAttr("tags", Tag.dao.getAll());
		setAttr("categorys", Category.dao.getAllCategorys());
		render("../index/index.html");
	}
}
