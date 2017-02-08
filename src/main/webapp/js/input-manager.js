
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
    $(this.target).append(
            '<div id="input-box_' + this.count + '" class="input-box row">'
                + '<div class="col-lg-12">'
                + '<textarea id="input-text_' + this.count + '" '
                    + 'class="form-control input-textarea" rows="3" spellcheck="false"></textarea>'
                + '</div>'
            + '</div>\n');
    this.count++;
};

InputManager.prototype.removeInput = function() {
    if (this.count > 1) {
        this.getInputBox(this.count - 1).remove();
        this.count--;
    }
};

InputManager.prototype.collectInputs = function() {
    var array = [];
    for (var i = 0; i < this.count; i++) {
        array.push(this.getInputTextArea(i).val());
    }
    return array;
};

InputManager.prototype.showResults = function(response) {
    for (var j in response.results) {
        var result = response.results[j];
        var box = this.getInputBox(j);

        if (result.match) {
            box.removeClass("test-failed");
            box.addClass("test-passed");
        } else {
            box.addClass("test-failed");
            box.removeClass("test-passed");
        }
    }
};

InputManager.prototype.clearResults = function() {
    for (var i = 0; i < this.count; i++) {
        var box = this.getInputBox(i);
        box.removeClass("test-failed");
        box.removeClass("test-passed");
    }
};
