
function ServerApi() {

}

ServerApi.onErrorGlobal = function(msg) {
    // default handler
    console.log('error: ' + msg);
};

ServerApi.onSuccessGlobal = function(msg) {
    // default handler
};

ServerApi.post = function(url, request, onSuccess) {
    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(request),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            if (response.hasOwnProperty('exception') && response.exception !== null) {
                ServerApi.onErrorGlobal(response.exception);
            } else {
                ServerApi.onSuccessGlobal();
                onSuccess(response);
            }
        },
        failure: ServerApi.onErrorGlobal
    });
};

ServerApi.evalMatch = function(request, onSuccess) {
    ServerApi.post('/eval/match', request, onSuccess);
};

ServerApi.evalFindAll = function(request, onSuccess) {
    ServerApi.post('/eval/find-all', request, onSuccess);
};

ServerApi.evalSplit = function(request, onSuccess) {
    ServerApi.post('/eval/split', request, onSuccess);
};