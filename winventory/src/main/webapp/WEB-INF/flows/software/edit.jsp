<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Edit Application</title>
    
<!-- Include Required Prerequisites for Date Range Picker -->
<script type="text/javascript" src="//cdn.jsdelivr.net/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/moment.min.js"></script>

<!-- Include Date Range Picker -->
<script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/1/daterangepicker.js"></script>
<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/1/daterangepicker-bs3.css" />
<script type="text/javascript">
$(function() {
    $('input[name="purchasedDate"]').daterangepicker({
        format: 'YYYY-MM-DD',
        minDate: '1970-01-01',
        singleDatePicker: true,
        showDropdowns: true
    }); 
    $('input[name="expirationDate"]').daterangepicker({
        format: 'YYYY-MM-DD',
        minDate: '1970-01-01',
        singleDatePicker: true,
        showDropdowns: true
    }); 

});
</script>

    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/style.css'>
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/normalize.css' />
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/bootstrap.css' />

    <script src='${contextPath}/resources/js/actions.js' type="text/javascript"></script>
    <script src='${contextPath}/resources/js/bootstrap.min.js' type="text/javascript"></script>
    <script src='${contextPath}/resources/js/validator.js'
    type="text/javascript"></script>
    
    <!--  TYPEAHEAD  -->
    <script src="${contextPath}/resources/js/typeahead.bundle.js"></script>
    <script src="${contextPath}/resources/js/winventory-typeahead.js"></script>
</head>

<style>
    table {
        width: 100%;
    }
    
    thead,
    tbody,
    tr,
    td,
    th {
        display: block;
    }
    
    tr:after {
        content: ' ';
        display: block;
        visibility: hidden;
        clear: both;
    }
    
    thead th {
        height: 30px;
        /*text-align: left;*/
    }
    
    tbody {
        height: 200px;
        overflow-y: auto;
    }
    
    thead {
        /* fallback */
    }
    
    tbody td,
    thead th {
        width: 14%;
        float: left;
    }
</style>

<body>
<jsp:include page="/base.jsp" />
    <div class="container-fluid">
        <div class="row">
            <jsp:include page="swBase.jsp" />
            <div class="col-md-8">
                <div class="main">
                    <div class="boom">
                        <h2 class="center">Edit Application Info</h2>
                    </div>
                    <br>
                    <div class="padme">
                    
                        <%@ page
                            import="com.simoncomputing.app.winventory.domain.Software"%>
                        <%@ page import="java.util.ArrayList"%>

                        <%
                            //Retrieve software object to be edited   
                         
                            Software software = (Software) request.getAttribute("software");

                            if (software != null) {
                        %>
                    
                        <form class="form-horizontal" action="edit"
                            data-toggle="validator" role="form" method="post" name="myform"
                            onsubmit="return validateDates();">
                            
                            <!-- Software object's name -->
                            <div class="form-group">
                                <label for="name" class="col-sm-2 control-label">Name </label>
                                <div class="col-sm-9 search-field">
                                    <input name="name" type="text" id="name" class="form-control search-software-name"
                                        value="<%=software.getName()%>" required>
                                </div>
                                <div class="col-sm-10 col-sm-offset-2">
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            
                            <!-- Software object's serial number -->
                            <div class="form-group">
                                <label for="serialNo" class="col-sm-2 control-label">Serial Number
                                </label>
                                <div class="col-sm-9">
                                    <input name="serialNo" type="text" id="serialNo"
                                        class="form-control" value="<%=software.getSerialNo()%>" required>
                                </div>
                                <div class="col-sm-10 col-sm-offset-2">
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            
                            <!-- Software object's license key -->
                            <div class="form-group">
                                <label for="licenseKey" class="col-sm-2 control-label">License Key </label>
                                <div class="col-sm-9">
                                    <input name="licenseKey" type="text" id="licenseKey" 
                                        class="form-control" value="<%=software.getLicenseKey()%>" required>
                                </div>
                                <div class="col-sm-10 col-sm-offset-2">
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            
                            <!-- Software object's version -->
                            <div class="form-group">
                                <label for="version" class="col-sm-2 control-label">Version</label>
                                <div class="col-sm-9">
                                    <input name="version" type="text" id="version"
                                        class="form-control" value="<%=software.getVersion()%>" required>
                                </div>
                                <div class="col-sm-10 col-sm-offset-2">
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            
                            <!-- Software object's cost -->
                            <div class="form-group">
                                <label for="cost" class="col-sm-2 control-label">Cost</label>
                                <div class="col-sm-9">
                                    <input name="cost" type="number" step="any" id="cost"
                                        class="form-control" value="<%=software.getCost()%>" required>
                                </div>
                                <div class="col-sm-10 col-sm-offset-2">
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            
                            <!-- Software object's purchased date -->
                            <div class="form-group">
                                <label for="purchasedDate" class="col-sm-2 control-label">Date Purchased</label>
                                <div class="col-sm-9">
                                    <input name="purchasedDate" type="date" id="purchasedDate"
                                        class="form-control" value="<%=software.getPurchasedDate()%>" required>${pDateError}
                                </div>
                                <div class="col-sm-10 col-sm-offset-2">
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            
                            <!-- Software object's expiration date -->
                            <div class="form-group">
                                <label for="expirationDate" class="col-sm-2 control-label">Expiration Date</label>
                                <div class="col-sm-9">
                                    <input name="expirationDate" type="date" id="expirationDate"
                                        class="form-control" value="<%=software.getExpirationDate()%>" required>${eDateError}
                                </div>
                                <div class="col-sm-10 col-sm-offset-2">
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            
                            <!-- Software object's description -->
                            <div class="form-group">
                                <label for="description" class="col-sm-2 control-label">Description</label>
                                <div class="col-sm-9">
                                    <input name="description" type="text" id="description"
                                        class="form-control" value="<%=software.getDescription()%>" required>
                                </div>
                                <div class="col-sm-10 col-sm-offset-2">
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            
                            <!-- Form submission -->
                            <div class="form-group">
                                <div class="col-sm-10 col-sm-offset-2">
                                    <button type="submit" value="Update" name="button" class="btn btn-primary">Submit</button>
                                    <button type="reset" class="btn btn-default">Reset</button>
                                    <a class="btn btn-default"
                                    href="${contextPath}/software/view?key=<%=software.getKey()%>" role="button">Cancel</a>
                                    
                                </div>
                                <div class="col-sm-10 col-sm-offset-2">
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                        </form>
                         <br>
                         <br>
                         <br>
                         <br>
                         <hr>
                         <br>
                         <!-- <div class="center"> -->
                         	<!-- <div class="col-md-5"></div> -->
                         	<!-- <div class="col-md-4"> -->
                         
                          	<form action="${contextPath}/software/view" role="form" method="get" class="form-group">

								<c:if test="${userInfo.hasPermission.deleteSoftware }">
                                    <input type="hidden" id="key" name="key" value="<%=software.getKey()%>">
                                        <input type="hidden" id="delete" name="delete" value="true">
                                    <button type="Submit" name="button" value="Delete" class="btn btn-danger btn-lg btn-center">Delete Forever</button>
                                    
								</c:if>
						 	</form>
						
						 	<!-- </div>
                         	<div class="col-md-3"></div> -->
						 <!-- </div> -->

						<%
						    }
						%>
                                        
                        <br>
                        <div>
                            
                        </div>
                    </div>
                </div>
                <br>
                <br>
            </div>
            <br>
            <br>
            <br>
            <br>
        </div>
    </div>
    <jsp:include page="/WEB-INF/includes/footer.jsp" /> 
    <!-- <script>
    function confirmDelete(){
    	return confirm("Are you sure you want to delete this item?");
    }</script> -->  
</body>

</html>