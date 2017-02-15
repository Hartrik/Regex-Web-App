
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


// --- InputBoxComponent

function InputBoxComponent(suffix) {
    this.suffix = suffix;
    this.DIV_BOX = 'input-box_' + suffix;

    this.editorModule = undefined;
    this.allModules = [];
}

InputBoxComponent.prototype.addModule = function(factory) {
    var module = factory(this.suffix, this);
    this.allModules.push(module);
    return this;
};

InputBoxComponent.prototype.addEditorModule = function(factory) {
    var module = factory(this.suffix, this);
    this.editorModule = module;
    this.allModules.push(module);
    return this;
};

InputBoxComponent.prototype.render = function() {
    var components = "";

    this.allModules.forEach(function(m) {
        components += m.render();
    });

    return `
        <div id="${this.DIV_BOX}" class="input-box row">
          ${components}
        </div>
    `;
};

InputBoxComponent.prototype.getBox = function() {
    return $('#' + this.DIV_BOX);
};

InputBoxComponent.prototype.showResults = function(result) {
    this.clearResults();

    var box = this.getBox();
    if (result.hasOwnProperty('match')) {

        if (result.match) {
            box.addClass("test-passed");

            this.allModules.forEach(function(m) {
                m.showResults(result);
            });

        } else {
            box.addClass("test-failed");
        }

    } else {
        box.addClass("test-processed");

        this.allModules.forEach(function(m) {
            m.showResults(result);
        });
    }
};

InputBoxComponent.prototype.clearResults = function() {
    var box = this.getBox();

    // update style/color
    box.removeClass("test-failed");
    box.removeClass("test-passed");
    box.removeClass("test-processed");

    this.allModules.forEach(function(m) {
        m.clearResults();
    });
};


// --- InputManager

function InputManager(suffix, target, inputBoxBuilder) {
    this.inputBoxBuilder = inputBoxBuilder;
    this.suffix = suffix;
    this.count = 0;
    this.targetID = target;
    this.inputs = [];
}

InputManager.prototype.addInput = function() {
    var input = new InputBoxComponent(this.suffix + '-' + this.count++);
    this.inputBoxBuilder(input);  // init input box

    this.inputs.push(input);

    // render new input box
    $('#' + this.targetID).append(input.render());
};

InputManager.prototype.removeInput = function() {
    if (this.count > 1) {
        var last = this.inputs.pop();

        last.getBox().remove();
        this.count--;
    }
};

InputManager.prototype.createRequest = function(request) {
    return this.inputs.map(function(input) {
        return input.editorModule.createRequest(request);
    });
};

InputManager.prototype.showResults = function(response) {
    for (var i in response.results) {
        var result = response.results[i];
        this.inputs[i].showResults(result);
    }
};

InputManager.prototype.clearAllResults = function() {
    this.inputs.forEach(function(input) {
        input.clearResults();
    });
};


// --- RegexMethodComponent

function RegexMethodComponent(suffix, inputBoxBuilder, eval) {
    this.eval = eval;

    this.DIV_BOXES = 'text-inputs_' + suffix;
    this.BUTTON_ADD = 'btn-add-input_' + suffix;
    this.BUTTON_REMOVE = 'btn-remove-input_' + suffix;
    this.BUTTON_EVAL = 'btn-eval_' + suffix;

    this.inputManager = new InputManager(suffix, this.DIV_BOXES, inputBoxBuilder);
}

RegexMethodComponent.prototype.render = function() {
    return `
        <div id="${this.DIV_BOXES}" class="inputs-container"></div>
        <a id="${this.BUTTON_ADD}" class="btn btn-large btn-default" href="#/">+</a>
        <a id="${this.BUTTON_REMOVE}" class="btn btn-large btn-default" href="#/">-</a>
        <a id="${this.BUTTON_EVAL}" class="btn btn-large btn-primary" href="#/">Process</a>
    `;
};

RegexMethodComponent.prototype.init = function() {
    var inputManager = this.inputManager;
    var evalFunction = this.eval;
    var thisRef = this;

    inputManager.addInput();

    $('#' + this.BUTTON_ADD).on('click', function() {
        inputManager.addInput();
    });

    $('#' + this.BUTTON_REMOVE).on('click', function() {
        inputManager.removeInput();
    });

    $('#' + this.BUTTON_EVAL).on('click', function() {
        var request = {};
        inputManager.createRequest(request);

        evalFunction(thisRef, request);
    });
};

RegexMethodComponent.prototype.showResults = function(data) {
    this.inputManager.showResults(data);
};

RegexMethodComponent.prototype.clearAllResults = function() {
    this.inputManager.clearAllResults();
};
