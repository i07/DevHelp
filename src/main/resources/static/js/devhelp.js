
Date.prototype.getUnixTime = function() { 
	return this.getTime()/1000|0 
};

var DevHelp = {
	
	TimeStampUpdate: null,
	TimeStampFocus: false,
	DateTimeUpdate: null,
	DateTimeFocus: false,

	Init: function() {
		
    	DevHelp.SetDates();
    	DevHelp.StartTimestampUpdate();
		//this.loadPage('pages/index');
	},

	SetDates: function() {

    	var now = new Date();
    
	    var strDate = [now.getFullYear(), AddZero(now.getMonth() + 1), AddZero(now.getDate())].join("-");
	        
	    var strTime = [AddZero(now.getHours()), AddZero(now.getMinutes()), AddZero(now.getSeconds())].join(":");
	   	
	   	$('#current_date').val(strDate + " " + strTime);
	    $('#timestamp').val(now.getUnixTime());

	},

	StartTimestampUpdate: function() {

		DevHelp.TimeStampUpdate = setInterval(function() {

			if(!DevHelp.TimeStampFocus) {
				var now = new Date();
				$('#timestamp').val(now.getUnixTime());
			}

			if (!DevHelp.DateTimeFocus) {
				var now = new Date();
    
	   			var strDate = [now.getFullYear(), AddZero(now.getMonth() + 1), AddZero(now.getDate())].join("-");
	    		var strTime = [AddZero(now.getHours()), AddZero(now.getMinutes()), AddZero(now.getSeconds())].join(":");
	   	
	   			$('#current_date').val(strDate + " " + strTime);
			}
		}, 1000);
	},

	TimestampFocus: function() {
		DevHelp.TimeStampFocus = true;
		
		$('#timestamp').select();
		var successful = document.execCommand('copy');

        if(successful) {
        	$('#timestamp').blur();
        	$('#timestamp_copied').fadeOut(1);
        	$('#timestamp_copied').html('Timestamp<br/>Copied!');
        	$('#timestamp_copied').fadeIn(500, function() {
        		setTimeout(function() {
        			$('#timestamp_copied').fadeOut(500);
        		}, 1000);
        	})
        }
	},
	
	TimestampBlur: function() {
		DevHelp.TimeStampFocus = false;
		//$('#timestamp').blur();
	},

	DatetimeFocus: function() {
		DevHelp.DateTimeFocus = true;
		$('#current_date').select();
		var successful = document.execCommand('copy');
		if(successful) {
        	$('#current_date').blur();
        	$('#timestamp_copied').fadeOut(1);
        	$('#timestamp_copied').html('DateTime<br/>Copied!')
        	$('#timestamp_copied').fadeIn(500, function() {
        		setTimeout(function() {
        			$('#timestamp_copied').fadeOut(500);
        		}, 1000);
        	})
        }
	},

	DatetimeBlur: function() {
		DevHelp.DateTimeFocus = false;
	},

	LoadPage: function(page) {
		
		$("#gui").html('');
		$.ajax({
			type: "GET",
			beforeSend: function(request) {
				request.setRequestHeader("DevHelp", "true");
			},
			url: page,
			processData: false,
			success: function(msg) {
				$("#gui").html(msg);
			}
		});
	}
}