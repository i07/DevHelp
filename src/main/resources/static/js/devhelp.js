
Date.prototype.getUnixTime = function() { 
	return this.getTime()/1000|0 
};

var DevHelp = {
	
	TimeStampUpdate: null,
	TimeStampFocus: false,
	DateTimeUpdate: null,
	DateTimeFocus: false,
	B64Type: 'encode',

	Init: function() {
		
    	DevHelp.SetDates();
    	DevHelp.StartTimestampUpdate();
    	
    	var ct = new Date().getTimezoneOffset();
    	ct = ct / -60;
    	if (ct>0) ct="+"+ct;
    	$("#client_timezone").html(ct);
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
	
	CopyToClipboard: function(element) {
		if ($('#' + element + ' ~ i').hasClass("text-lighten-1")) {
			$('#' + element).select();
			var successful = document.execCommand('copy');
			if (successful) {
				$('#'+element).blur();
				$('#time_select').focus().blur();
				DevHelp.ShowNotice($('#' + element).val() + "<br/>sent to clipboard.");
			}
		}
	},
	
	ShowNotice: function(message) {
		$('#timestamp_copied').fadeOut(1);
    	$('#timestamp_copied').html(message);
    	$('#timestamp_copied').fadeIn(1000, function() {
    		setTimeout(function() {
    			$('#timestamp_copied').fadeOut(1000);
    		}, 1000);
    	})
	},
	
	TimestampToDate: function() {
		var timestamp = $('#calc_timestamp').val();
		if (timestamp == "") {
			$('#calc_timestamp ~ i').removeClass('text-lighten-1').addClass('text-lighten-3');
			
			$('#date_select').val('');
			$('#date_select ~ i').removeClass('text-lighten-1').addClass('text-lighten-3');
			
			$('#time_select').val('');
		} else {
			
			$('#calc_timestamp ~ i').removeClass('text-lighten-3').addClass('text-lighten-1');
			var date = new Date(timestamp*1000);

			$('#date_select').val([date.getFullYear(), AddZero(date.getUTCMonth() + 1), AddZero(date.getUTCDate())].join("-"));
			$('#date_select ~ i').removeClass('text-lighten-3').addClass('text-lighten-1');
			
			$('#time_select').val([AddZero(date.getUTCHours()), AddZero(date.getUTCMinutes()), AddZero(date.getUTCSeconds())].join(":"));
		}
	},
	
	DateTimeToTimestamp: function() {
		var dateStr = $('#date_select').val();
		var timeStr = $('#time_select').val();
		console.log(timeStr);
		if (dateStr == "") {
			var date = new Date();
			dateStr = [date.getFullYear(), AddZero(date.getMonth() + 1), AddZero(date.getDate())].join("-");
			var chkdate = new Date(dateStr + " " + timeStr);
			if (chkdate.getUnixTime() != 0) {
				$('#date_select').val(dateStr);
				$('#date_select ~ i').removeClass('text-lighten-3').addClass('text-lighten-1');
			}
		}
		var date = new Date(dateStr + " " + timeStr);
		if (date.getUnixTime() != 0) {
			$('#calc_timestamp').val(date.getUnixTime());
			$('#calc_timestamp ~ i').removeClass('text-lighten-3').addClass('text-lighten-1');
		} else {
			$('#calc_timestamp').val('');
			$('#calc_timestamp ~ i').removeClass('text-lighten-1').addClass('text-lighten-3');
		}
	},
	
	B64switch: function(wh) {
		DevHelp.B64Type = wh;
		$('#base64dd_title').html(wh);
		if (wh=="encode") {
			$('#base64_text').attr('placeholder', 'type text to base64 encode');
			$('#base64_result').attr('placeholder', 'encoded result will appear here');
		} else {
			$('#base64_text').attr('placeholder', "type text to base64 decode");
			$('#base64_result').attr('placeholder', 'decoded result will appear here');
		}
	},
	
	DoBase64: function() {
		if (DevHelp.B64Type=="encode") {
			$('#base64_result').val(btoa($('#base64_text').val()));
		} else {
			$('#base64_result').val(atob($('#base64_text').val()));
		}
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