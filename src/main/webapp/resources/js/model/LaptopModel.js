var rootUrl = "http://localhost:8080/cormacLaptops/rest/laptops/";

var Laptop = Backbone.Model.extend({
	urlRoot : rootUrl,
	defaults : {
		"id" : null,
		"make" : "",
		"model" : "",
		"price" : null,
		"ram" : null,
		"hardDrive" : null,
		"processor" : "",
		"description" : ""
	},
	initialize : function() {
		console.log("Laptop initialized");
	}
});

var SearchLaptops = Backbone.Collection.extend({}, {
	search : function(query, options) {

		var search = $.Deferred();

		var collection = new this([], options);

		collection.url = _.result(collection, 'url') + query; // QUERY URL Binds here
		var fetch = collection.fetch({
			data : {
				q : query
			}
		});

		fetch.done(_.bind(function() {
			search.resolveWith(this, [ collection ]);
		}, this));

		fetch.fail(function() {
			search.reject();
		});

		return search.promise();
	}
});

var LaptopsMakeSearch = SearchLaptops.extend({
	url : rootUrl + "search?make="
});

var LaptopsRam = SearchLaptops.extend({
	url : rootUrl + "ram/"
});

var LaptopsList = Backbone.Collection.extend({
	model : Laptop
});