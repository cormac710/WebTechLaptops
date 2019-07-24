var rootUrl = "http://localhost:8080/cormacLaptops/rest/laptops/";
var laptopViewedId;

function listTheLaptops(){
	$('#laptopList').empty();
	// note min & max for the slider are declared in the slider code
	// and change as the slider changes
	var findLaptopsById = LaptopsRam.search(min+"/"+max);
	findLaptopsById.done(function(laptops){
		var tblView = new MainView({
			collection: laptops
		});
		tblView.$el.appendTo(document.body);

		$('#searchTbl').css('visibility', 'visible');
		$('#searchTbl').DataTable();
	})
}

function searchByMake(){
	$('#laptopList').empty();
	$('#searchTbl').show();
	var laptopModel = $('#makeSearch').val();
	var findLaptops = LaptopsMakeSearch.search(laptopModel);
	findLaptops.done(function(laptops){
		var tblView = new MainView({
			collection: laptops
		});
		tblView.$el.appendTo(document.body);

		$('#searchTbl').css('visibility', 'visible');
		$('#searchTbl').DataTable();
	})
}

function viewLaptopDetails(){
	console.log(laptopViewedId);
	getById(laptopViewedId);
}

var getById = function(laptopId) {
	
	laptopViewedId = laptopId;
	var laptop = new Laptop({ id : laptopId })
	laptop.fetch({ 
		success: function(laptop){
			$('.modal-content').html(new DetailsView({model:laptop}).renderModal().el);

			new DetailsView().renderDropzone();
		}
	});
}

function deleteLaptop(id) {
	var laptop = new Laptop({id: id});
	console.log("Destroying....")
	laptop.destroy({
		success: function(laptop){
			$('#rowLaptop' + id).remove();
			$('#rowLaptopRam' + id).remove();

			toastr.warning("Successfully Deleted!!");
		}
	});
	removeTabaleElements();
	findAll();
}

var findAll = function() {
	$.ajax({
		type : "GET",
		url : rootUrl,
		dataType : "json",
		success : renderLaptopsTable
	});
}

var renderLaptopsTable = function(data) {
	$.each(data,function(index, laptop) {
						$('#laptopsTbl tbody')
								.append("<tr id='rowLaptop"+ laptop.id+ "'>"
										+ "<td>"+ laptop.make+ "</td>"
										+ "<td>"+ laptop.model+ "</td>"
										+ "<td>"+ laptop.ram+ "</td>"
										+ "<td>&euro;"+ laptop.price+ "</td>"
										+ "<td><a id='aDelete' class='btn btn-danger' onclick='deleteLaptop("+ laptop.id
												+ ")' >Delete</a></td>"
										+ "<td><a id='aModal' class='btn btn-info' data-toggle='modal' href='#myModal' onclick='getById("
											+ laptop.id+ ")' >More Info</a></td>"
									+ "</tr>")
					});
	$('#laptopsTbl').DataTable();
}

var showForm = function(){
	$('#addLaptopForm').html(new DetailsView().renderAddLaptop().el);
}