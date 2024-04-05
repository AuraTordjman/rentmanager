<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier un client</title>
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
            <h1>Modifier un client</h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body">
                            <form action="${pageContext.request.contextPath}/users/edit" method="post">

                                <div class="form-group">
                                    <label for="nom">Nom :</label>
                                    <input type="text" class="form-control" id="nom" name="nom" value="${client.nom}">
                                </div>
                                <div class="form-group">
                                    <label for="prenom">Prénom :</label>
                                    <input type="text" class="form-control" id="prenom" name="prenom" value="${client.prenom}">
                                </div>
                                <div class="form-group">
                                    <label for="email">Email :</label>
                                    <input type="email" class="form-control" id="email" name="email" value="${client.email}">
                                </div>
                                <div class="form-group">
                                    <label for="naissance">Date de naissance :</label>
                                    <input type="date" class="form-control" id="naissance" name="naissance" value="${client.naissance}">
                                </div>
                                <form action="${pageContext.request.contextPath}/users/edit" method="post">
                                    <input type="hidden" name="clientId" value="${client.id}">
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
