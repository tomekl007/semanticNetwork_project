
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