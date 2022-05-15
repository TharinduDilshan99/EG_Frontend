<%@page import="com.Bill"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Billing Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<link rel="stylesheet" href="Views/researches.css">
<script src="Components/jquery-3.2.1.js"></script>
<script src="Components/billscals.js"></script>

</head>
<body>

 <div class="container">
 <div class="row">
 <div class="col-6">

<h1>BillsCals Management</h1>
<form id="formBill" name="formBill" method="post" enctype="multipart/form-data">
	 User Name:
	<input id="UserName" name="UserName" type="text" class="form-control form-control-sm">
	<br> User Address:
	<input id="UserAddress" name="UserAddress" type="text" class="form-control form-control-sm">
	<br> Unit Count:
	<input id="UnitCount" name="UnitCount" type="text" class="form-control form-control-sm">
	<br> Bill Amount:
	<input id="BillAmount" name="BillAmount" type="text" class="form-control form-control-sm">
	<br> Due Amount:
	<input id="DueAmount" name="DueAmount" type="text" class="form-control form-control-sm">
	<br> Date:
	<input id="Date" name="Date" type="text" class="form-control form-control-sm">
	<br>
	<br>
	<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
	<input type="hidden" id="hidBillIDSave" name="hidBillIDSave" value="">
</form>
<br>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>

<br>
<div id="divBillGrid">
	 <%
	 	Bill bill = new Bill();
	 	out.print(bill.readBill());
	 %>
</div>

</div>
</div>
</div>
</body>
</html>

