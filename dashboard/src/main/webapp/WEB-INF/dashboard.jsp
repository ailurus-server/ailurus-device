<!DOCTYPE html>
<html lang="en" data-ng-app="dashboardApp" data-ng-controller="AppCtrl">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/favicon.ico">

    <title>Dashboard</title>

    <link href="webjars/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="webjars/bootstrap/3.2.0/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/dashboard/dashboard.css" rel="stylesheet">

    <script src="webjars/jquery/1.11.1/jquery.js"></script>
    <script src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="webjars/angularjs/1.2.22/angular.js"></script>
    <script src="webjars/angularjs/1.2.22/angular-route.js"></script>
    <script src="webjars/angularjs/1.2.22/angular-resource.js"></script>


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="js/bootstrap/ie10-viewport-bug-workaround.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script src="js/dashboard/app.js"></script>
    <script>
      dashboardApp.value('loginUser', {
        name: 'aardvark'
      });
    </script>

    <script src="js/dashboard/controllers.js"></script>
    <script src="js/dashboard/filters.js"></script>
    <script src="js/dashboard/services.js"></script>


  </head>

  <body>
    <div class="navbar navbar-default navbar-static-top" role="navigation"
         data-ng-show="app.showNavBar">
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
        <div class="navbar-collapse collapse navbar-center-parent">
          <ul class="nav navbar-nav navbar-center">
            <li data-ng-class="{active: app.panel == 'device'}"><a href="#/device">Device</a></li>
            <li data-ng-class="{active: app.panel == 'users'}"><a href="#/users">Users</a></li>
            <li data-ng-class="{active: app.panel == 'store'}"><a href="#/store">Store</a></li>
            <li data-ng-class="{active: app.panel == 'support'}"><a href="#/support">Support</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li>
              <p class="navbar-text">
                  {{user.name}}
              <p>
            </li>
            <li>
                <button type="button" class="btn btn-default navbar-btn">
                    <span class="glyphicon glyphicon-log-out"></span>
                </button>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <div data-ng-view></div>
  </body>
</html>
