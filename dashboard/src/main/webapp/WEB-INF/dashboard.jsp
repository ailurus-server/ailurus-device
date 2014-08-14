<!DOCTYPE html>
<html lang="en" data-ng-app="dashboardApp" data-ng-controller="AppCtrl">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/img/favicon.ico">

    <title>Dashboard</title>

    <link href="/css/dashboard/dashboard.css" rel="stylesheet">

    <link href="/webjars/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="/webjars/jquery/1.11.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="/webjars/angularjs/1.2.22/angular.min.js"></script>
    <script src="/webjars/angularjs/1.2.22/angular-route.js"></script>

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="/js/bootstrap/ie10-viewport-bug-workaround.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script src="/js/dashboard/app.js"></script>
    <script src="/js/dashboard/models.js"></script>
    <script src="/js/dashboard/controllers.js"></script>
  </head>

  <body>
    <div class="navbar navbar-default navbar-static-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <span class="navbar-brand">Dashboard</span>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>

    <div data-ng-view></div>
  </body>
</html>
