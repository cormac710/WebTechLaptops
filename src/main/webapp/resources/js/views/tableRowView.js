var LaptopView = Backbone.View.extend({
  model: Laptop,
  tagName:'tr',
  className:"",

  render:function(){
		 var template= _.template($('#tblRow').html(), this.model.toJSON());
		return this.$el.html(template);
  }
});
