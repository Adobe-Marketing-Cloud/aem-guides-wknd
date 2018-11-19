// Wrap bindings in anonymous namespace to prevent collisions
jQuery(function($) {
    "use strict";

 function applyComponentStyles() {

  //Top Level Navigation (expected to only be one of these)
  $("#header-navbar .cmp-navigation").not("[data-top-nav-processed='true']").each(function() {
            // Mark the component element as processed to avoid the cyclic processing (see .not(..) above).
            var nav = $(this).attr("data-top-nav-processed", true),
                $body = $('body');
            
            // Toggle Nav
            $('<div id="toggleNav">' +
                 '<a href="#mobileNav" class="toggle"><i class="wkndicon wkndicon-ico-bm" aria-hidden="true"></i></a>' +
                '</div>'
            ).appendTo($body);
            
         // Navigation Panel.
            $(
                '<div id="mobileNav" class="cmp-navigation--mobile">' +
                    '<nav class="cmp-navigation">' +
                        $(this).html() +
                    '</nav>' +
                '</div>'
            )
                .appendTo($body)
                .panel({
                    delay: 500,
                    hideOnClick: true,
                    hideOnSwipe: true,
                    resetScroll: true,
                    resetForms: true,
                    side: 'left',
                    target: $body,
                    visibleClass: 'navPanel-visible'
                });
        });
    }

  applyComponentStyles();
  
  $(".responsivegrid").bind("DOMNodeInserted", applyComponentStyles);
});