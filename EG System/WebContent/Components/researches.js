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
	 var status = validateResearchForm();
	 if (status != true)
		 {
		  $("#alertError").text(status);
		  $("#alertError").show();
		  return;
	 }
	 //If status equals to true
	 var type = ($("#hidResearchIDSave").val() == "") ? "POST" : "PUT";
	 var formData = new FormData($("#formResearches")[0]);
	 console.log(formData);
	 $.ajax(
	 {
		 url : "ResearchesAPI",
		 type : type,
		 data : formData,
		 enctype : "multipart/form-data",
		 complete : function(response, status)
		 {
			 onResearchSaveComplete(response.responseText, status);
		 },
		 processData : false,
		 contentType :false
	 }); 
});

function onResearchSaveComplete(response, status)
{
	if (status == "success")
	 {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divResearchesGrid").html(resultSet.data);
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
	
	 $("#hidResearchIDSave").val("");
	 $("#formResearches")[0].reset();
}

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	 $("#hidResearchIDSave").val($(this).data("researchid")); 
	 $("#title").val($(this).closest("tr").find('td:eq(0)').text());
	 $("#author").val($(this).closest("tr").find('td:eq(1)').text());
	 $("#category").val($(this).closest("tr").find("td:eq(2)").text());
	 $("#year").val($(this).closest("tr").find('td:eq(3)').text());
});

//client-model
function validateResearchForm()
{
	// title
	if ($("#title").val().trim() == "")
	{
		return "Insert Title.";
	}
	// author
	if ($("#author").val().trim() == "")
	{
		return "Insert Author.";
	}
	// category
	if ($("#category").val() == "0")
	{
		return "Insert Catergory.";
	}
	// year
	var year = $("#year").val().trim();
	if(year == ""){
		return "Insert Year.";
	}
	if (!$.isNumeric(year))
	{
		return "Insert a numerical value for year.";
	}
	
	// file
	if($("#uplaodFile").val().trim() == "")
	{
		return "Insert Document.";
	}
	
	return true;
}

$(document).on("click", ".btnRemove", function(event)
{
		$.ajax(
		 {
			 url : "ResearchesAPI",
			 type : "DELETE",
			 data : "researchID=" + $(this).data("researchid"),
			 dataType : "text",
			 complete : function(response, status)
			 {
				 onResearchDeleteComplete(response.responseText, status);
			 }
		 });
});


function onResearchDeleteComplete(response, status)
{
	if (status == "success")
	 {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			 $("#alertSuccess").text("Successfully deleted.");
			 $("#alertSuccess").show();
			 $("#divResearchesGrid").html(resultSet.data);
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


