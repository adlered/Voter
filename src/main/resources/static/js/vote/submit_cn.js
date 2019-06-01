function eventOnSelect(limit) {
    $(".selections").on("click", function () {
        var select = $(this);
        var len = $("input[name=optionsCheckbox]:checked").length;
        if (limit != -1) {
            if (len > limit) {
                alert(": ( 超出最高选择项目限制 (" + limit + ")");
                select.iCheck('uncheck');
            }
        }
    });
}

function submitVote(choice, VID) {
    if (choice == 1) {
        wait();
        var result = $("input[name='optionsRadios']:checked").attr("id");
        if (result != undefined) {
            vote(VID, result);
        } else {
            emptySet();
        }
    } else if (choice == 0) {
        wait();
        var obj = document.getElementsByName("optionsCheckbox");
        var checkBoxes = [];
        for (var i in obj) {
            if (obj[i].checked)
                checkBoxes.push(obj[i].getAttribute("id"));
        }
        if (checkBoxes.length != 0) {
            vote(VID, checkBoxes.toString());
        } else {
            emptySet();
        }
    }
}

function wait() {
    $(".alert").html("");
    $("#submitButton").html("<i class=\"fa fa-circle-o-notch fa-spin\"></i> 稍候");
}

function successful() {
    $("#submitButton").html("<i class=\"fa fa-check\"></i> 谢谢 : )")
    $("#submitButton").removeClass("btn-default");
    $("#submitButton").addClass("btn-success");
    $("#submitButton").removeAttr("onclick");
    $(document.querySelectorAll("#divVote")).remove();
    $(document.querySelectorAll("#divProgress")).removeClass("col-xs-5");
    $(document.querySelectorAll("#divProgress")).addClass("col-xs-6");
    $(document.querySelectorAll("#divSelection")).removeClass("col-xs-5");
    $(document.querySelectorAll("#divSelection")).addClass("col-xs-6");
}

function emptySet() {
    $(".alert").html("<br><div class=\"alert alert-warning\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a><strong>出错了！</strong>你必须选择至少1个选项。</div>")
    $("#submitButton").html("<i class=\"fa fa-rotate-right\"></i> 重试");
}

function connectionRefused() {
    $(".alert").html("<br><div class=\"alert alert-danger\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a><strong>抱歉，请重试！</strong>连接断开。</div>")
    $("#submitButton").html("<i class=\"fa fa-rotate-right\"></i> 重试");
}

function only1Time() {
    $(".alert").html("<br><div class=\"alert alert-danger\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a><strong>抱歉，你不能再为这个活动投票了！</strong>你只能为每个活动投票1次。</div>")
    $("#submitButton").html("<i class=\"fa fa-rotate-right\"></i> 重试");
}

function invalidRule() {
    $(".alert").html("<br><div class=\"alert alert-danger\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a><strong>抱歉，请重试！</strong>错误的发起信息。（你在尝试注入吗？）</div>")
    $("#submitButton").html("<i class=\"fa fa-rotate-right\"></i> 重试");
}

function vote(VID, selected) {
    setTimeout(function () {
        var params = {};
        params.VID = VID;
        params.selected = selected;
        $.ajax({
            url: "/submitVote",
            data: params,
            success: function (data) {
                if (data == 1) {
                    successful();
                    var list = selected.split(",");
                    list.forEach(function (value) {
                        var count = $("#progress" + value + " .count").text();
                        var afterCount = Number(count) + 1;
                        $("#progress" + value + " .count").text(afterCount);
                        var width = $("#progress" + value).width();
                        var afterWidth = Number(width) + 25;
                        $("#progress" + value).width(afterWidth);
                        $("#progress" + value).removeClass("progress-bar-success");
                        $("#progress" + value).addClass("progress-bar-info");
                    });
                } else if (data == 0) {
                    only1Time();
                }
            }
        });
    }, 1000);
}