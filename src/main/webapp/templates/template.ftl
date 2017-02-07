<#macro page_body></#macro>

<#macro display_page>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>${page_title}</title>

    <link rel="stylesheet" type="text/css" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="/css/main.css" />

    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script type="text/javascript" src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

  </head>

  <body>
    <#include "template-nav.ftl">

    <div class="container">
      <@page_body/>
    </div>

    <footer>
      <hr>
      <div class="container">
        <p>Copyright &copy; Patrik Harag 2017</p>
      </div>
    </footer>

  </body>
</html>
</#macro>