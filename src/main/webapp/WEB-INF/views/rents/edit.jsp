<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier une reservation</title>
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
            <h1>Modifier une reservation</h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body">
                            <form action="${pageContext.request.contextPath}/rents/edit" method="post">

                                <div class="box-body">

                                    <div class="form-group">
                                        <label for="vehicle" class="col-sm-2 control-label">Voiture</label>
                                        <div class="col-sm-10">
                                            <select class="form-control" id="vehicle" name="vehicle">
                                                <option value="">Selectionnez une voiture</option>
                                                <c:forEach items="${listeDesVoitures}" var="vehi">
                                                    <option value="${vehi.id}">${vehi.constructeur}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label for="client" class="col-sm-2 control-label">Client</label>
                                        <div class="col-sm-10">
                                            <select class="form-control" id="client" name="client">
                                                <option value="">Selectionnez un client</option>
                                                <c:forEach items="${listeDesClients}" var="cli">
                                                    <option value="${cli.id}">${cli.nom} ${cli.prenom}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="begin">Date de debut :</label>
                                        <input type="date" class="form-control" id="begin" name="begin" value="${reservation.debut}">
                                    </div>
                                    <div class="form-group">
                                        <label for="end">Date de fin :</label>
                                        <input type="date" class="form-control" id="end" name="end" value="${reservation.fin}">
                                    </div>
                                </div>
                                <!-- /.box-body -->

                                <form action="${pageContext.request.contextPath}/rents/edit" method="post">
                                    <input type="hidden" name="reservationId" value="${reservation.id}">
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
