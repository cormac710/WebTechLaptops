var MainView = Backbone.View.extend({
	collection : LaptopsList,
	initialize : function() {
		this.listenTo(this.collection, 'add', this.renderList);
		this.render();
	},

	render : function() {
		this.collection.each(function(laptop) {
			$('#laptopList').append(new LaptopView({
				model : laptop
			}).render());
		},

		this);
	}
});