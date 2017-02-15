<#include "template.ftl">

<#assign page_title="Java 8 Regex Designer"/>

<#macro page_body>

<script type="text/javascript" src="js/server-api.js"></script>
<script type="text/javascript" src="js/code-generation.js"></script>
<script type="text/javascript" src="js/input-manager.js"></script>
<script type="text/javascript">components = []</script>

<!-- regex input -->
<div id="box-with-regex" class="row">
  <div class="col-lg-10">
    <textarea id="input-regex" class="form-control" rows="3" spellcheck="false"
              placeholder="Type your regex here..."></textarea>
    <script type="text/javascript">
      $(document).ready(function () {
        $("#input-regex").keyup(function (e) {
          components.forEach(function (i) { i.clearAllResults(); });
          hideError();
        });
      });
    </script>

  </div>
  <div class="col-lg-2">
    <a id="btn-copy-regex" class="btn btn-default" href="#">Copy as Java
      string...</a>
    <script type="text/javascript">
      $(document).ready(function () {
        $('#btn-copy-regex').on('click', function () {
          var pattern = $('#input-regex').val();
          ClipboardUtils.copy(CodeGenerator.asJavaString(pattern));
        });
      });
    </script>
  </div>
</div>

<!-- error alert -->
<div id="error-alert" class="alert alert-danger fade in" style="display: none;">
  <span id="error-alert-content"></span>
</div>
<script type="text/javascript">
  function showError(msg) {
    $('#error-alert').show();
    $('#error-alert-content').text(msg);
  }
  function hideError() {
    $('#error-alert').hide();
  }
  ServerApi.onErrorGlobal = showError;
  ServerApi.onSuccessGlobal = hideError;
</script>

<!-- tab panel -->
<ul class="nav nav-tabs">
  <li class="active"><a data-toggle="tab" href="#t-match">Match</a></li>
  <li><a data-toggle="tab" href="#t-find">Find</a></li>
  <li><a data-toggle="tab" href="#t-split">Split</a></li>
  <li><a data-toggle="tab" href="#t-replace">Replace</a></li>
</ul>

<div class="tab-content">
  <div id="t-match" class="tab-pane fade in active"></div>
  <script type="text/javascript">
    $(document).ready(function () {

      var builder = function(inputBox) {
        inputBox
            .addEditorModule(function(s, t) { return new TextAreaBoxModule(s, t); })
            .addModule(function(s, t) { return new HideableBoxModule(s, t, new CollapsibleBoxModule(s, t, new GroupTableBoxModule(s, t))) });
      };

      var eval = function(ins, request) {
        request.pattern = $('#input-regex').val();

        ServerApi.evalMatch(request, function(data) {
          ins.showResults(data);
        });

      };

      var component = new RegexMethodComponent('m', builder, eval);

      $('#t-match').html(component.render());
      component.init();

      components.push(component);
    });
  </script>

  <div id="t-find" class="tab-pane fade"></div>
  <script type="text/javascript">
    $(document).ready(function () {

      var builder = function(inputBox) {
        inputBox
            .addEditorModule(function(s, t) { return new TextAreaBoxModule(s, t); })
            .addModule(function(s, t) { return new HideableBoxModule(s, t, new GroupTableBoxModule(s, t)) });
      };

      var eval = function(ins, request) {
        request.pattern = $('#input-regex').val();

        ServerApi.evalFindAll(request, function(data) {
          ins.showResults(data);
        });

      };

      var component = new RegexMethodComponent('f', builder, eval);

      $('#t-find').html(component.render());
      component.init();

      components.push(component);
    });
  </script>

  <div id="t-split" class="tab-pane fade">
    <script type="text/javascript">
      $(document).ready(function () {
        var builder = function(inputBox) {
          inputBox
              .addEditorModule(function(s, t) { return new TextAreaBoxModule(s, t); })
              .addModule(function(s, t) { return new HideableBoxModule(s, t, new GroupTableBoxModule(s, t)); });
        };

        var eval = function(ins, request) {
          request.pattern = $('#input-regex').val();

          ServerApi.evalSplit(request, function(data) {
            ins.showResults(data);
          });
        };

        var component = new RegexMethodComponent('s', builder, eval);

        $('#t-split').html(component.render());
        component.init();

        components.push(component);
      });
    </script>
  </div>

  <div id="t-replace" class="tab-pane fade">
    TODO
  </div>
</div>

</#macro>

<@display_page/>