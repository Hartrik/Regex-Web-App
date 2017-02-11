<#include "template.ftl">

<#assign page_title="error"/>

<#macro page_body>
  <div class="alert alert-danger">
      ${message}
  </div>
</#macro>

<@display_page/>