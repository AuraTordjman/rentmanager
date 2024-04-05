<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier un vehicule</title>
    <%@include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>Modifier un vehicule</h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body">
                            <form action="${pageContext.request.contextPath}/vehicles/edit" method="post">

                                <div class="form-group">
                                    <label for="constructeur">Constructeur :</label>
                                    <input type="text" class="form-control" id="constructeur" name="constructeur" value="${vehicle.constructeur}">
                                </div>
                                <div class="form-group">
                                    <label for="modele">Modele :</label>
                                    <input type="text" class="form-control" id="modele" name="modele" value="${vehicle.modele}">
                                </div>
                                <div class="form-group">
                                    <label for="nb_places">Nombre de place :</label>
                                    <input type="text" class="form-control" id="nb_places" name="nb_places" value="${vehicle.nb_places}">
                                </div>

                                <form action="${pageContext.request.contextPath}/vehicles/edit" method="post">
                                    <input type="hidden" name="vehicleId" value="${vehicle.id}">
                                    <button type="submit" class="btn btn-primary">
                                        Enregistrer
                                    </button>
                                </form>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
