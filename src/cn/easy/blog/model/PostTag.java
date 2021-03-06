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

public class PostTag extends Model<PostTag> {

	private static final long serialVersionUID = -159491196000805346L;
	public static final PostTag dao = new PostTag();
	
	public List<PostTag> getPostIdByTagId(String tagId){
		return PostTag.dao.find("select distinct(post_id) from posttag where tag_id = " + tagId);
	}
	
	public void delByPostId(String postId) {
		Db.update("delete from posttag where post_id = " + postId);
	}
	
	public List<PostTag> getPostTagsByPostId(String postId) {
		return PostTag.dao.find("select * from posttag where post_id = " + postId);
	}
}
