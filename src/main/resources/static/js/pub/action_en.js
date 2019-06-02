function submitVote() {
    clearAllError();
    setTimeout(function () {
        if (checkAll()) {
            //Build serial
            var build = "";
            for (var i = 1; i <= options; ++i) {
                var num = i;
                var count = 0;
                var selectionText = $("#option" + i).val();
                build += "<%" + num + "<%" + count + "<%" + selectionText;
            }
            params = {};
            params.title = $("#voteTitle").val();
            params.describe = $("#voteDescribe").val();
            params.selection = build;
            params.type = mode;
            params.limit = $("#modeInput").val();
            if (mode == 1) {
                params.limit = -1;
            }
            $.ajax({
                url: "/voterSubmit",
                data: params,
                success: function (data) {
                    setTimeout(function () {
                        if (data == -7426) {
                            $(".isError").html("<font color='red'>Invalid input :( </font>Please check.");
                        } else {
                            location.href = "/vote/en/" + data;
                        }
                    }, 1000);
                }
            });
        } else {
            $(".isError").html("<font color='red'>Invalid input :( </font>Please check.");
        }
    }, 800);
}

var mode = 0;

function singleElection() {
    mode = 1;
    $("#mode").html("Single election <span class=\"caret\"></span>");
    $("#modeInput").attr("disabled", "");
    $("#modeInput").val("-1");
}

function multipleSelection() {
    mode = 0;
    $("#mode").html("Multiple selection <span class=\"caret\"></span>");
    $("#modeInput").removeAttr("disabled");
}

var options = 3;

function addOption() {
    $("#close" + options).remove();
    ++options;
    var append = "" +
        "    <div class=\"option" + options + "\">\n" +
        "        <div class=\"input-group\">\n" +
        "            <span class=\"input-group-addon\">Option " + options + "</span>\n" +
        "            <input class=\"form-control\" id=\"option" + options + "\" type=\"text\">\n" +
        "            <div class=\"cls\"></div>\n" +
        "            <span class=\"input-group-btn\" id=\"close" + options + "\">\n" +
        "                <button class=\"btn btn-default\" type=\"button\" onclick=\"delOption()\">\n" +
        "                    <i class=\"fa fa-close\"></i>\n" +
        "                </button>\n" +
        "            </span>\n" +
        "        </div>\n" +
        "        <br>\n" +
        "    </div>";
    $(".add").append(append);
}

function delOption() {
    $(".option" + options).remove();
    --options;
    $(".option" + options + " .cls").after("" +
        "            <span class=\"input-group-btn\" id=\"close" + options + "\">\n" +
        "                <button class=\"btn btn-default\" type=\"button\" onclick=\"delOption()\">\n" +
        "                    <i class=\"fa fa-close\"></i>\n" +
        "                </button>\n" +
        "            </span>");
}

//Maximum limit check
$("#modeInput").on("input propertychange", function () {
    var input = Number($("#modeInput").val());
    if (isNaN(input)) {
        errorS();
    } else {
        if (input <= options) {
            successS();
        } else {
            errorS();
        }
    }
});

function errorS() {
    $("#status").removeClass("has-success");
    $("#status").addClass("has-error");
}

function successS() {
    $("#status").removeClass("has-error");
    $("#status").addClass("has-success");
}

//All input check
function checkAll() {
    var allDone = true;
    //Options check
    for (var i = 1; i <= options; ++i) {
        var selectionText = $("#option" + i).val();
        if (selectionText == "") {
            allDone = false;
            $(".option" + i).addClass("has-error");
        }
    }
    //Build params
    params = {};
    params.title = $("#voteTitle").val();
    params.describe = $("#voteDescribe").val();
    params.type = mode;
    params.limit = $("#modeInput").val();
    //Verify
    if (params.title == "") {
        allDone = false;
        $("#borderTitle").addClass("has-error");
    }
    if (params.describe == "") {
        allDone = false;
        $("#borderDescribe").addClass("has-error");
    }
    //Limit
    if ($("#modeInput").val() != "") {
        if (!isNaN(Number($("#modeInput").val()))) {
            if (Number($("#modeInput").val()) > options) {
                allDone = false;
                errorS();
            }
        } else {
            allDone = false;
            errorS();
        }
    } else {
        allDone = false;
        errorS();
    }
    return allDone;
}

function clearAllError() {
    for (var i = 1; i <= options; ++i) {
        $(".option" + i).removeClass("has-error");
    }
    $("#borderTitle").removeClass("has-error");
    $("#borderDescribe").removeClass("has-error");
    $("#status").removeClass("has-error");
    $(".isError").html("");
}