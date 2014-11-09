// Tabs
jQuery(document).ready(function($){
	$('.stabs a').click(function(){
		switch_tabs($(this));
	});
	switch_tabs($('.defaulttab'));
	function switch_tabs(obj) {
		$('.stab-content').hide();
		$('.stabs a').removeClass("selected");
		var id = obj.attr("rel");
		$('#'+id).show();
		obj.addClass("selected");
	}
});

jQuery(document).ready(function(){
	jQuery('.inside ul li:last-child').css('border-bottom','0px'); // remove last border-bottom from list in tab conten
	jQuery('.tabs').each(function(){
		jQuery(this).children('li').children('a:first').addClass('selected'); // Add .selected class to first tab on load
	});
	jQuery('.inside > *').hide();
	jQuery('.inside > *:first-child').show();
   
	jQuery('.tabs li a').click(function(evt){ // Init Click funtion on Tabs
        var clicked_tab_ref = jQuery(this).attr('href'); // Strore Href value
        jQuery(this).parent().parent().children('li').children('a').removeClass('selected'); //Remove selected from all tabs
        jQuery(this).addClass('selected');
        jQuery(this).parent().parent().parent().children('.inside').children('*').hide();
        jQuery('.inside ' + clicked_tab_ref).fadeIn(500);
        evt.preventDefault();
	});
});

// Scroll to Top script
var $backToTopTxt = "返回顶部", $backToTopEle = $(
		'<div class="backToTop"></div>').appendTo($("body")).text(
		$backToTopTxt).attr("title", $backToTopTxt).click(function() {
	$("html, body").animate({
		scrollTop : 0
	}, 120);
}), $backToTopFun = function() {
	var st = $(document).scrollTop(), winh = $(window).height();
	(st > 0) ? $backToTopEle.show() : $backToTopEle.hide();
	if (!window.XMLHttpRequest) {
		$backToTopEle.css("top", st + winh - 166);
	}
};
$(window).bind("scroll", $backToTopFun);
$(function() {
	$backToTopFun();
});
