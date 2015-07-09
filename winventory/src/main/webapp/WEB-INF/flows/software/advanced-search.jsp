<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

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

<!-- Styles the dual-entry form (date purchased and expiration date) -->
<style>
.double-input .form-control { 
    width: 50%;
    border-right-width: 1px; 
} 
.double-input .form-control:focus {
    border-right-width: 1px; 
}
.input-group-addon {
    min-width:200px;
    text-align:center;
}
</style>
</head>

<body>
    <jsp:include page="/base.jsp" />
    <div class="container-fluid">
        <div class="row">
            <jsp:include page="swBase.jsp" />
            <div class="col-md-8">
                
                
                <div class="boom">
                        <h2 class="center">Software Advanced Search</h2>
                    </div>
                    <div class="padme">                     
                        <form action="search" method="post">
                            <div class="form-control-static">
                                <div id="idContainer" class="myClass">
                                    <div class="input-group" id="idDiv">
                                        <span class="input-group-addon" id="basic-addon1">Name</span>
                                        <input name="id[]" type="text" class="form-control"
                                            placeholder="Microsoft Word"> <span class="input-group-btn"
                                            aria-hidden="true">
                                            <button class="btn btn-default"
                                                onclick="addInput('idContainer');return false;">
                                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                            </button> <br>
                                        </span>
                                    </div>
                                </div>
                                <br>
                                <div id="idContainer2" class="myClass">
                                    <div class="input-group" id="idDiv2">
                                        <span class="input-group-addon" id="basic-addon1">Serial Number</span>
                                        <input name="id[]" type="text" class="form-control"
                                            placeholder="JFDKN889238SJDKLQLM"> <span
                                            class="input-group-btn" aria-hidden="true">
                                            <button class="btn btn-default"
                                                onclick="addInput2('idContainer2');return false;">
                                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                            </button> <br>
                                        </span>
                                    </div>
                                </div>
                                <br>
                                <div id="idContainer3" class="myClass">
                                    <div class="input-group" id="idDiv3">
                                        <span class="input-group-addon" id="basic-addon1">License Key</span>
                                        <input name="id[]" type="text" class="form-control"
                                            placeholder="JS92-KLSM-298M-MW21"> <span class="input-group-btn"
                                            aria-hidden="true">
                                            <button class="btn btn-default"
                                                onclick="addInput3('idContainer3');return false;">
                                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                            </button> <br>
                                        </span>
                                    </div>
                                </div>
                                <br>
                                <div id="idContainer4" class="myClass">
                                    <div class="input-group" id="idDiv4">
                                        <span class="input-group-addon" id="basic-addon1">Version</span>
                                        <input name="id[]" type="text" class="form-control" placeholder="Professional 2013"> 
                                        <span class="input-group-btn" aria-hidden="true">
                                            <button class="btn btn-default"
                                                onclick="addInput4('idContainer4');return false;">
                                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                            </button> <br>
                                        </span>
                                    </div>
                                </div>
                                <br>
                                <div id="idContainer5" class="myClass">
                                    <div class="input-group" id="idDiv5">
                                        <span class="input-group-addon" id="basic-addon1">Cost $</span>
                                            <input name="id[]" type="number" class="form-control"
                                            placeholder="89.99"> <span class="input-group-btn"
                                            aria-hidden="true">
                                            <button class="btn btn-default"
                                                onclick="addInput6('idContainer5');return false;">
                                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                            </button> <br>
                                        </span>
                                    </div>
                                </div>
                                <br>
							<div id="idContainer6" class="myClass">
                                <div class="input-group double-input">
                                    <span class="input-group-addon" id="basic-addon1">Date Purchased Range</span>
                                           <input name="id[]" type="date" class="form-control"
                                                    placeholder="YYYY-MM-DD">
                                           <input name="id[]" type="date" class="form-control"
                                                    placeholder="YYYY-MM-DD">
                                    </div>
                            </div>
                            <br>
                            <div id="idContainer7" class="myClass">
                                <div class="input-group double-input">
                                    <span class="input-group-addon" id="basic-addon1">Expiration Date Range</span>
                                           <input name="id[]" type="date" class="form-control"
                                                    placeholder="YYYY-MM-DD">
                                           <input name="id[]" type="date" class="form-control"
                                                    placeholder="YYYY-MM-DD">
                                    </div>
                            </div>
                            <br>
                                <input type="hidden" name="action" value="doInsert">
                                <input type="hidden" name="table" value="property">
                            </div>
                            <button type="submit" class="btn btn-default">Search</button>
                            <button type="reset" class="btn btn-default">Clear</button>
                        </form>
                    </div>
                </div>
                <br> <br>
            </div>
            <br> <br> <br> <br>
        </div>
    </div>
    <script>
        function addInput(divName) {
            var newDiv = document.createElement('div');
            newDiv.innerHTML = "<br><div class=\"input-group extra-box\" id=\"idDiv\">"
                    + "<input name=\"id[]\" type=\"text\" class=\"form-control\">"
                    + "</div>";
            document.getElementById(divName).appendChild(newDiv);
        }
        function addInput2(divName) {
            var newDiv = document.createElement('div');
            newDiv.innerHTML = "<br><div class=\"input-group extra-box\" id=\"idDiv2\">"
                    + "<input name=\"id[]\" type=\"text\" class=\"form-control\">"
                    + "</div>";
            document.getElementById(divName).appendChild(newDiv);
        }
        function addInput3(divName) {
            var newDiv = document.createElement('div');
            newDiv.innerHTML = "<br><div class=\"input-group extra-box\" id=\"idDiv3\">"
                    + "<input name=\"id[]\" type=\"text\" class=\"form-control\">"
                    + "</div>";
            document.getElementById(divName).appendChild(newDiv);
        }
        function addInput4(divName) {
            var newDiv = document.createElement('div');
            newDiv.innerHTML = "<br><div class=\"input-group extra-box\" id=\"idDiv4\">"
                    + "<input name=\"id[]\" type=\"date\" class=\"form-control\">"
                    + "</div>";
            document.getElementById(divName).appendChild(newDiv);
        }
        function addInput5(divName) {
            var newDiv = document.createElement('div');
            newDiv.innerHTML = "<br><div class=\"input-group extra-box\" id=\"idDiv5\">"
                    + "<input name=\"id[]\" type=\"text\" class=\"form-control\">"
                    + "</div>";
            document.getElementById(divName).appendChild(newDiv);
        }
        /* function addInput5(divName) {
            var newDiv = document.createElement('div');
            ArrayList<RefCondition> conditions = (ArrayList<RefCondition>) request.getAttribute("conditions");
                int i = 0;
                newDiv.innerHTML = "<br><div class=\"input-group extra-box\" id=\"idDiv5\">"
                    + "<select name=\"id[]\" class=\"form-control\">";
                for (i = 0; i < conditions.size(); i++) { 
                    newDiv.innerHTML = newDiv.innerHTML + "<option>" + conditions.get(i).getCode() + "</option>";   
                }
                newDiv.innerHTML = newDiv.innerHTML + "</select></div>";
            
            document.getElementById(divName).appendChild(newDiv);
        } */
        function addInput6(divName) {
            var newDiv = document.createElement('div');
            newDiv.innerHTML = "<br><div class=\"input-group extra-box\" id=\"idDiv6\">"
                    + "<input name=\"id[]\" type=\"text\" class=\"form-control\">"
                    + "</div>";
            document.getElementById(divName).appendChild(newDiv);
        }
    </script>
    <br>
    <br>
    <br>
    <br>
                
                
                
                
                
                
<!--                 <div class="main"> -->
<!--                     <h1 class="center">Advanced Search For Applications</h1> -->
<!--                     <h4 class="center">Scan barcode now or enter manually</h4> -->
<!--                     <form action="search" method="post"> -->
<!--                         <div class="form-control-static"> -->
<!--                             <div id="idContainer" class="myClass"> -->
<!--                                 <div class="input-group" id="nameDiv"> -->
<!--                                     <span class="input-group-addon" id="basic-addon1">Name</span> <input -->
<!--                                         name="name" type="text" class="form-control" -->
<!--                                         placeholder="Microsoft Office"> <span -->
<!--                                         class="input-group-btn" aria-hidden="true"> -->
<!--                                         <button class="btn btn-default" -->
<!--                                             onclick="addInput('nameContainer');return false;"> -->
<!--                                             <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> -->
<!--                                         </button> <br> -->
<!--                                     </span> -->
<!--                                 </div> -->
<!--                             </div> -->
<!--                             <br> -->
<!--                             <div class="input-group" id="serialNoDiv"> -->
<!--                                 <span class="input-group-addon" id="basic-addon1">Serial -->
<!--                                     Number</span> <input name="serialNo" type="text" -->
<!--                                     class="form-control" placeholder="JKDK89802N904"> <span -->
<!--                                     class="input-group-btn" aria-hidden="true"> -->
<!--                                     <button class="btn btn-default" -->
<!--                                         onclick="addInput('serialNoDiv');return false;"> -->
<!--                                         <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> -->
<!--                                     </button> <br> -->
<!--                                 </span> -->
<!--                             </div> -->
<!--                             <br> -->
<!--                             <div class="input-group" id="versionDiv"> -->
<!--                                 <span class="input-group-addon" id="basic-addon1">Version</span> -->
<!--                                 <input name="version" type="text" class="form-control" -->
<!--                                     placeholder="3.2.4"> <span class="input-group-btn" -->
<!--                                     aria-hidden="true"> -->
<!--                                     <button class="btn btn-default" -->
<!--                                         onclick="addInput('versionDiv');return false;"> -->
<!--                                         <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> -->
<!--                                     </button> <br> -->
<!--                                 </span> -->
<!--                             </div> -->
<!--                             <br> -->
<!--                             <div class="input-group" id="licenseKeyDiv"> -->
<!--                                 <span class="input-group-addon" id="basic-addon1">License -->
<!--                                     Key</span> <input name="licenseKey" type="text" class="form-control" -->
<!--                                     placeholder="98034782340983"> <span -->
<!--                                     class="input-group-btn" aria-hidden="true"> -->
<!--                                     <button class="btn btn-default" -->
<!--                                         onclick="addInput('licenseKeyDiv');return false;"> -->
<!--                                         <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> -->
<!--                                     </button> <br> -->
<!--                                 </span> -->
<!--                             </div> -->
<!--                             <br> -->
<!--                             <div class="input-group" id="costDiv"> -->
<!--                                 <span class="input-group-addon" id="basic-addon1">Cost $</span> -->
<!--                                 <input name="cost" type="text" class="form-control" -->
<!--                                     placeholder="89.99"> <span class="input-group-btn" -->
<!--                                     aria-hidden="true"> -->
<!--                                     <button class="btn btn-default" -->
<!--                                         onclick="addInput('costDiv');return false;"> -->
<!--                                         <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> -->
<!--                                     </button> <br> -->
<!--                                 </span> -->
<!--                             </div> -->
<!--                             <br> -->
<!--                             <div class="input-group double-input" id="purchaseDateDiv"> -->
<!--                                 <span class="input-group-addon" id="basic-addon1">DatePurchased</span> -->
<!--                                 <input -->
<!--                                     name="purchaseDateStart" type="date" class="form-control" -->
<!--                                     placeholder="YYYY/MM/DD"> -->
<!--                                 <input -->
<!--                                     name="purchaseDateEnd" type="date" class="form-control" -->
<!--                                     placeholder="YYYY/MM/DD"> -->
                                    
<!--                                 <span class="input-group-btn"> -->
<!--                                         <button class="btn btn-default" type="button" onClick="showHelp()"> -->
<!--                                             <span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> -->
<!--                                         </button><br> -->
<!--                                 </span>  -->
<!--                             </div> -->
<!--                             <br> -->
<!--                             <div class="input-group double-input" id="expirationDateDiv"> -->
<!--                                 <span class="input-group-addon" id="basic-addon1">Expiration Date</span> -->
<!--                                 <input -->
<!--                                     name="expirationDateStart" type="date" class="form-control" -->
<!--                                     placeholder="YYYY/MM/DD"> -->
<!--                                 <input -->
<!--                                     name="expirationDateEnd" type="date" class="form-control" -->
<!--                                     placeholder="YYYY/MM/DD"> -->
                                    
<!--                                 <span class="input-group-btn"> -->
<!--                                         <button class="btn btn-default" type="button" onClick="showHelp()"> -->
<!--                                             <span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> -->
<!--                                         </button><br> -->
<!--                                 </span>  -->
<!--                             </div> -->
<!--                             <br> -->
                            
<!--                             <input type="hidden" name="action" value="doInsert"> -->
<!--                             <input type="hidden" name="table" value="property"> -->
<!--                         </div> -->
<!--                         <button type="submit" class="btn btn-default">Search</button> -->
<!--                         <button type="reset" class="btn btn-default">Clear</button> -->
<!--                     </form> -->
<!--                 </div> -->
<!--                 <br> <br> -->
<!--             </div> -->
<!--             <br> <br> <br> <br> -->
<!--         </div> -->
<!--     </div> -->
<!--     <script> -->
<!-- //             function addInput(divName) {
//                 var newDiv = document.createElement('div');
//                 newDiv.innerHTML = "<br><div class=\"input-group extra-box\" id=\"idDiv\">" +
//                     "<input name=\"id[]\" type=\"text\" class=\"form-control\">" +
//                     "</div>";
//                 // TODO 
//                 // FIX INDEXING PROBLEM OF ARRAY
//                 document.getElementById(divName).appendChild(newDiv);
//             } -->
<!--         </script> -->
<!--     <br> -->
<!--     <br> -->
<!--     <br> -->
<!--     <br> -->
  <!--   <div class="top-header">
        <div class="center">
            <br> <br>
            <h4>SimonComputing &copy; 2015</h4>
            <br> <br>
        </div>
    </div> -->
    	<jsp:include page="/WEB-INF/includes/footer.jsp" />
    
    
    
    <script>
    function showHelp(){
        alert("Not sure about the exact date?\nSearch by a range of dates by entering the earlier "
            + "date in the first box and the more recent date in the second box.\nDates entered "
            + "are inclusive.");
    }
    </script>
    
</body>

</html>