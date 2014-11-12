var currentUrl = location.href;
$(function() {
	$("#user-menu li").click(function() {
		$("#user-menu li.active").removeClass("active");
		$(this).addClass("active");
		$("#user-index-title").html($(this).html());
	});
	// 为li标签赋予点击事件，令目标页面显示在id为col-content的div里面
	$("#user-menu").on(
			"click",
			"li",
			function(e) {
				var href = $(this).attr("href");
				if (!href || href.substr(0, 1) == "#"
						|| href.indexOf("javascript:") != -1) {
					return;
				}
				// bootstrap定义的组件忽略
				if ($(this).data("toggle") || $(this).attr("role")) {
					return;
				}
				e.preventDefault();
				$("#col-content").html("loading&nbsp;<i class='icon-spinner icon-spin icon-2x'></i>");
				$("#col-content").load($(this).attr("href"));
				currentUrl = href;
			});
	// 触发牌选中状态的菜单的click事件
	$("#user-menu li.active").trigger("click");
});
function refresh() {
	$("#col-content").html("loading&nbsp;<i class='icon-spinner icon-spin icon-2x'></i>");
	$("#col-content").load(currentUrl);
	$(".modal").modal("hide");
	$(".modal-backdrop").hide();
}
function showConfirm(config) {
	if (!config) {
		return;
	}
	if ((typeof config) == "string") {
		showConfirm({
			content : config
		});
		return;
	}
	config.type = "confirm";
	showModal(config);
}
function showModal(config) {
	if (!config.content) {
		return;
	}
	if (window._modal_id) {
		$("#modal-" + window._modal_id).remove();
		$(".modal-backdrop").hide();
	}
	if (!config.title) {
		config.title = "来自" + location.host + "的消息";
	}
	var id = (new Date()).getTime();
	var modal = document.createElement("div");
	var dialog = document.createElement("div");
	var content = document.createElement("div");
	var header = document.createElement("div");
	var body = document.createElement("div");
	var footer = document.createElement("div");
	$(modal).addClass("modal").addClass("fade").attr("id", "modal-" + id).attr(
			"role", "dialog").attr("aria-labelledby", "modal-title+" + id)
			.attr("aria-hidden", "true");
	$(dialog).addClass("modal-dialog");
	if ("sm" == config.size || "small" == config.size) {
		$(dialog).addClass("modal-sm");
	} else if ("lg" == config.size || "large" == config.size) {
		$(dialog).addClass("modal-lg");
	}
	$(modal).append(dialog);
	$(content).addClass("modal-content");
	$(dialog).append(content);
	$(header).addClass("modal-header");
	var title = document.createElement("h4");
	$(title).addClass("modal-title").attr("id", "modal-title+" + id).text(
			config.title);
	$(header).append(title);
	$(content).append(header);
	$(body).addClass("modal-body").html("<p>" + config.content + "<p>");
	$(content).append(body);
	$(footer).addClass("modal-footer");
	if ("confirm" == config.type) {
		var ensureBtn = document.createElement("button");
		var cancelBtn = document.createElement("button");
		$(ensureBtn).attr("type", "button").addClass("btn").addClass(
				"btn-primary").attr("data-dismiss", "modal");
		if (config.ensureText) {
			$(ensureBtn).text(config.ensureText);
		} else {
			$(ensureBtn).text("确 定").css("width", "100px");
		}
		$(ensureBtn).click(config.ensure);
		$(cancelBtn).attr("type", "button").addClass("btn").addClass(
				"btn-default").attr("data-dismiss", "modal");
		if (config.cancelText) {
			$(cancelBtn).text(config.cancelText);
		} else {
			$(cancelBtn).text("取 消").css("width", "100px");
		}
		$(cancelBtn).click(config.cancel);
		$(footer).append(cancelBtn).append(ensureBtn);
	} else {
		var btnWarp = document.createElement("div");
		var closeBtn = document.createElement("button");
		$(closeBtn).attr("type", "button").addClass("btn").addClass(
				"btn-primary").addClass("btn-block").attr("data-dismiss",
				"modal");
		if (config.btnText && (typeof config.btnText) == "string") {
			$(closeBtn).text(config.btnText);
			$(btnWarp).css("width", (config.btnText.length * 14 + 60) + "px");
		} else {
			$(btnWarp).css("width", "150px");
			$(closeBtn).text("确定");
		}
		$(closeBtn).click(config.after);
		$(btnWarp).addClass("container-fluid");
		$(btnWarp).append(closeBtn);
		$(footer).append(btnWarp);
	}
	$(content).append(footer);
	$(document.body).append(modal);
	$(modal).modal({
		backdrop : "static",
		keyboard : false
	});
	window._modal_id = id;
}
