package cn.easy.blog.validator;

import cn.easy.blog.common.RegExp;
import cn.easy.blog.util.RegExpUtil;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class LoginValidator extends Validator {

	@Override
	protected void handleError(Controller controller) {
		controller.render("login.html");
	}

	@Override
	protected void validate(Controller controller) {
		// 验证帐号
		String account = controller.getPara("account");
		System.out.println(">>>>>>>>>>>> account " + account);
		if (!RegExpUtil.checkMobile(account) && !RegExpUtil.checkMail(account)) {
			addError("msg", "请输入格式正确的手机号或者邮箱");
			return;
		}
		// 验证密码
		validateRegex("pwd", RegExp.PASSWORD, "msg", "密码不规范");
	}

}
