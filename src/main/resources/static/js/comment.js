$(document).on('click', '.delete-comment', function (event) {
    event.preventDefault();
    var commentId = $(this).data('id');
    // получаем значение th:value
    $.ajax({
        type: "POST",
        url: "/comment",
        data: {"id": commentId},
    });
});
$(document).on('click', '.delete-comment2', function (event) {
    event.preventDefault();
    var commentId = $(this).data('id2');
    // получаем значение th:value
    $.ajax({
        type: "POST",
        url: "/commentFind",
        data: {"id": commentId},
    });
});