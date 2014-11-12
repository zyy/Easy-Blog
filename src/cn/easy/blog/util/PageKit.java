package cn.easy.blog.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * 分页工具，获取页码参数，生成分页html
 * 
 * @version 2014.07.11 修改分页链接生成逻辑，使其理更加简洁
 * 
 */
public class PageKit {
	/**
	 * 生成分页的html代码，在freemarker定义宏实现过于麻烦，所以项目采用这种方式，用java生成。
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String generateHTML(String url, Page page) {
		StringBuilder html = new StringBuilder();
		int pn = page.getPageNumber();// 当前页码
		int tp = page.getTotalPage();// 总页数
		// 如果一共只有一页，不用生成链接了
		if (tp <= 1) {
			return "";
		}
		url = url + "?pn=";
		// 起始页与结束页
		int ps = pn - 3;
		if (ps < 1) {
			ps = 1;
		}
		int pe = ps + 6;
		// 计算开始页与结束页
		if (pe > tp) {
			pe = tp;
		}
		if (pe == tp) {
			ps = pe - 6;
		}
		if (ps < 1) {
			ps = 1;
		}
		// 拼接ul开始标签
		html.append("<ul class='pager'>");
		// 首页
		if (ps > 1) {
			html.append("<li><a href='" + url + "1'>1</a></li>");
		}
		// 省略号
		if (ps > 2) {
			html.append("<li>...</li>");
		}
		// 生成从ps到pe的分页链接
		for (int i = ps; i <= pe; i++) {
			if (pn == i) {
				html.append("<li class='active'><a>" + i + "</a></li>");
			} else {
				html.append("<li><a href='" + url + i + "'>" + i + "</a></li>");
			}
		}
		// 省略号
		if (tp - pe > 1) {
			html.append("<li>...</li>");
		}
		// 最后一页
		if (pe < tp) {
			html.append("<li><a href='" + url + tp + "'>" + tp + "</a></li>");
		}
		// 拼接ul结束标签
		html.append("</ul>");

		return html.toString();
	}

	/**
	 * 获取页码
	 * 
	 * @param controller
	 * @return
	 */
	public static int getPn(Controller controller) {
		// 页码
		int pn = 1;
		if (RegExpUtil.checkPositiveInteger(controller.getPara("pn"))) {
			pn = Integer.parseInt(controller.getPara("pn"));
		}
		return pn;
	}
}
