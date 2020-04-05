var stompClient = null;

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
        stompClient.connect({}, function (frame) {
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
});