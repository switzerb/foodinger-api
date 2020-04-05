var stompClient = null;
var csrfToken = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    showMessage(connected ? "CONNECT" : "DISCONNECT");
}

function connect() {
    return new Promise(resolve => {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.debug = null;
        stompClient.connect({
            "X-CSRF-TOKEN": csrfToken
        }, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/greetings', function (greeting) {
                showMessage(JSON.parse(greeting.body).content);
            });
            resolve();
        }, function(frame) {
            if (typeof frame === "string") {
                // not an actual error, but rather stompClient saying "whoops"
                setConnected(false);
                console.log("Disconnected: " + frame)
            } else {
                console.log("ERROR", frame)
            }
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    if (stompClient == null || !stompClient.connected) {
        connect().then(sendName);
        return;
    }
    stompClient.send("/app/join", {}, JSON.stringify({'name': $("#name").val()}));
}

function showMessage(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("#main-content form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $.ajax("/user-info")
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
                    for (var i = 0; i < data.length; i++) {
                        var r = data[i];
                        var n;
                        switch (r.type) {
                            case "oauth":
                                n = '<a href="/oauth2/authorization/' + r.id + '">via ' + r.name + '</a>';
                                break;
                            case "local-user":
                                n = '<a href="/mock/local-user/' + r.id + '">as ' + r.name + '</a>';
                                break;
                            case "new-local-user":
                                // noinspection HtmlUnknownTarget
                                n = '<form action="/mock/local-user" method="post"><input name="name" placeholder="New User\'s Name" /> <button>Create</button></form>';
                                break
                            default:
                                console.warn("Unknown '%s' provider type", r.type, r);
                                continue;
                        }
                        $list.append($("<li></li>").append(n));
                    }
                    $("#splash").show();
                })
        });
});