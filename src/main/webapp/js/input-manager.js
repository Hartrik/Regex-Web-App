
Element.prototype.remove = function() {
    this.parentElement.removeChild(this);
};

NodeList.prototype.remove = HTMLCollection.prototype.remove = function() {
    for (var i = this.length - 1; i >= 0; i--) {
        if (this[i] && this[i].parentElement) {
            this[i].parentElement.removeChild(this[i]);
        }
    }
};

function InputManager(target) {
    this.count = 0;
    this.target = target;
}

InputManager.prototype.getInputBox = function(i) {
    return $('#input-box_' + i);
};

InputManager.prototype.getInputTextArea = function(i) {
    return $('#input-text_' + i);
};

InputManager.prototype.addInput = function() {
    this.count++;
    $(this.target).append(
            '<div id="input-box_' + this.count + '">'
                + '<div class="form-group">'
                + '<label for="input-text_1">Input #' + this.count + '</label>'
                + '<textarea id="input-text_' + this.count + '" class="form-control" rows="3" spellcheck="false"></textarea>'
                + '</div>'
            + '</div>\n');
};

InputManager.prototype.removeInput = function() {
    if (this.count > 1) {
        this.getInputBox(this.count).remove();
        this.count--;
    }
};

InputManager.prototype.collectInputs = function() {
    var array = [];
    for (var i = 1; i <= this.count; i++) {
        array.push(this.getInputTextArea(i).val());
    }
    return array;
};

InputManager.prototype.showResults = function(response) {
    var i = 1;
    for (var j in response.results) {
        var result = response.results[j];

        var color;
        if (result.match) {
            color = "#00FF00";
        } else {
            color = "#FF0000";
        }

        this.getInputBox(i++).css('background-color', color);
    }
};

InputManager.prototype.clearResults = function() {
    for (var i = 1; i <= this.count; i++) {
        this.getInputBox(i).css('background-color', 'white');
    }
};
