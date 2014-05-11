
function sendNewVCard() {
    var formData = JSON.stringify($("#vcardForm").serializeArray());
    alert(formData);
    $.ajax({
        type: "POST",
        url: "serverUrl",
        data: formData,
        success: function () {
        },
        dataType: "json",
        contentType: "application/json"
    });
}

function sendNewVCard2(){
    // construct an HTTP request
    var form = Document.getElementById("vcardForm");
    var xhr = new XMLHttpRequest();
    xhr.open(form.method, form.action, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

    // send the collected data as JSON
    xhr.send(JSON.stringify(form.data));

    xhr.onloadend = function () {
        // done
    };
}