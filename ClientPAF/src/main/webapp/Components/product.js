/**
 * 
 */
 $(document).ready(function()
{
if ($("#alertSuccess").text().trim() == "")
 {
 $("#alertSuccess").hide();
 }
 $("#alertError").hide();
});
 
 
 // SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateProductForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	$.ajax(
		{
			url: "ProductsAPI",
			type: type,
			data: $("#formProduct").serialize(),
			dataType: "text",
			complete: function(response, status) {
				onProductSaveComplete(response.responseText, status);
			}
		});
});
		
		function onProductSaveComplete(response, status) {

	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		}

		else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidItemIDSave").val("");
	$("#formProduct")[0].reset();

}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidItemIDSave").val($(this).closest("tr").find('#hidItemIDUpdate').val());
	$("#ProductCode").val($(this).closest("tr").find('td:eq(0)').text());
	$("#ProductCategory").val($(this).closest("tr").find('td:eq(1)').text());
	$("#ProductName").val($(this).closest("tr").find('td:eq(2)').text());
	$("#ProductPrice").val($(this).closest("tr").find('td:eq(3)').text());
	$("#ProductDesc").val($(this).closest("tr").find('td:eq(4)').text());
});



// CLIENT-MODEL================================================================
function validateProductForm() {
	// CODE
	if ($("#ProductCode").val().trim() == "") {
		return "Insert Product Code.";
	}
	// Category
	if ($("#ProductCategory").val().trim() == "") {
		return "Insert Product Category.";
	}
	
	// NAME
	if ($("#ProductName").val().trim() == "") {
		return "Insert Product Name.";
	}



	// PRICE-------------------------------
	if ($("#ProductPrice").val().trim() == "") {
		return "Insert Product Price.";
	}


	// is numerical value
	var tmpPrice = $("#ProductPrice").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Product Price.";
	}



	// convert to decimal price
	$("#ProductPrice").val(parseFloat(tmpPrice).toFixed(2));



	// DESCRIPTION------------------------
	if ($("#ProductDesc").val().trim() == "") {
		return "Insert Product Description.";
	}
	return true;
}


///REMOVE============================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax(
		{
			url: "ProductsAPI",
			type: "DELETE",
			data: "ProductID=" + $(this).data("productid"),
			dataType: "text",
			complete: function(response, status) {
				onProductDeleteComplete(response.responseText, status);
			}


		});

});


function onProductDeleteComplete(response, status) {

	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		}

		else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	}

	else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	}

	else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();

	}

}

 