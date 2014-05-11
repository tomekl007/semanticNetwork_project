
$(window).load(function(){

	
	var form = document.getElementById("vcardForm"),
		name  ,
		nickname  ,
		data = {};
		
	form.onsubmit = function(e){
		e.preventDefault();
		name = document.getElementById("name").value;
		nickname = document.getElementById("nickname").value;
		data["fullName"] = name;
		data["nickName"] = nickname;
		console.log(data)
		var xhr = new XMLHttpRequest();
        console.log(form.method);
		xhr.open(form.method, form.action, true);
		xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
        xhr.setRequestHeader('Access-Control-Allow-Origin', '*')

        // send the collected data as JSON
		xhr.send(JSON.stringify(data));

		xhr.onloadend = function () {
			console.log(true);
		};

	}
});