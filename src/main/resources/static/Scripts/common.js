jQuery("input").blur(function(){
    alert('in');
    var validformat = /^\d{2}\/\d{2}\/\d{4}$/
    if (!validformat.test(this.val())) {
        alert('Enter date in dd/MM/yyy format please');
        this.val("");
        return false;
    }
});