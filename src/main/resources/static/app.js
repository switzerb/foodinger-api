var csrfToken = null;

$(function () {
    $("#main-content form").on('submit', function (e) {
        e.preventDefault();
    });
    $.ajax("/api/user-info")
        .then(function(data, status, jqXHR) {
            $("#hello").text(data.name);
            $("#name").val(data.name);
            csrfToken = jqXHR.getResponseHeader("X-CSRF-TOKEN")
            $("#welcome-bar")
                .show()
                .find("input[name=_csrf]")
                    .val(csrfToken);
            $("#main-content").show();
        }, function() {
            $.ajax("/login-providers")
                .then(function(data) {
                    var $list = $("#login-provider-list").empty();
                    if (data.length === 0) {
                        $list.append('<li>... there are no providers configured?</li>');
                    }
                    var hasNewUser = false;
                    for (var i = 0; i < data.length; i++) {
                        var r = data[i];
                        var n;
                        switch (r.type) {
                            case "oauth":
                                n = 'via <a href="/oauth2/authorization/' + r.id + '">your ' + r.name + ' account</a>';
                                break;
                            case "local-user":
                                n = '<form action="/mock-user/login?password=garbage" method="post"><input type="hidden" name="username" value="' + r.id + '" />as <button class="btn-link">' + r.name + '</button></form>';
                                break;
                            case "new-local-user":
                                hasNewUser = true;
                                continue;
                            default:
                                console.warn("Unknown '%s' provider type", r.type, r);
                                continue;
                        }
                        $list.append($("<li></li>").append(n));
                    }
                    if (hasNewUser) {
                        // noinspection HtmlUnknownTarget
                        $list.after('<form action="/mock-user" method="post">New mock user: <input name="username" placeholder="Username" /> <button>Create</button></form>');
                    }
                    $("#splash").show();
                })
        });
});