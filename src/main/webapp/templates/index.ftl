<#include "template.ftl">

<#assign page_title="Hello"/>

<#macro page_body>

<!-- regex -->
<div class="form-group">
  <label for="input-regex">Regex</label>
  <textarea id="input-regex" class="form-control" rows="3" spellcheck="false"></textarea>
</div>

<div id="text-inputs"></div>

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
                  inputManager.showResults(data);
              },
              failure: function(errMsg) {
                  alert(errMsg);
              }
          });

      });

      $("#input-regex").keyup(function(e) {
          inputManager.clearResults();
      });
  });
</script>

</#macro>

<@display_page/>