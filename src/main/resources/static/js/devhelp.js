
var devhelp = {
	
	init: function() {
		//$('body').css('background-color', '#f00');
		this.loadPage('pages/index');
	},
	
	loadPage: function(page) {
		
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