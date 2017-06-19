
var devhelp = {
	
	init: function() {
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