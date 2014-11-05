package cn.easy.blog.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.server.Authentication.User;

import cn.easy.aop.Interceptor;
import cn.easy.blog.common.WebKeys;
import cn.easy.core.ActionInvocation;
import cn.easy.core.Controller;
import cn.easy.util.StringUtil;

public class LoginInterceptor implements Interceptor {

	@Override
	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		HttpServletRequest request = ai.getController().getRequest();
		/**
		 * 对于session里没有用户对象的进行拦截
		 */
		if (controller.getSessionAttr(WebKeys.SESSION_USER) != null) {
			ai.invoke();
			return;
		}
		// 尝试从cookie里找userid
		String userId = controller.getCookie(WebKeys.COOKIE_USER_ID);
		User user = null;
		if (StringUtil.isEmpty(userId)) {
			//user = User.dao.findById(userId);
		}
		if (user != null) {
			controller.setSessionAttr(WebKeys.SESSION_USER, user);
			ai.invoke();
			return;
		}
		/**
		 * 如果是post请求或者异步请求保存来源页面地址
		 */
		if (request.getHeader("x-requested-with") != null) {
			controller.setSessionAttr(WebKeys.SESSION_REFERER,
					request.getHeader("Referer"));
			ai.getController().getResponse().setHeader("login", "unLogin");
			ai.getController().getResponse().setStatus(401);
			return;
		} else if ("POST".equals(request.getMethod())) {
			controller.setSessionAttr(WebKeys.SESSION_REFERER,
					request.getHeader("Referer"));
		} else {// 非异步的get请求保存请求地址
			String queryString = StringUtil.isEmpty(request.getQueryString()) ? ""
					: "?" + request.getQueryString();
			controller.setSessionAttr(WebKeys.SESSION_REFERER,
					request.getRequestURL() + queryString);
		}
		ai.getController().redirect("/");
		return;

	}

}
