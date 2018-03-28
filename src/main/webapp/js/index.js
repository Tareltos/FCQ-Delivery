$("#tabs").on("click", "a", function () {
    var me = $(this);

    $(".active").removeClass("active");
    me.addClass("active");
    $("#" + me.attr("id").replace("tab", "container")).addClass("active");
    return false;
});
$(function () {
    //script for popups
    $('a.edit').click(function () {
        $('div.' + $(this).attr("rel")).fadeIn(500);
        $("body").append("<div id='overlay'></div>");
        $('#overlay').show().css({'filter': 'alpha(opacity=80)'});
        return false;
    });
    $('a.close').click(function () {
        $(this).parent().fadeOut(100);
        $('#overlay').remove('#overlay');
        return false;
    });
});