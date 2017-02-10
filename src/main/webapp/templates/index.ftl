<#include "template.ftl">

<#assign page_title="Hello"/>

<#macro page_body>

<!-- regex input -->
<div id="box-with-regex" class="row">
  <div class="col-lg-10">
    <textarea id="input-regex" class="form-control" rows="3" spellcheck="false"
              placeholder="Type your regex here..."></textarea>
  </div>
  <div class="col-lg-2">
    <a id="btn-copy-regex" class="btn btn-default" href="#">Copy as Java string...</a>

    <script type="text/javascript" src="js/code-generation.js"></script>
    <script type="text/javascript">
      $(document).ready(function() {
          $('#btn-copy-regex').on('click', function() {
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
</script>

<!-- tab panel -->
<ul class="nav nav-tabs">
  <li class="active"><a data-toggle="tab" href="#t-match">Match</a></li>
  <li><a data-toggle="tab" href="#t-find">Find</a></li>
  <li><a data-toggle="tab" href="#t-replace">Replace</a></li>
  <li><a data-toggle="tab" href="#t-split">Split</a></li>
</ul>

<div class="tab-content">
  <div id="t-match" class="tab-pane fade in active">
    <div id="text-inputs" class="inputs-container"></div>

    <a id="btn-add-input" class="btn btn-large btn-default"  href="#">+</a>
    <a id="btn-remove-input" class="btn btn-large btn-default"  href="#">-</a>
    <a id="btn-eval-match" class="btn btn-large btn-primary"  href="#">Regex match</a>

    <script type="text/javascript" src="js/input-manager.js"></script>
    <script type="text/javascript">
  $(document).ready(function() {
      var inputManager = new InputManager('#text-inputs');

      inputManager.addInput();

      $('#btn-add-input').on('click', function() {
          inputManager.addInput();
      });

      $('#btn-remove-input').on('click', function() {
          inputManager.removeInput();
      });

      $('#btn-eval-match').on('click', function() {
          var request = {
              pattern: $('#input-regex').val(),
              inputs: inputManager.collectInputs()
          };

          $.ajax({
              type: "POST",
              url: "/eval/match",
              data: JSON.stringify(request),
              contentType: "application/json; charset=utf-8",
              dataType: "json",
              success: function(data) {
                  if (data.exception !== null) {
                      showError(data.exception);
                  } else {
                      hideError();
                      inputManager.showResults(data);
                  }
              },
              failure: showError
          });
      });

      $("#input-regex").keyup(function(e) {
          inputManager.clearAllResults();
          hideError();
      });
  });
    </script>
  </div>

  <div id="t-find" class="tab-pane fade">
    TODO
  </div>

  <div id="t-replace" class="tab-pane fade">
    TODO
  </div>

  <div id="t-split" class="tab-pane fade">
    TODO
  </div>
</div>

</#macro>

<@display_page/>