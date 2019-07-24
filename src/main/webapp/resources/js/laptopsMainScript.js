var editingOn = 0;

var searchByRamRange = function() {
	$('#laptopsRamTbl tbody').empty();
	var ramRangeUrl = rootUrl;
	ramRangeUrl += "ram?minRam=" + min + "&maxRam=" + max;
	$.ajax({
		type : "GET",
		url : ramRangeUrl,
		dataType : "json",
		success : renderLaptopsRamTable
	});
}

$(function() {
	loadSlider();
	findAll();

	$('#btnDisable').click(function() {
		console.log(editingOn);

		return false;
	});

});

function enableDisableTextboxes() {
	if (editingOn == 0) {
		enableModalTextBoxs();
		editingOn = 1;
	} else {
		disableModalTextBoxs();
		editingOn = 0;
	}
}

var enableModalTextBoxs = function() {
	$('#btnDisable').text("Turn Editing Off");
	$("#modalMake").prop('disabled', false);
	$("#modalModel").prop('disabled', false);
	$("#modalPrice").prop('disabled', false);
	$("#modalRam").prop('disabled', false);
	$("#modalHardDrive").prop('disabled', false);
	$("#modalProcessor").prop('disabled', false);
	$("#modalDescription").prop('disabled', false);
	$("#btnEditLaptop").prop('disabled', false);
}

var disableModalTextBoxs = function() {
	$('#btnDisable').text("Turn Editing On");
	$("#modalMake").prop('disabled', true);
	$("#modalModel").prop('disabled', true);
	$("#modalPrice").prop('disabled', true);
	$("#modalRam").prop('disabled', true);
	$("#modalHardDrive").prop('disabled', true);
	$("#modalProcessor").prop('disabled', true);
	$("#modalDescription").prop('disabled', true);
	$("#btnEditLaptop").prop('disabled', true);
}

var min = 6;
var max = 8;

var loadSlider = function() {
	$("#slider-range").slider(
			{
				range : true,
				min : 0,
				max : 16,
				values : [ 6, 8 ],
				slide : function(event, ui) {
					$("#amount").val(
							"Min " + ui.values[0] + " -  Max " + ui.values[1]);
				},
				stop : function(event, ui) {
					min = ui.values[0];
					max = ui.values[1];
				}
			});

	$("#amount").val(
			"Min " + $("#slider-range").slider("values", 0) + " -  Max "
					+ $("#slider-range").slider("values", 1));
}

var removeTabaleElements = function() {
	$('#laptopsTbl').DataTable().destroy();
	$('#laptopsTbl tbody').empty();
	$('#searchTbl').DataTable().destroy();
	$('#searchTbl tbody').empty();
}

function initMap() {
	google.maps.visualRefresh = true;
	var myLatLng = {
		lat : 53.41793423962402,
		lng : -7.905156609896267
	};
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom : 17,
		center : myLatLng,
		mapTypeId : 'satellite'
	});

	var marker = new google.maps.Marker({
		position : myLatLng,
		map : map,
		title : 'We Are Here!!'
	});
}