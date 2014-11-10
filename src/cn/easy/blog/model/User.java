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

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User> {

	private static final long serialVersionUID = 7927107897061864964L;
	public static final User dao = new User();

	public User getByAccount(String account) {
		return this.findFirst("select * from user where phone=? or email=? ",
				account, account);
	}
	
	public List<User> getAll() {
		return User.dao.find("select * from user");
	}
}
