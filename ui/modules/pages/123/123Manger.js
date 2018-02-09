layui.define(["request", "hsForm", "hsTable"], function (exports) {
    var request = layui.request;
    var hsForm = layui.hsForm;
    var hsTable = layui.hsTable;
    var template;
    request.get(window.RESOURCE_PATH + "modules/pages/123/123Save.hf", function (json) {
        template = json;
    });

    function init(containerId) {
        hsTable.init("user-table" + new Date().getTime(), containerId, "123", [[
            
            {field: 'name', title: "名称", sort: true},
            
            {field: 'parentId', title: "父级ID", sort: true},
            
            {field: 'permissionId', title: "权限ID", sort: true},
            
            {field: 'path', title: "树编码", sort: true},
            
            {field: 'sortIndex', title: "树编码", sort: true},
            
            {
            type: "toolbar", title: "操作", toolbar: "<script type='text/html'>" +
            "<button lay-event=\"edit\" class='layui-btn layui-btn-sm'><i class=\"layui-icon\">&#xe642;</i>编辑</button>" +
            "</script>"
            }
        ]], {
            btns: [{
                name: '新建',
                callback: function () {
                    hsForm.openForm(template, function (form) {
                        console.log(form);
                        return true;
                    });
                }
            }],
            search: [
            
               {
                    label: '名称',
                    column: 'name',
                    type: 'input'
                },
            
               {
                    label: '父级ID',
                    column: 'parentId',
                    type: 'input'
                },
            
               {
                    label: '权限ID',
                    column: 'permissionId',
                    type: 'input'
                },
            ]
        });

    }

    exports("userManage", {
        init: init
    })
});