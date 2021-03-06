
// --- BoxModule

function BoxModule(suffix, inputBoxComponent) {
    this.suffix = suffix;
    this.inputBoxComponent = inputBoxComponent;
}

BoxModule.prototype.render = function() {};
BoxModule.prototype.init = function() {};
BoxModule.prototype.createRequest = function(request) {};
BoxModule.prototype.showResults = function(result /* part of response */) {};
BoxModule.prototype.clearResults = function() {};


// --- TextAreaBoxModule extends BoxModule

function TextAreaBoxModule(suffix, inputBoxComponent) {
    BoxModule.call(this, suffix, inputBoxComponent);

    this.TEXT_AREA = 'input-text_' + suffix;
}

TextAreaBoxModule.prototype = new BoxModule();

TextAreaBoxModule.prototype.render = function() {
    return `
        <textarea id="${this.TEXT_AREA}" class="form-control input-textarea"
                  rows="3" spellcheck="false"></textarea>
    `;
};

TextAreaBoxModule.prototype.init = function() {
    var input = this.inputBoxComponent;
    $('#' + this.TEXT_AREA).keydown(function(e) {
        if ((e.keyCode === 0 || e.keyCode === 32) && e.ctrlKey) {
            input.inputManager.eval([ input ]);
        }
    });
};

TextAreaBoxModule.prototype.createRequest = function(request) {
    if (!request.hasOwnProperty('inputs')) {
        request.inputs = [];
    }

    request.inputs.push(this.getText());
};

TextAreaBoxModule.prototype.getText = function() {
    return $('#' + this.TEXT_AREA).val();
};

TextAreaBoxModule.prototype.select = function(start, end) {
    var area = $('#' + this.TEXT_AREA);

    area.focus();
    area[0].setSelectionRange(start, end);
};


// --- EnclosingBoxModule extends BoxModule

function EnclosingBoxModule(suffix, inputBoxComponent, module) {
    BoxModule.call(this, suffix, inputBoxComponent);

    this.module = module;
}

EnclosingBoxModule.prototype = new BoxModule();

EnclosingBoxModule.prototype.render = function() {
    return this.module.render();
};

EnclosingBoxModule.prototype.init = function() {
    return this.module.init();
};

EnclosingBoxModule.prototype.showResults = function(result) {
    return this.module.showResults(result)
};

EnclosingBoxModule.prototype.clearResults = function() {
    return this.module.clearResults();
};

EnclosingBoxModule.prototype.createRequest = function(request) {
    return this.module.createRequest(request);
};

// editor
EnclosingBoxModule.prototype.getText = function() {
    return this.module.getText();
};

// editor
EnclosingBoxModule.prototype.select = function(start, end) {
    return this.module.select(start, end);
};


// --- HideableBoxModule extends EnclosingBoxModule

function HideableBoxModule(suffix, inputBoxComponent, module) {
    EnclosingBoxModule.call(this, suffix, inputBoxComponent, module);

    this.DIV_HIDEABLE = 'hideable_' + suffix;
}

HideableBoxModule.prototype = new EnclosingBoxModule();

HideableBoxModule.prototype.render = function() {
    var content = this.module.render();

    return `
        <div id="${this.DIV_HIDEABLE}" style="display: none;">
          ${content}
        </div>
    `;
};

HideableBoxModule.prototype.showResults = function(result) {
    var r = this.module.showResults(result);
    if (r)
        $('#' + this.DIV_HIDEABLE).show();

    return r;
};

HideableBoxModule.prototype.clearResults = function() {
    var r = this.module.clearResults();
    $('#' + this.DIV_HIDEABLE).hide();
    return r;
};


// --- CollapsibleBoxModule extends EnclosingBoxModule

function CollapsibleBoxModule(suffix, inputBoxComponent, module) {
    EnclosingBoxModule.call(this, suffix, inputBoxComponent, module);

    this.BTN_COLLAPSE = 'input-groups-collapse_' + suffix;
}

CollapsibleBoxModule.prototype = new EnclosingBoxModule();

CollapsibleBoxModule.prototype.render = function() {
    var content = this.module.render();

    return `
        <div class="panel-group">
        <div class="panel panel-default">
        
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" href="#${this.BTN_COLLAPSE}">Groups</a>
            </h4>
          </div>
        
          <div id="${this.BTN_COLLAPSE}" class="panel-collapse collapse">
            <div class="panel-body">
              ${content}
            </div>
          </div>
        
        </div>
        </div>
    `;
};


// --- GroupTableBoxModule extends BoxModule

function GroupTableBoxModule(suffix, inputBoxComponent) {
    BoxModule.call(this, suffix, inputBoxComponent);

    this.TABLE_GROUPS = 'group-table_' + suffix;
    this.TABLE_GROUPS_BODY = 'input-groups-rows_' + suffix;

    this.inputBoxComponent = inputBoxComponent;
}

GroupTableBoxModule.prototype = new BoxModule();

GroupTableBoxModule.prototype.render = function() {
    return `
          <table id="${this.TABLE_GROUPS}" class="table table-hover">
            <thead><tr><th>#</th><th>Start</th><th>End</th><th>Content</th><th></th></tr></thead>
            <tbody id="${this.TABLE_GROUPS_BODY}"></tbody>
          </table>
    `;
};

GroupTableBoxModule.prototype.showResults = function(result) {
    if (result.groups.length > 0) {
        var tableBody = $('#' + this.TABLE_GROUPS_BODY);
        tableBody.html('');  // clear

        var textAreaBoxModule = this.inputBoxComponent.editorModule;

        for (var j in result.groups) {
            var id  = (+j + 1);
            var group = result.groups[j];
            var content = textAreaBoxModule.getText().substring(group.start, group.end);
            var outputTextArea = OutputTextComponent.render(content);
            var BTN_SELECT = this.TABLE_GROUPS + "-" + j;

            var html = `
                <tr>
                  <td class="col-min">${id}</td>
                  <td class="col-min">${group.start}</td>
                  <td class="col-min">${group.end}</td>
                  <td class="col-max">${outputTextArea}</td>
                  <td class="col-min"><a id="${BTN_SELECT}" class="btn btn-xs btn-default" href="#/">Select</a></td>
                </tr>
            `;

            tableBody.append(html);

            // highlighting
            (function (id, ac, start, end) {
                $('#' + id).on('click', function() {
                    ac.select(start, end);
                });
            })(BTN_SELECT, textAreaBoxModule, group.start, group.end);
        }

        return true;
    }
    return false;
};

GroupTableBoxModule.prototype.clearResults = function() {
    $('#' + this.TABLE_GROUPS_BODY).html('');
};


// --- SeparatorBoxModule extends BoxModule

function SeparatorBoxModule(suffix, inputBoxComponent, size) {
    BoxModule.call(this, suffix, inputBoxComponent);

    this.size = size;
}

SeparatorBoxModule.prototype = new BoxModule();

SeparatorBoxModule.prototype.render = function() {
    return `
        <div style="height: ${this.size}px;"></div>
    `;
};


// --- OutputText

function OutputTextComponent() {

}

OutputTextComponent._entityMap = {
    '&': '&amp;',
    '<': '&lt;',
    '>': '&gt;',
    '"': '&quot;',
    "'": '&#39;',
    '/': '&#x2F;',
    '`': '&#x60;',
    '=': '&#x3D;'
};

OutputTextComponent._escape = function(string) {
    return String(string).replace(/[&<>"'`=\/]/g, function(s) {
        return OutputTextComponent._entityMap[s];
    });
};

OutputTextComponent.render = function(text) {
    var escaped = OutputTextComponent._escape(text);
    return `
          <span class="output-text">${escaped}</span>
    `;
};

//