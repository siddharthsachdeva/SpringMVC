$(document).ready(function(){
	$(".select-all-checkbox").change(function(){
		if(this.checked) {
			$('.is-checked').attr('checked', true);
	    }else{
	    	$('.is-checked').attr('checked', false);
	    }
		 
	});
	
});