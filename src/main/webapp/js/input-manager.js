
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
            + '<div id="input-groups_' + this.count + '" class="col-lg-3" style="display: none;">'
            + '<table class="table table-hover">'
            + '<thead><tr><th>ID</th><th>Start</th><th>End</th></tr></thead>'
            + '<tbody id="' + 'input-groups-rows_' + this.count + '"></tbody>'
            + '</div>'
        + '</div>');
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
    for (var i in response.results) {
        var result = response.results[i];
        var box = this.getInputBox(i);

        this.clearResults(i);

        if (result.match) {
            box.addClass("test-passed");

            // update groups
            if (result.groups.length > 0) {
                var html = "";
                for (var j in result.groups) {
                    var group = result.groups[j];

                    html += '<tr>'
                        + '<td>' + (+j + 1) + '</td>'
                        + '<td>' + group.start + '</td>'
                        + '<td>' + group.end + '</td>'
                        + '</tr>'
                }

                $('#input-groups-rows_' + i).html(html);
                $('#input-groups_' + i).show();
            }

        } else {
            box.addClass("test-failed");
        }
    }
};

InputManager.prototype.clearResults = function(i) {
    var box = this.getInputBox(i);

    // update style/color
    box.removeClass("test-failed");
    box.removeClass("test-passed");

    // update groups
    $('#input-groups_' + i).hide();
};

InputManager.prototype.clearAllResults = function() {
    for (var i = 0; i < this.count; i++) {
        this.clearResults(i);
    }
};
