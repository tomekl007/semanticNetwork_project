
$(window).load(function(){

	
	var form = document.getElementById("vcardForm"),
		name  ,
		nickname  ,
		data = {};
		
	form.onsubmit = function(e){
		e.preventDefault();
		name = document.getElementById("name").value;
		nickname = document.getElementById("nickname").value;
		data["name"] = name;
		data["nickname"] = nickname;
		console.log(data)
		var xhr = new XMLHttpRequest();
		xhr.open(form.method, form.action, true);
		xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

		// send the collected data as JSON
		xhr.send(JSON.stringify(data));

		xhr.onloadend = function () {
			console.log(true);
		};

	}
});