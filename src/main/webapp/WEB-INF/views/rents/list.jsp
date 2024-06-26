<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Reservations
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/rents/create">Ajouter</a>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body no-padding">
                            <table class="table table-striped">
                                <tr>
                                    <th style="width: 10px">#</th>
                                    <th>Voiture</th>
                                    <th>Client</th>
                                    <th>Debut</th>
                                    <th>Fin</th>
                                    <th>Action</th>
                                </tr>
                                <c:forEach items="${rents}" var="reservation">
                                        <tr>
                                        <td>${reservation.id}</td>
                                        <td>${reservation.vehicle_id}</td>
                                        <td>${reservation.client_id}</td>
                                        <td>${reservation.debut}</td>
                                        <td>${reservation.fin}</td>
                                        <td>

                                            <div class="btn-group" role="group" style="display: flex;">
                                                <form action="${pageContext.request.contextPath}/rents/details" method="get">
                                                    <input type="hidden" name="reservationId" value="${reservation.id}">
                                                    <button type="submit" class="btn btn-primary" style="margin-right: 5px;">
                                                        <i class="fa fa-play"></i>
                                                    </button>
                                                </form>
                                                <a class="btn btn-success " style="border-radius:3px;" href="${pageContext.request.contextPath}/rents/edit?id=${reservation.id}">
                                                    <i class="fa fa-edit"></i>
                                                </a>
                                                <form action="${pageContext.request.contextPath}/rents/delete" method="post">
                                                    <input type="hidden" name="reservationId" value="${reservation.id}">
                                                    <button type="submit" class="btn btn-danger" style="margin-left: 5px;">
                                                        <i class="fa fa-trash"></i>
                                                    </button>
                                                </form>
                                            </div>
                                        </td>

                                        </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
