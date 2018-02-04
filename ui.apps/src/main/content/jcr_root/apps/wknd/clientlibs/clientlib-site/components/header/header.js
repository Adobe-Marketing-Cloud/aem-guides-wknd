(function (site, $) {
    'use strict';
    var wkndheader = $(site + " .wknd-header"),
        scroll;
    
    if($(window).scrollTop() > 30) {
        wkndheader.addClass("navbar-sticky");
    }
    
    $(window).scroll(function(){
         
         scroll = $(window).scrollTop();
    if(scroll > 30) {
        wkndheader.addClass("navbar-sticky");
    } else {
        wkndheader.removeClass("navbar-sticky");
    }
});
}('.root',jQuery));