$(document).ready(function () {
    //$('#ContentPlaceHolder1_btnSearch').trigger('click');
    $('#ContentPlaceHolder1_btnsubmit').click(function () {
        $("#commentForm").validate();
        //alert($("#commentForm").find('input[type="text"]').val());
        //if ($("#commentForm").find('input[type="text"]').val() == $("#commentForm").find('input[type="text"]').attr('placeholder'))
        //{
        //    alert($("#commentForm").find('input[type="text"]').attr('placeholder'));
        //    return false;
        //}
    })   
})
function Count(text, long) {
    var maxlength = new Number(long); // Change number to your max length.
    if (text.value.length > maxlength) {
        text.value = text.value.substring(0, maxlength);
        alert(" Only " + long + " characters");
    }
}
$(window).bind("pageshow", function () {
    var form = $('form');
    //form[0].reset();
});