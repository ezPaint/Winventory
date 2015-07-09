<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Inventory</title>

    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/style.css'>
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/normalize.css' />
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/bootstrap.css' />

    <script src='${contextPath}/resources/js/actions.js' type="text/javascript"></script>
    <script src='${contextPath}/resources/js/jquery-1.11.3.min.js' type="text/javascript"></script>
    <script src='${contextPath}/resources/js/bootstrap.min.js' type="text/javascript"></script>
</head>

<body>

    <body>
        <div class="top-header">
            <div class="media-right">
                <br>
                <img src="${contextPath}/resources/images/SC-Logo.png" height="50" width="50" alt="">
            </div>
            <div class="media-body"> 
                <br>
                <h4>The Winventory</h4>
                <br>
            </div>
        </div>
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar">
                        <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="navbar">
                    <ul class="nav navbar-nav">
                        <li><a href="#">Databases</a>
                        </li>
                        <li><a href="#">Services</a>
                        </li>
                        <li><a href="#">Applications</a>
                        </li>
                        <li><a href="#">Servers</a>
                        </li>
                        <li class="active"><a href="index.html" target="_top">Software</a>
                        </li>
                        <li><a href="../barcodes/index.html" target="_top">Barcode</a>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="../admin/index.html" target="_top" class="btn">
                                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                            </a>
                        </li>
                        <li>
                            <a href="#" target="_top" class="btn">
                                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                            </a>
                        </li>
                        <li>
                            <a href="#" target="_top" class="btn">
                                <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        
        
        
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2">
                    <div class="list-group">
                        <a href="index.html" class="list-group-item">
                            <span class="glyphicon glyphicon-th-list" aria-hidden="true"> List</span>
                        </a>
                        <a href="garage.html" class="list-group-item">
                            <span class="glyphicon glyphicon-briefcase" aria-hidden="true"> Storage</span>
                        </a>
                        <a href="insert.html" class="list-group-item">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"> Add</span>
                        </a>
                        <a href="search.html" class="list-group-item">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"> Search</span>
                        </a>
                    </div>
                </div>
                <h2>${error}<h2>
                    
            </div>
        </div>
        <div class="top-header">
            <div class="center">
                <br>
                <h4>SimonComputing Â© 2015</h4>
                <br>
            </div>
        </div>
    </body>

</html>