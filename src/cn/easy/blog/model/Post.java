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
package cn.easy.blog.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class Post extends Model<Post> {

	private static final long serialVersionUID = -1092937447324107462L;
	public static final Post dao = new Post();

	public List<Post> getNewest() {
		String sql = "select * from post limit 10";
		return Post.dao.find(sql);
	}

	public List<Record> getIndexPosts() {
		List<Record> posts = Db
				.find("SELECT p.id,p.title,p.content,u.name,p.post_date,p.views "
						+ "FROM post as p join user as u where p.user_id = u.id limit 10");
		for (Record post : posts) {
			System.out.println(getTagsHtml(post.getInt("id")));
			post.set("tags", getTagsHtml(post.getInt("id")));
			System.out.println(">>>>>>>>>>>" + post.toJson());
		}
		return posts;
	}
	
	public String getTagsHtml(Integer id) {
		StringBuilder html = new StringBuilder();
        List<Tag> list = Tag.dao.find("select * from tag where id in("
        		+ "select distinct(tag_id) from blog.posttag where post_id=" + id + ")");
        if(null != list && list.size() > 0) {
            html.append("<span class=\"mt_icon\"></span>");
        }
        String temp = "<a title=\"{}\" href=\"/tag/id\">{}</a>";
        for (int i = 0; i < list.size(); i++) {
        	Tag tag = list.get(i);
        	String temp1 = temp.replace("id", String.valueOf(tag.getInt("id")));
            html.append(temp1.replace("{}", tag.getStr("name")));
            if (i != list.size() - 1 ) {
                html.append(',');
            }
        }
        return html.toString();
	}

}
