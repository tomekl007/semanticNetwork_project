
$(window).load(function(){

	
	var form = document.getElementById("vcardForm"),
		name  ,
		nickname  ,
		data = {};
		
	form.onsubmit = function(e){
		e.preventDefault();
		name = $("#name").val();
		nickname = $("#nickname").val();
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
		xhr.onloadend = function (data) {
            var rdf = data.currentTarget.response;
            console.log(rdf)
            var div = $("#rdf");
            div.html(rdf);
        };

	}
});