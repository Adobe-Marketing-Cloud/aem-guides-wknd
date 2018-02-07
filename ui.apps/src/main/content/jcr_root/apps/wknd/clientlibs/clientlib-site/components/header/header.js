(function (site, $) {
    'use strict';
    var wkndheader = $(site + " .wknd-header"),
        scroll,
        mobileBreakpoint = 992;
    
    if($(window).scrollTop() > 0 && $(window).width() > mobileBreakpoint) {
        wkndheader.addClass("navbar-sticky");
    }
    
    $(window).scroll(function(){
         
         scroll = $(window).scrollTop();
    if(scroll > 0 && $(window).width() > mobileBreakpoint) {
        wkndheader.addClass("navbar-sticky");
    } else {
        wkndheader.removeClass("navbar-sticky");
    }
});
}('.root',jQuery));