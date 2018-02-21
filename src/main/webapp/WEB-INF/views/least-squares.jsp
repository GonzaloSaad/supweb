<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Matematica Superior - UTN FRC</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/webdesign/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/webdesign/css/simple-sidebar.css" rel="stylesheet">

</head>

<body>
 <!-- Page header wrapper -->
        <div class="header">
            <a href="#menu-toggle" class="header-block" id="menu-toggle">
                <div class="menuicon"></div>
                <div class="menuicon"></div>
                <div class="menuicon"></div>
            </a>
            <h1 class = "header-block"><a href="${pageContext.request.contextPath}/home">Matematica Superior<a> > Minimos Cuadrados</h1>
        </div>
    <div id="wrapper">

        <!-- Sidebar -->
        <div id="sidebar-wrapper">
            <ul class="sidebar-nav">
                <li class="sidebar-brand">
                    <a href="${pageContext.request.contextPath}/home">
                        Matematica Superior
                    </a>
                </li>
                <li>
                    <a class = "actual" href="${pageContext.request.contextPath}/least-squares">Minimos Cuadrados</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/newton-raphson">Newton-Raphson</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/runge-kutta">Runge-Kutta</a>
                </li>

            </ul>
        </div>
        <!-- /#sidebar-wrapper -->





        <!-- Page Content -->
        <div id="page-content-wrapper">   <div class="container-fluid">

            <div>
                <form action="/least-squares" method="post">
                    <p>x:</p><input type="text" name="x"><br>
                    <p>y:</p><input type="text" name="y"><br>
                    <p>f(x):</p><input type="text" name="f"><br>
                    <input type="radio" name="method" value="gauss" checked> Gauss  
                    <input type="radio" name="method" value="gauss-jordan"> Gauss-Jordan<br>
                    <input type="radio" name="pivot" value="total" checked> Pivot Total  
                    <input type="radio" name="pivot" value="partial"> Pivot Parcial<br>
                    <input type="submit" value="Resolver">

                </form>
            
            </div>
            <div>
                <hr>
                <p>${solution}</p>
                <hr>
                <p>${error}</p>
                <hr>
                <p>${finalFunction}</p>
            </div>
            
        </div>
    </div>
    <!-- /#page-content-wrapper -->

</div>
<!-- /#wrapper -->

<!-- Bootstrap core JavaScript -->
<script src="${pageContext.request.contextPath}/webdesign/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/webdesign/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Menu Toggle Script -->
<script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
</script>

</body>

</html>