$(document).ready(function(){

    $('#btnUndoNodeDeletions').on('click',function(){
        $(this).toggleClass("active btn-danger");
        if($(this).hasClass("active")){

            $(this).html("Stop Refreshing")
        }else{
            $(this).html("Start Refreshing")
        }
    });
});

