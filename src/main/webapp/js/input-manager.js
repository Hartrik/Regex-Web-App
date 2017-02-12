
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


// --- TextAreaComponent

function TextAreaComponent(id) {
    this.id = id;
}

TextAreaComponent.prototype.getTextAreaID = function() {
    return 'input-text_' + this.id;
};

TextAreaComponent.prototype.getText = function() {
    return $('#' + this.getTextAreaID()).val();
};

TextAreaComponent.prototype.render = function() {
    return '<textarea id="' + this.getTextAreaID() + '" '
        + 'class="form-control input-textarea" rows="3" spellcheck="false"></textarea>'
};

TextAreaComponent.prototype.highlight = function(start, end) {
    var area = $('#' + this.getTextAreaID());

    area.focus();
    area[0].setSelectionRange(start, end);
};


// --- GroupsComponent

function GroupsComponent(id, textAreaComponent) {
    this.id = id;
    this.textAreaComponent = textAreaComponent;
}

GroupsComponent.prototype.getGroupsBox = function() {
    return $('#input-groups_' + this.id);
};

GroupsComponent.prototype.render = function() {
    var html = '<div id="input-groups_' + this.id + '" class="col-lg-5" style="display: none;">'
        + '<div class="panel-group">'
         + '<div class="panel panel-default">'

          + '<div class="panel-heading">'
           + '<h4 class="panel-title">'
            + '<a data-toggle="collapse" href="#input-groups-collapse' + this.id + '">Groups</a>'
           + '</h4>'
          + '</div>'

          + '<div id="input-groups-collapse' + this.id + '" class="panel-collapse collapse">'
           + '<div class="panel-body">'
            + '<table id="group-table_' + this.id + '" class="table table-hover">'
             + '<thead><tr><th>ID</th><th>Start</th><th>End</th><th>Content</th></tr></thead>'
             + '<tbody id="' + 'input-groups-rows_' + this.id + '"></tbody>'
            + '</table>'
           + '</div>'
          + '</div>'

         + '</div>'
        + '</div>'
        + '</div>'


    return html;
};

GroupsComponent.prototype.showResults = function(result) {
    if (result.groups.length > 0) {
        var tableBody = $('#input-groups-rows_' + this.id);
        tableBody.html('');  // clear

        for (var j in result.groups) {
            var group = result.groups[j];
            var content = this.textAreaComponent.getText().substring(group.start, group.end);
            var hID = 'group-table_' + this.id + "_" + j;

            var html = '<tr>'
                + '<td>' + (+j + 1) + '</td>'
                + '<td>' + group.start + '</td>'
                + '<td>' + group.end + '</td>'
                + '<td class="group-content"><span>' + content + '</span></td>'
                + '<td><a id="' + hID + '" class="btn btn-xs btn-default" href="#">Select</a></td>'
                + '</tr>';

            tableBody.append(html);

            // highlighting
            (function (id, ac, start, end) {
                $('#' + id).on('click', function() {
                    ac.highlight(start, end);
                });
            })(hID, this.textAreaComponent, group.start, group.end);
        }

        this.getGroupsBox().show();
    }
};

GroupsComponent.prototype.clearResults = function() {
    this.getGroupsBox().hide();
};


// --- InputBoxComponent

function InputBoxComponent(id) {
    this.id = id;
    this.textAreaComponent = new TextAreaComponent(id);
    this.groupsComponent = new GroupsComponent(id, this.textAreaComponent);
}

InputBoxComponent.prototype.getBox = function() {
    return $('#input-box_' + this.id);
};

InputBoxComponent.prototype.render = function() {
    return '<div id="input-box_' + this.id + '" class="input-box row">'
            + '<div class="col-lg-12">'
            + this.textAreaComponent.render()
            + '</div>'

            + this.groupsComponent.render()
        + '</div>';
};

InputBoxComponent.prototype.showResults = function(result) {
    this.clearResults();

    var box = this.getBox();
    if (result.match) {
        box.addClass("test-passed");

        // update groups
        this.groupsComponent.showResults(result);

    } else {
        box.addClass("test-failed");
    }
};

InputBoxComponent.prototype.clearResults = function() {
    var box = this.getBox();

    // update style/color
    box.removeClass("test-failed");
    box.removeClass("test-passed");

    // update groups
   this.groupsComponent.clearResults();
};


// --- InputManager

function InputManager(target) {
    this.count = 0;
    this.target = target;
    this.inputs = [];
}

InputManager.prototype.addInput = function() {
    var input = new InputBoxComponent(this.count++);
    this.inputs.push(input);

    // render new input box
    $(this.target).append(input.render());
};

InputManager.prototype.removeInput = function() {
    if (this.count > 1) {
        var last = this.inputs.pop();

        last.getBox().remove();
        this.count--;
    }
};

InputManager.prototype.collectInputs = function() {
    return this.inputs.map(function(input) {
        return input.textAreaComponent.getText();
    });
};

InputManager.prototype.showResults = function(response) {
    for (var i in response.results) {
        var result = response.results[i];
        this.inputs[i].showResults(result);
    }
};

InputManager.prototype.clearAllResults = function() {
    this.forEach(function(input) {
        input.clearResults();
    });
};

InputManager.prototype.forEach = function(func) {
    for (var i in this.inputs) {
        func(this.inputs[i]);
    }
};
