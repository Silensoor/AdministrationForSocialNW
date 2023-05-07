$(document).ready(function () {
    $('.open-popup').click(function (e) {
        document.querySelector('.popup-bg').style.display = "flex";
    });
    $('.close-popup').click(function () {
        document.querySelector('.popup-bg').style.display = "none";
    });
    $('.open-popup3').click(function (e) {
        document.querySelector('.popup-bg2').style.display = "flex";
    });
    $('.close-popup2').click(function () {
        document.querySelector('.popup-bg2').style.display = "none";
    });
    $('.open-popup5').click(function (e) {
        document.querySelector('.popup-bg3').style.display = "flex";
    });
    $('.close-popup3').click(function () {
        document.querySelector('.popup-bg3').style.display = "none";
    });
    $('.open-popup7').click(function (e) {
        document.querySelector('.popup-bg4').style.display = "flex";
    });
    $('.close-popup4').click(function () {
        document.querySelector('.popup-bg4').style.display = "none";
    });
    $('#form-poput3').submit(function (event) {
        event.preventDefault(); // Отменить стандартное поведение формы


        fetch('http://localhost:8086/api/v1/auth/captcha', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
            .then(data => {
                const code = data.code;
                // Создать объект для отправки POST запроса
                const postData = {
                    firstName: $('#form-poput3 input[name="firstName"]').val(),
                    lastName: $('#form-poput3 input[name="lastName"]').val(),
                    passwd1: $('#form-poput3 input[name="passwd"]').val(),
                    passwd2: $('#form-poput3 input[name="passwd"]').val(),
                    email: $('#form-poput3 input[name="email"]').val(),
                    code: code,
                    codeSecret: code
                };

                fetch('http://localhost:8086/api/v1/account/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(postData)
                })
                    .then(response => {
                        if (response.ok) {
                            location.reload();

                        } else {
                            console.log("POST запрос не отправлен")// Обработка ошибки
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    });
            });
    });

    $('.open-popup6').click(function () {

        fetch('http://localhost:8086/api/v1/auth/captcha', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
            .then(data => {
                const code = data.code;

                // Генерация случайных значений
                const firstName = getRandomString(8);
                const lastName = getRandomString(8);
                const password = getRandomNumber(8).toString();
                const email = `${firstName.toLowerCase()}.${lastName.toLowerCase()}@example.com`;

                // Создать объект для отправки POST запроса
                const postData = {
                    firstName: firstName,
                    lastName: lastName,
                    passwd1: password,
                    passwd2: password,
                    email: email,
                    code: code,
                    codeSecret: code
                };

                fetch('http://localhost:8086/api/v1/account/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(postData)
                })
                    .then(response => {
                        if (response.ok) {
                            console.log("OK")
                        } else {
                            console.log("POST запрос не отправлен")// Обработка ошибки
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    });
            });

        function getRandomString(length) {
            let result = '';
            const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
            const charactersLength = characters.length;
            for (let i = 0; i < length; i++) {
                result += characters.charAt(Math.floor(Math.random() * charactersLength));
            }
            return result;
        }

        function getRandomNumber(length) {
            return Math.floor(Math.pow(10, length - 1) + Math.random() * 9 * Math.pow(10, length - 1));
        }
    });


});
$('.open-popup4').click(function () {
    $.ajax({
        type: "POST",
        url: "/clear"

    })
});
$(document).on('click', '.delete-person', function (event) {
    event.preventDefault();
    var personId = $(this).data('id');
    // получаем значение th:value
    $.ajax({
        type: "POST",
        url: "/clear",
        data: {"id": personId},
    });
});
$(document).on('click', '.delete-person2', function (event) {
    event.preventDefault();
    var personId = $(this).data('id');
    // получаем значение th:value
    $.ajax({
        type: "POST",
        url: "/clear",
        data: {"id": personId},
    });
});

$('.open-popup2').click(function () {
    $.ajax({
        type: "POST",
        url: "/creates"

    })
});
const field0Text = document.getElementById('delete-person')
const field1 = document.getElementById('field1');
const field1Text = document.getElementById('field1-text');
const field2 = document.getElementById('field2');
const field2Text = document.getElementById('field2-text');
const field3 = document.getElementById('field3');
const field3Text = document.getElementById('field3-text');
const field4 = document.getElementById('field4');
const field4Text = document.getElementById('field4-text');
const field5 = document.getElementById('field5');
const field5Text = document.getElementById('field5-text');
const field6 = document.getElementById('field6');
const field6Text = document.getElementById('field6-text');
const field7 = document.getElementById('field7');
const field7Text = document.getElementById('field7-text');
const field8 = document.getElementById('field8');
const field8Text = document.getElementById('field8-text');


// добавляем обработчики событий на чекбоксы
field1.addEventListener('change', function () {
    field1Text.disabled = !this.checked;
});
field2.addEventListener('change', function () {
    field2Text.disabled = !this.checked;
});
field3.addEventListener('change', function () {
    field3Text.disabled = !this.checked;
});
field4.addEventListener('change', function () {
    field4Text.disabled = !this.checked;
});
field5.addEventListener('change', function () {
    field5Text.disabled = !this.checked;
});
field6.addEventListener('change', function () {
    field6Text.disabled = !this.checked;
});
field7.addEventListener('change', function () {
    field7Text.disabled = !this.checked;
});
field8.addEventListener('change', function () {
    field8Text.disabled = !this.checked;
});