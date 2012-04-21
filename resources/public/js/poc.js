$(function(){
    var Spiral = {};

    Spiral.Model = Backbone.Model.extend({});

    Spiral.ModelCollection = Backbone.Collection.extend({
        url: "/models"
    });

    Spiral.SelectedModelBeacon = _.extend({}, Backbone.Events);
    Spiral.SelectedModel = null;

    Spiral.AllModels = new Spiral.ModelCollection();
    
    Spiral.MView = Backbone.View.extend({
        render: function() {
            this.$el.html(this.template(this.model.toJSON()));
            this.$el.data('backbone-model', this.model);
            this.postRender();
            return this;
        },
        postRender: $.noop
    });

    Spiral.ModelLiView = Spiral.MView.extend({
        tagName: "li",
        className: "model",
        template: _.template($('#model-li-tmpl').html())
    });
    

    Spiral.ModelDetails = Spiral.MView.extend({
        tagName: "div",
        className: "model-details",
        template: _.template($('#model-details-tmpl').html())
    });

    Spiral.ModelDetailsContainer = Backbone.View.extend({
        el: $("#model-details-container"),
        initialize: function() {
            Spiral.SelectedModelBeacon.bind('change', this.render, this);
        },
        render: function() {
            var v = new Spiral.ModelDetails({model: Spiral.SelectedModel});
            this.$el.html(v.render().el);
        }
    });

    Spiral.ModelList = Backbone.View.extend({
        initialize: function() {
            this.model_list = this.$("#model-list");
            Spiral.AllModels.bind('all', this.render, this);
        },
        el: $("#model-list-container"),
        render: function() {
            var self = this;
            Spiral.AllModels.each(function(model){
                var v = new Spiral.ModelLiView({model: model});
                self.model_list.append(v.render().el);
            });
        },
        events: {
            'click .model': 'showModelDetails'
        },
        showModelDetails: function(e){
            var model = $(e.target).data('backbone-model');
            Spiral.SelectedModel = model;
            Spiral.SelectedModelBeacon.trigger('change');
        }
    });
    
    //init
    new Spiral.ModelList();
    new Spiral.ModelDetailsContainer();
    Spiral.AllModels.fetch();

    window.Spiral = Spiral;
});
