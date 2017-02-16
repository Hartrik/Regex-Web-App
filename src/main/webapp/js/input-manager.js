
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

function InputBoxComponent(inputManager, suffix) {
    this.inputManager = inputManager;
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

InputBoxComponent.prototype.init = function() {
    this.allModules.forEach(function(m) {
        m.init();
    });
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


// --- InputManagerComponent

function InputManagerComponent(suffix, inputBoxBuilder, evalProvider, patternProvider) {
    this.suffix = suffix;
    this.inputBoxBuilder = inputBoxBuilder;
    this.evalProvider = evalProvider;
    this.patternProvider = patternProvider;

    this.inputs = [];
    this.count = 0;

    this.DIV_BOXES = 'text-inputs_' + suffix;
    this.BUTTON_ADD = 'btn-add-input_' + suffix;
    this.BUTTON_REMOVE = 'btn-remove-input_' + suffix;
    this.BUTTON_EVAL = 'btn-eval_' + suffix;
}

InputManagerComponent.prototype.render = function() {
    return `
        <div id="${this.DIV_BOXES}" class="inputs-container"></div>
        <a id="${this.BUTTON_ADD}" class="btn btn-large btn-default" href="#/">+</a>
        <a id="${this.BUTTON_REMOVE}" class="btn btn-large btn-default" href="#/">-</a>
        <a id="${this.BUTTON_EVAL}" class="btn btn-large btn-primary" href="#/">Process</a>
    `;
};

InputManagerComponent.prototype.init = function() {
    var thisRef = this;

    thisRef.addInput();

    $('#' + this.BUTTON_ADD).on('click', function() {
        thisRef.addInput();
    });

    $('#' + this.BUTTON_REMOVE).on('click', function() {
        thisRef.removeInput();
    });

    $('#' + this.BUTTON_EVAL).on('click', function() {
        thisRef.eval(thisRef.inputs);
    });
};

InputManagerComponent.prototype.addInput = function() {
    var input = new InputBoxComponent(this, this.suffix + '-' + this.count++);

    // add modules
    this.inputBoxBuilder(input);

    // render input box
    $('#' + this.DIV_BOXES).append(input.render());

    // init input box
    input.init();

    this.inputs.push(input);
};

InputManagerComponent.prototype.removeInput = function() {
    if (this.count > 1) {
        var last = this.inputs.pop();

        last.getBox().remove();
        this.count--;
    }
};

InputManagerComponent.prototype.createRequest = function(selectedInputs) {
    var request = {
        pattern: this.patternProvider(),
    };

    selectedInputs.forEach(function(input) {
        return input.editorModule.createRequest(request);
    });

    return request;
};

InputManagerComponent.prototype.eval = function(selectedInputs) {
    var thisRef = this;

    this.evalProvider(this.createRequest(selectedInputs), function(data) {
        thisRef.showResults(selectedInputs, data);
    });
};

InputManagerComponent.prototype.showResults = function(selectedInputs, response) {
    var results = response.results;
    selectedInputs.forEach(function(input) {
        input.showResults(results.shift());
    });
};

InputManagerComponent.prototype.clearAllResults = function() {
    this.inputs.forEach(function(input) {
        input.clearResults();
    });
};
