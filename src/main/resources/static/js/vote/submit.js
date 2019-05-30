function submitVote(choice) {
    if (choice == 1) {
        var result = $("input[name='optionsRadios']:checked").attr("id");
        if (result != undefined) {
            $(".alert").html("");
            $("#submitButton").html("<i class=\"fa fa-circle-o-notch fa-spin\"></i> Please wait");
            setTimeout(function () {
                $("#submitButton").html("<i class=\"fa fa-check\"></i> Thank you :)")
                $("#submitButton").removeClass("btn-default");
                $("#submitButton").addClass("btn-success");
                $("#submitButton").removeAttr("onclick");
            }, 2000);
        } else {
            $(".alert").html("<br><div class=\"alert alert-warning\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a><strong>Going wrong！</strong>You must select at least 1 option.</div>")
        }
    } else if (choice == 0) {
        var obj = document.getElementsByName("optionsCheckbox");
        var check_val = [];
        for(var i in obj){
            if(obj[i].checked)
                check_val.push(obj[i].getAttribute("id"));
        }
        alert(check_val);
    }
}