function eventOnSelect(limit) {
    $(".selections").on("click", function () {
        var select = $(this);
        var len = $("input[name=optionsCheckbox]:checked").length;
        if (limit != -1) {
            if (len > limit) {
                alert(": ( Out of select limit (" + limit + ")");
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
    $("#submitButton").html("<i class=\"fa fa-circle-o-notch fa-spin\"></i> Please wait");
}

function successful() {
    $("#submitButton").html("<i class=\"fa fa-check\"></i> Thank you : )")
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
    $(".alert").html("<br><div class=\"alert alert-warning\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a><strong>Going wrong！</strong>You must select at least 1 option.</div>")
    $("#submitButton").html("<i class=\"fa fa-rotate-right\"></i> Retry");
}

function connectionRefused() {
    $(".alert").html("<br><div class=\"alert alert-danger\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a><strong>Sorry, try again！</strong>Connection refused.</div>")
    $("#submitButton").html("<i class=\"fa fa-rotate-right\"></i> Retry");
}

function only1Time() {
    $(".alert").html("<br><div class=\"alert alert-danger\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a><strong>Sorry, You can't vote for this anymore！</strong>You can just vote for 1 time.</div>")
    $("#submitButton").html("<i class=\"fa fa-rotate-right\"></i> Retry");
}

function invalidRule() {
    $(".alert").html("<br><div class=\"alert alert-danger\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a><strong>Sorry, try again！</strong>Invalid vote rule. (R U Hacking?)</div>")
    $("#submitButton").html("<i class=\"fa fa-rotate-right\"></i> Retry");
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