
String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.split(search).join(replacement);
};


function CodeGenerator() {
}

CodeGenerator.escape = function(text) {
    return text
            .replaceAll('\\', '\\\\')
            .replaceAll('"', '\\"')
            .replaceAll('\n', '\\n')
            .replaceAll('\r', '\\r')
    ;
};

CodeGenerator.asJavaString = function(text) {
    return '"' + CodeGenerator.escape(text) + '"';
};


function ClipboardUtils() {
}

ClipboardUtils.copy = function(text) {
    var $temp = $("<input>");
    $("body").append($temp);
    $temp.val(text).select();
    document.execCommand("copy");
    $temp.remove();
};