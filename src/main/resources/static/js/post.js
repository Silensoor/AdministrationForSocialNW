$(document).on('click', '.delete-person', function (event) {
    event.preventDefault();
    var postId = $(this).data('id');
    // получаем значение th:value
    $.ajax({
        type: "POST",
        url: "/delete",
        data: {"id": postId},
    });
});
$(document).on('click', '.delete-person2', function (event) {
    event.preventDefault();
    var postId = $(this).data('id2');
    // получаем значение th:value
    $.ajax({
        type: "POST",
        url: "/deleteFind",
        data: {"id": postId},
    });
});