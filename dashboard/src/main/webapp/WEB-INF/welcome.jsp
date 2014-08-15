<!DOCTYPE html>
<html lang="en" data-ng-app="welcomeApp" data-ng-controller="AppCtrl">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/favicon.ico">

    <title data-ng-bind="app.title">Welcome</title>

    <link href="webjars/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="webjars/bootstrap/3.2.0/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/welcome/welcome.css" rel="stylesheet">

    <script src="webjars/jquery/1.11.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="webjars/angularjs/1.2.22/angular.min.js"></script>
    <script src="webjars/angularjs/1.2.22/angular-route.js"></script>

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="js/bootstrap/ie10-viewport-bug-workaround.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script src="js/welcome/app.js"></script>
    <script src="js/welcome/controllers.js"></script>
  </head>

  <body>
    <br>
    <div class="container" data-ng-show="app.step > 0 && !app.done">
      <div class="row">
        <div class="col-sm-12">
          <div class="btn-group btn-breadcrumb">
            <a href="#/" class="btn" data-ng-class="app.getStepClass(0)">Welcome</a>
            <a href="#/user" class="btn" data-ng-class="app.getStepClass(1)">Create Your First User</a>
            <a href="#/name" class="btn" data-ng-class="app.getStepClass(2)">Name Your Device</a>
            <a href="#/online" class="btn" data-ng-class="app.getStepClass(3)">Get it Online</a>
            <a href="#/url" class="btn" data-ng-class="app.getStepClass(4)">Set a URL</a>
            <a href="#/review" class="btn" data-ng-class="app.getStepClass(5)">Review</a>
          </div>
        </div>
      </div>
    </div>
    <div data-ng-view></div>
  </body>
</html>
