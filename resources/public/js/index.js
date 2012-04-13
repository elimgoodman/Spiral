(function($){
    
    var Views = {};
    var Collections = {};

    var Type = Backbone.Model.extend({
        url: '/type'
    });

    var TypeCollection = Backbone.Collection.extend({
        model: Type,
        url: '/types'
    });
    
    Collections.AllTypes = new TypeCollection();

    var TypeList = Backbone.View.extend({
        initialize: function() {
            Collections.AllTypes.bind('all', this.render, this);
        },
        el: "#all-types",
        render: function() {
            var self = this;
            this.$el.html("");

            Collections.AllTypes.each(function(type){
                self.$el.append("<li>" + type.get("name") + "</li>");
            });
        }
    });

    var FieldForm = Backbone.View.extend({
        template: _.template($('#type-field-tmpl').html()),
        tagName: 'li',
        className: 'type-field',
        render: function() {
            this.$el.html(this.template());
            return this;
        }
    });

    var TypeForm = Backbone.View.extend({
        initialize: function() {
            this.type_fields = $("#type-fields");
        },
        el: "#type-form",
        events: {
            'click .add-field-link': 'addField',
            'click #save-btn': 'saveType'
        },
        addField: function() {
            var field_form = new FieldForm();

            this.type_fields.append(field_form.render().el);
        },
        saveType: function(e) {
            var serialized = this.serialize();
            var type = new Type(serialized);
            type.save(serialized, {
                success: function() {
                    console.log(type);
                }
            });

            e.preventDefault();
        },
        serialize: function() {
            return {
                foo: [
                    {a: "hello"},
                    {c: 2}
                ]
            };
        }
    });

    $(function(){
        Views.TypeForm = new TypeForm();
        Views.TypeList = new TypeList();

        Collections.AllTypes.fetch();
    });

    window.Views = Views;
})(jQuery);


