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
        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-3">

                    <!-- Profile Image -->
                    <div class="box box-primary" style="width: 400px;">
                        <div class="box-body box-profile">
                            <h3 class="profile-username text-center">Information sur le client :</h3>

                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <b>Identite</b> <span class="pull-right">${client.nom} ${client.prenom}</span>
                                </li>
                                <li class="list-group-item">
                                    <b>Adresse email</b> <span class="pull-right">(${client.email})</span>
                                </li>
                                <li class="list-group-item">
                                    <b>Reservation(s)</b> <span class="pull-right">${reservations.size()}</span>
                                </li>
                            </ul>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->

                    <!-- /.nav-tabs-custom -->
                </div>
                <!-- /.col -->

            <!-- /.row -->

        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
