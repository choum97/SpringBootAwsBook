var main = { //index.js만의 유효범위(scope)를 만들어 사용하는 것, 나중에 로딩된 함수를 덮어쓰게 되기 때문임
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();