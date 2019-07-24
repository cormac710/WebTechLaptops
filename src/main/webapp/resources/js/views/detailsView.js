editingOn = 0;
var DetailsView = Backbone.View.extend({
	model : Laptop,
	initialize : function() {
	},

	events : {
		"click #btnAddLaptop" : "insertLaptop",
		"click #btnEditLaptop" : "update"
	},

	insertLaptop : function(e) {
		if ($("#make").val().length == 0 || $("#model").val().length == 0) {
			alert("Enter Make and Model")
		} else {
			var laptopDetails = {
				"make" : $("#make").val(),
				"model" : $("#model").val(),
				"price" : $("#price").val(),
				"ram" : $("#ram").val(),
				"hardDrive" : $("#hardDrive").val(),
				"processor" : $("#processor").val(),
				"description" : $("#description").val()
			}

			var laptop = new Laptop();
			laptop.save(laptopDetails, {
				success : function(laptop) {
					console.log("inserted " + laptop.get("make"));
					$("#make").val("");
					$("#model").val("");
					$("#price").val("");
					$("#ram").val("");
					$("#hardDrive").val("");
					$("#processor").val("");
					$("#description").val("");
					toastr.success("Laptop Inserted");
				}
			});

			removeTabaleElements();
			findAll();
		}
	},

	update : function(e) {
		var laptopDetails = {
			"id" : $("#modalId").val(),
			"make" : $("#modalMake").val(),
			"model" : $("#modalModel").val(),
			"price" : $("#modalPrice").val(),
			"ram" : $("#modalRam").val(),
			"hardDrive" : $("#modalHardDrive").val(),
			"processor" : $("#modalProcessor").val(),
			"description" : $("#modalDescription").val()
		}

		var laptop = new Laptop();
		laptop.save(laptopDetails, {
			success : function(laptop) {
				console.log("inserted " + laptop.get("make"));
			}
		});

		removeTabaleElements();
		findAll();
	},

	renderAddLaptop : function() {
		var addTemplate = _.template($('#addLaptop').html(), {});
		$(this.el).html(addTemplate);
		console.log("Added Template")
		return this;
	},

	renderImage : function() {
		var addTemplate = _.template($('#imageFigure').html(), this.model
				.toJSON());
		$(this.el).html(addTemplate);
		console.log("Added IMG Template")
		return this;
	},

	renderImageUpload : function() {
		var addTemplate = _.template($('#imageUpload').html(), this.model
				.toJSON());
		$(this.el).html(addTemplate);
		return this;
	},

	renderDropzone : function() {
		this.myAwesomeDropzone = new Dropzone("#my-awesome-dropzone", {
			acceptedFiles : ".jpg",
			maxFiles : 1,
			maxFilesize: 2, // In MB
			init : function() {
				this.on("success", function(file) {
					var newState = -1;
					var myVar = setTimeout(function() {
						if (newState == -1) {
							viewLaptopDetails();
						}
					}, 3000);
				});
			}
		});
	},

	renderModal : function() {
		var templateModal = _.template($('#laptopModal').html(), this.model
				.toJSON());
		$(this.el).html(templateModal);
		return this;
	},

	render : function() {
		var template = _.template($('#wine-details').html(), this.model
				.toJSON());
		return this.$el.html(template);
	}

});