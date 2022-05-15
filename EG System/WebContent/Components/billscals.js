$(document).ready(function()
{
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	 $("#alertSuccess").text("");
	 $("#alertSuccess").hide();
	 $("#alertError").text("");
	 $("#alertError").hide(); 
	 
	// Form validation-------------------
	 var status = validateBillForm();
	 if (status != true)
		 {
		  $("#alertError").text(status);
		  $("#alertError").show();
		  return;
	 }
	 //If status equals to true
	 var type = ($("#hidBillIDSave").val() == "") ? "POST" : "PUT";
	 var formData = new FormData($("#formBill")[0]);
	 console.log(formData);
	 $.ajax(
	 {
		 url : "BillAPI",
		 type : type,
		 data : formData,
		 enctype : "multipart/form-data",
		 complete : function(response, status)
		 {
			 onBillSaveComplete(response.responseText, status);
		 },
		 processData : false,
		 contentType :false
	 }); 
});

function onBillSaveComplete(response, status)
{
	if (status == "success")
	 {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divBillGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
		}
	 } else if (status == "error")
	 {
			 $("#alertError").text("Error while saving.");
			 $("#alertError").show();
	 } else
	 {
			 $("#alertError").text("Unknown error while saving..");
			 $("#alertError").show();
	 } 
	
	 $("#hidBillIDSave").val("");
	 $("#formBill")[0].reset();
}

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	 $("#hidBillIDSave").val($(this).data("billid")); 
	 $("#UserName").val($(this).closest("tr").find('td:eq(0)').text());
	 $("#UserAddress").val($(this).closest("tr").find('td:eq(1)').text());
	 $("#UnitCount").val($(this).closest("tr").find("td:eq(2)").text());
	 $("#BillAmount").val($(this).closest("tr").find('td:eq(3)').text());
	 $("#DueAmount").val($(this).closest("tr").find('td:eq(4)').text());
	 $("#Date").val($(this).closest("tr").find('td:eq(5)').text());
});

//client-model
function validateBillForm()
{
	// UserName
	if ($("#UserName").val().trim() == "")
	{
		return "Insert UserName.";
	}
	// UserAddress
	if ($("#UserAddress").val().trim() == "")
	{
		return "Insert UserAddress.";
	}
	// UnitCount
	var UnitCount = $("#UnitCount").val().trim();
	if(UnitCount == ""){
		return "Insert UnitCount.";
	}
	if (!$.isNumeric(UnitCount))
	{
		return "Insert a numerical value for UnitCount.";
	}
	// BillAmount
	if ($("#BillAmount").val().trim() == "")
	{
		return "Insert BillAmount.";
	}
	// DueAmount
	if ($("#DueAmount").val().trim() == "")
	{
		return "Insert DueAmount.";
	}	
	// Date
	if($("#Date").val().trim() == "")
	{
		return "Insert Date.";
	}
	
	return true;
}

$(document).on("click", ".btnRemove", function(event)
{
		$.ajax(
		 {
			 url : "BillAPI",
			 type : "DELETE",
			 data : "billID=" + $(this).data("billid"),
			 dataType : "text",
			 complete : function(response, status)
			 {
				 onBillDeleteComplete(response.responseText, status);
			 }
		 });
});


function onBillDeleteComplete(response, status)
{
	if (status == "success")
	 {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			 $("#alertSuccess").text("Successfully deleted.");
			 $("#alertSuccess").show();
			 $("#divBillGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
		}
	 } else if (status == "error")
	 {
			 $("#alertError").text("Error while deleting.");
			 $("#alertError").show();
	 } else
	 {
			 $("#alertError").text("Unknown error while deleting..");
			 $("#alertError").show();
	 }
}


