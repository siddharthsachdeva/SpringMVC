$("#sample_editable_1").find("tr").each(function () {
    $(this).find("input[value=Inactive]").parents().eq(1).css("display", "none")
});
$("#sample_editable_2").find("tr").each(function () {
    $(this).find("input[value=Inactive]").parents().eq(1).css("display", "none")
});
$("#sample_editable_3").find("tr").each(function () {
    $(this).find("input[value=Inactive]").parents().eq(1).css("display", "none")
});
$("#sample_editable_1").find("tbody tr:even").each(function () {
    $(this).css("background-color", "#f5f5f5").children().css("background", "none")
});
$("#sample_editable_1").find("tbody tr:odd").each(function () {
    $(this).css("background-color", "#ffffff").children().css("background", "none")
});
$("#sample_editable_2").find("tbody tr:even").each(function () {
    $(this).css("background-color", "#f5f5f5").children().css("background", "none")
});
$("#sample_editable_2").find("tbody tr:odd").each(function () {
    $(this).css("background-color", "#ffffff").children().css("background", "none")
});
$("#sample_editable_3").find("tbody tr:even").each(function () {
    $(this).css("background-color", "#f5f5f5").children().css("background", "none")
});
$("#sample_editable_3").find("tbody tr:odd").each(function () {
    $(this).css("background-color", "#ffffff").children().css("background", "none")
});
var tableId;
var autoSort = true;
var rowArr;
var keySort = [];
$("#ContentPlaceHolder1_btnSearch").click(function () {
    var a = $("#ContentPlaceHolder1_txtSearchPractitioner").val().toLowerCase();
    sortingTable(a, "#sample_editable_1");
    return false
});
$("#ContentPlaceHolder1_txtSearchPractitioner").keyup(function (a) {
    if (!(a.which >= 33 && a.which <= 40)) {
        var b = $(this).val().toLowerCase();
        sortingTable(b, "#sample_editable_1")
    }
});
$("#ContentPlaceHolder1_btnSearch_Pain_Management").click(function () {
    var a = $("#ContentPlaceHolder1_txtSearchPractitioner_Pain_Management").val().toLowerCase();
    sortingTable(a, "#sample_editable_2");
    return false
});
$("#ContentPlaceHolder1_txtSearchPractitioner_Pain_Management").keyup(function (a) {
    if (!(a.which >= 33 && a.which <= 40)) {
        var b = $(this).val().toLowerCase();
        sortingTable(b, "#sample_editable_2")
    }
});
$("#ContentPlaceHolder1_btnSearch_Podiatry").click(function () {
    var a = $("#ContentPlaceHolder1_txtSearchPodiatry").val().toLowerCase();
    sortingTable(a, "#sample_editable_3");
    return false
});
$("#ContentPlaceHolder1_txtSearchPodiatry").keyup(function (a) {
    if (!(a.which >= 33 && a.which <= 40)) {
        var b = $(this).val().toLowerCase();
        sortingTable(b, "#sample_editable_3")
    }
});

function sortingTable(b, c) {
    $(c).find("tbody tr:even").each(function () {
        $(this).css("background-color", "#f5f5f5").children().css("background", "none")
    });
    $(c).find("tbody tr:odd").each(function () {
        $(this).css("background-color", "#ffffff").children().css("background", "none")
    });
    $(c).find("tbody tr").each(function () {
        if ($(this).find("td:first").text().toLowerCase().indexOf(b) > 0) {
            $(this).css("background-color", "#008000").children().css("background", "none")
        }
    });
    rowArr = getRows(c);
    keySort = [32768, 16119285, 16777215];
    sortTable(c, rowArr, keySort);
    return false
}
function sortTable(d, f, e) {
    $.each(e, function (b, a) {
        $.each(f, function (c, h) {
            if (h[0] === a) {
                $(d).append(h[1])
            }
        })
    })
}
function getRows(c) {
    var d = new Array();
    $(c + " tbody tr").each(function () {
        var a = $(this).css("background-color");
        var b = rgbToInt(a);
        d.push([b, $(this)])
    });
    return d
}
function rgbToInt(f) {
    var i = /(.*?)rgb\((\d+), (\d+), (\d+)\)/.exec(f);
    var h = parseInt(i[2]);
    var j = parseInt(i[3]);
    var g = parseInt(i[4]);
    return parseInt((g | (j << 8) | (h << 16)), 10)
}
function autoSortKeys(e) {
    var f = new Array();
    $.each(e, function (b, a) {
        f.push(a[0])
    });
    var d = new Array();
    $.each(f, function (b, a) {
        if ($.inArray(a, d) === -1) {
            d.push(a)
        }
    });
    delete f;
    return d.sort().reverse()
}
$("#ContentPlaceHolder1_btnInactiveUsers").click(function () {
    var name = $("#ContentPlaceHolder1_btnInactiveUsers").val();
    var toReplace = name.substring(0, 4);
    if (toReplace == 'Show') {
        var replacedName = name.replace(toReplace, 'Hide');
        $("#ContentPlaceHolder1_btnInactiveUsers").val(replacedName);

        $("#sample_editable_1").find("tr").each(function () {
            $(this).find("input[value=Inactive]").parents().eq(1).css("display", "").css("background-color", "Red").children().css("background", "none")
        });
    }
    else if (toReplace == 'Hide') {
        var replacedName = name.replace(toReplace, 'Show');
        $("#ContentPlaceHolder1_btnInactiveUsers").val(replacedName);

        $("#sample_editable_1").find("tr").each(function () {
            $(this).find("input[value=Inactive]").parents().eq(1).css("display", "none").css("background-color", "none").children().css("background", "none")
        });
    }
    return false
});
$("#ContentPlaceHolder1_btnInactiveUsers_Pain_Management").click(function () {
    var name = $("#ContentPlaceHolder1_btnInactiveUsers_Pain_Management").val();
    var toReplace = name.substring(0, 4);
    if (toReplace == 'Show') {
        var replacedName = name.replace(toReplace, 'Hide');
        $("#ContentPlaceHolder1_btnInactiveUsers_Pain_Management").val(replacedName);

        $("#sample_editable_2").find("tr").each(function () {
            $(this).find("input[value=Inactive]").parents().eq(1).css("display", "").css("background-color", "Red").children().css("background", "none")
        });
    }
    else if (toReplace == 'Hide') {
        var replacedName = name.replace(toReplace, 'Show');
        $("#ContentPlaceHolder1_btnInactiveUsers_Pain_Management").val(replacedName);

        $("#sample_editable_2").find("tr").each(function () {
            $(this).find("input[value=Inactive]").parents().eq(1).css("display", "none").css("background-color", "none").children().css("background", "none")
        });
    }
    return false
});
$("#ContentPlaceHolder1_btnInactiveUsers_Podiatry").click(function () {
    var name = $("#ContentPlaceHolder1_btnInactiveUsers_Podiatry").val();
    var toReplace = name.substring(0, 4);
    if (toReplace == 'Show') {
        var replacedName = name.replace(toReplace, 'Hide');
        $("#ContentPlaceHolder1_btnInactiveUsers_Podiatry").val(replacedName);

        $("#sample_editable_3").find("tr").each(function () {
            $(this).find("input[value=Inactive]").parents().eq(1).css("display", "").css("background-color", "Red").children().css("background", "none")
        });
    }
    else if (toReplace == 'Hide') {
        var replacedName = name.replace(toReplace, 'Show');
        $("#ContentPlaceHolder1_btnInactiveUsers_Podiatry").val(replacedName);

        $("#sample_editable_3").find("tr").each(function () {
            $(this).find("input[value=Inactive]").parents().eq(1).css("display", "none").css("background-color", "none").children().css("background", "none")
        });
    }
    return false
});
$(".Active").toggleButtons({
    width: 60,
    height: 23,
    font: {
        "font-size": "10px"
    },
    style: {
        enabled: "warning",
        disabled: "danger"
    },
    label: {
        enabled: "YES",
        disabled: "NO"
    },
    onChange: function (d, e, f) {
        //if ((e && $(d).parents().eq(2).children().find("input[id*=hdnPatientStatus]").val() == "Inactive") || (e && $(d).find("input").attr("id").indexOf("chkDeactivate") > 0) || (!e && $(d).parents().eq(2).children().find("input[id*=hdnPatientStatus]").val() == "Inactive" && $(d).find("input").attr("id").indexOf("chkDeactivate") > 0)) {
        if ((e && $(d).parents().eq(2).children().find("input[id*=hdnPatientStatus]").val() == "Inactive") || (e && $(d).find("input").attr("id").indexOf("chkDeactivate") > 0)) {
            //alert('in status=' + e);
            updatePatient(d);
        }
        //        if (!e) {
        //            if ($(d).find("input").attr("id").indexOf("chkDeactivate") > 0) {
        //                if (confirm("Are you sure you would like to deactive this patient")) { } else {
        //                    $(d).toggleButtons("setState", true)
        //                }
        //            }
        //        }

        if ($(d).find("input").hasClass("Treatment") || $(d).find("input").hasClass("Assessment") || $(d).find("input").hasClass("CarePlan") || $(d).find("input").hasClass("InServiceEducation") || $(d).find("input").hasClass("EPC")) {
            var t = $(d).find("input").parent().parent().parent().parent().find("td").find("#hdnCount");
            var x = parseInt($(t).val());
            if (e) {
                $(t).val(x + 1);
            } else {
                $(t).val(x - 1);
                if ($(t).val() < 0) {
                    $(t).val(0);
                }
            }
        }

        if ($(d).find("input").hasClass("Refused") || $(d).find("input").hasClass("Absent")) {
            var t = $(d).find("input").parent().parent().parent().parent().find("td").find("#hdnNotCount");
            var x = parseInt($(t).val());
            if (e) {
                $(t).val(x + 1);
            } else {
                $(t).val(x - 1);
                if ($(t).val() < 0) {
                    $(t).val(0);
                }
            }

            var k = $(d).find("input").parents().eq(3).find("input.Treatment").parents().eq(1);
            var l = $(d).find("input").parents().eq(3).find("input.Assessment").parents().eq(1);
            var m = $(d).find("input").parents().eq(3).find("input.CarePlan").parents().eq(1);
            var n = $(d).find("input").parents().eq(3).find("input.InServiceEducation").parents().eq(1);
            var o = $(d).find("input").parents().eq(3).find("input.EPC").parents().eq(1);
            $(k).toggleButtons("setState", false);
            $(l).toggleButtons("setState", false);
            $(m).toggleButtons("setState", false);
            $(n).toggleButtons("setState", false);
            $(o).toggleButtons("setState", false);
            //reset hdncount variable now
            $(d).find("input").parent().parent().parent().parent().find("td").find("#hdnCount").val(0);
        }
    }
});
$(".Active_Pain_Management").toggleButtons({
    width: 60,
    height: 23,
    font: {
        "font-size": "10px"
    },
    style: {
        enabled: "warning",
        disabled: "danger"
    },
    label: {
        enabled: "YES",
        disabled: "NO"
    },
    onChange: function (s, q, m) {
        //if (q) {
        if ((q && $(s).parents().eq(2).children().find("input[id*=hdnPatientStatus]").val() == "Inactive") || (q && $(s).find("input").attr("id").indexOf("chkDeactivate") > 0)) {
            updatePatient(s)
        }
        if ($(s).find("input").hasClass("4B")) {
            var r = 0;
            $(s).find("input").parents().eq(4).find("input.4B").each(function () {
                if ($(this).is(":checked")) {
                    r = r + 1
                }
                $(".4Btxt").val(r)
            })
        } else {
            if ($(s).find("input").hasClass("4A")) {
                var r = 0;
                $(s).find("input").parents().eq(4).find("input.4A").each(function () {
                    if ($(this).is(":checked")) {
                        r = r + 1
                    }
                    $(".4Atxt").val(r)
                })
            } else {
                if ($(s).find("input").hasClass("3")) {
                    var r = 0;
                    $(s).find("input").parents().eq(4).find("input.3").each(function () {
                        if ($(this).is(":checked")) {
                            r = r + 1
                        }
                        $(".3txt").val(r)
                    })
                } else {
                    if ($(s).find("input").hasClass("Refused") || $(s).find("input").hasClass("Absent") || $(s).find("input").hasClass("ill") || $(s).find("input").hasClass("Deceased") || $(s).find("input").hasClass("Hospital")) {
                        var t = $(s).find("input").parent().parent().parent().parent().find("td").find("#hdnNotCount");
                        var e = parseInt($(t).val());
                        var o = $(".nottxt").val();
                        if (q) {
                            $(t).val(e + 1);
                            if (parseInt($(t).val()) == 1) {
                                $(".nottxt").val(parseInt(o) + 1)
                            }
                        } else {
                            $(t).val(e - 1);
                            if (parseInt($(t).val()) == 0) {
                                $(".nottxt").val(parseInt(o) - 1)
                            }
                        }
                    }
                }
            }
        }
        if ($(s).find("input").hasClass("3") || $(s).find("input").hasClass("4A") || $(s).find("input").hasClass("4B")) {
            var t = $(s).find("input").parent().parent().parent().parent().find("td").find("#hdnCount");
            var e = parseInt($(t).val());
            var o = $(".pttxt").val();
            if (q) {
                $(t).val(e + 1);
                if (parseInt($(t).val()) == 1) {
                    $(".pttxt").val(parseInt(o) + 1)
                }
            } else {
                $(t).val(e - 1);
                if (parseInt($(t).val()) == 0) {
                    $(".pttxt").val(parseInt(o) - 1)
                }
            }
        }
        if ($(s).find("input").hasClass("Refused") || $(s).find("input").hasClass("Absent") || $(s).find("input").hasClass("ill") || $(s).find("input").hasClass("Deceased") || $(s).find("input").hasClass("Hospital")) {
            var p = $(s).find("input").parents().eq(3).find("input.3").parents().eq(1);
            var l = $(s).find("input").parents().eq(3).find("input.4A").parents().eq(1);
            var n = $(s).find("input").parents().eq(3).find("input.4B").parents().eq(1);
            $(p).toggleButtons("setState", false);
            $(l).toggleButtons("setState", false);
            $(n).toggleButtons("setState", false);
            //reset hdncount variable now
            $(s).find("input").parent().parent().parent().parent().find("td").find("#hdnCount").val(0);
        }
        //        if (!q) {
        //            if ($(s).find("input").attr("id").indexOf("chkDeactive") > 0) {
        //                if (confirm("Are you sure you would like to deactive this patient")) { } else {
        //                    $(s).toggleButtons("setState", true)
        //                }
        //            }
        //        }
    }
});
$(".Active_Podiatry").toggleButtons({
    width: 60,
    height: 23,
    font: {
        "font-size": "10px"
    },
    style: {
        enabled: "warning",
        disabled: "danger"
    },
    label: {
        enabled: "YES",
        disabled: "NO"
    },
    onChange: function (h, f, g) {
        //if (f) {
        if ((f && $(h).parents().eq(2).children().find("input[id*=hdnPatientStatus]").val() == "Inactive") || (f && $(h).find("input").attr("id").indexOf("chkDeactivate") > 0)) {
            updatePatient(h)
        }
        if ($(h).find("input").attr("id").indexOf("chkHighCare") > 0) {
            var e = $(h).find("input").parent().parent().parent().parent().find("td").find("#hdnIsHighCare");
            if (f) {
                if ($(e).val() == "1") {
                    alert("DVA already selected.");
                    $(h).toggleButtons("setState", false);
                    $(e).val(1)
                } else {
                    $(e).val(1)
                }
            } else {
                $(e).val(0)
            }
        }
        if ($(h).find("input").attr("id").indexOf("chkDVA") > 0) {
            var e = $(h).find("input").parent().parent().parent().parent().find("td").find("#hdnIsHighCare");
            if (f) {
                if ($(e).val() == "1") {
                    alert("DVA cannot be charged for HC patients");
                    $(h).toggleButtons("setState", false);
                    $(e).val(1)
                } else {
                    $(e).val(1)
                }
            } else {
                $(e).val(0)
            }
        }
        //        if (!f) {
        //            if ($(h).find("input").attr("id").indexOf("chkDeactivate") > 0) {
        //                if (confirm("Are you sure you would like to deactive this patient")) { } else {
        //                    $(h).toggleButtons("setState", true)
        //                }
        //            }
        //        }


        if ($(h).find("input").hasClass("Treatment") || $(h).find("input").hasClass("Assessment") || $(h).find("input").hasClass("CarePlan") || $(h).find("input").hasClass("HighCare") || $(h).find("input").hasClass("LowCare") || $(h).find("input").hasClass("EPC") || $(h).find("input").hasClass("DVA") || $(h).find("input").hasClass("D904") || $(h).find("input").hasClass("CashPayment")) {
            var t = $(h).find("input").parent().parent().parent().parent().find("td").find("#hdnCount");
            var x = parseInt($(t).val());
            if (f) {
                $(t).val(x + 1);
            } else {
                $(t).val(x - 1);
                if ($(t).val() < 0) {
                    $(t).val(0);
                }
            }
        }

        if ($(h).find("input").hasClass("CashPayment")) {
            var u = $(h).find("input").parents().eq(3).find("input.Amount");
            if (f) {
                $(u).removeAttr("disabled");
            }
            else {
                $(u).attr("disabled", "disabled");
            }
        }

        if ($(h).find("input").hasClass("Refused") || $(h).find("input").hasClass("Absent")) {
            var t = $(h).find("input").parent().parent().parent().parent().find("td").find("#hdnNotCount");
            var x = parseInt($(t).val());
            if (f) {
                $(t).val(x + 1);
            } else {
                $(t).val(x - 1);
                if ($(t).val() < 0) {
                    $(t).val(0);
                }
            }

            var k = $(h).find("input").parents().eq(3).find("input.Treatment").parents().eq(1);
            var l = $(h).find("input").parents().eq(3).find("input.Assessment").parents().eq(1);
            var m = $(h).find("input").parents().eq(3).find("input.CarePlan").parents().eq(1);
            var n = $(h).find("input").parents().eq(3).find("input.HighCare").parents().eq(1);
            var o = $(h).find("input").parents().eq(3).find("input.LowCare").parents().eq(1);
            var p = $(h).find("input").parents().eq(3).find("input.EPC").parents().eq(1);
            var q = $(h).find("input").parents().eq(3).find("input.DVA").parents().eq(1);
            var r = $(h).find("input").parents().eq(3).find("input.D904").parents().eq(1);
            var s = $(h).find("input").parents().eq(3).find("input.CashPayment").parents().eq(1);
            var u = $(h).find("input").parents().eq(3).find("input.Amount");
            var v = $(h).find("input").parents().eq(3).find("input.RecieptNumber");
            var w = $(h).find("input").parents().eq(3).find("input.Comments");

            $(k).toggleButtons("setState", false);
            $(l).toggleButtons("setState", false);
            $(m).toggleButtons("setState", false);
            $(n).toggleButtons("setState", false);
            $(o).toggleButtons("setState", false);
            $(p).toggleButtons("setState", false);
            $(q).toggleButtons("setState", false);
            $(r).toggleButtons("setState", false);
            $(s).toggleButtons("setState", false);

            $(u).val('');
            $(v).val('');
            $(w).val('');

            //reset hdncount variable now
            $(h).find("input").parent().parent().parent().parent().find("td").find("#hdnCount").val(0);
        }
    }
});

function updatePatient(c) {
//    if (($(c).parents().eq(2).children().find("input[id*=hdnPatientStatus]").val() == "Inactive") && ($(c).find("input").attr("id").indexOf("chkDeactivate") > 0)) {
//        var d = $(c).parents().eq(2).children().find("input[id*=hdnPatientID]").val();
//        if (confirm("do you want to add this patient to program?")) {
//            $.ajax({
//                type: "POST",
//                url: "../Handlers/UpdatePatient.ashx",
//                data: {
//                    "patientId": d,
//                    "status": true
//                },
//                success: function (a) {
//                    $(c).parents().eq(2).children().find("input[id*=hdnPatientStatus]").val("Active");
//                    $(c).parents().eq(2).css("background-color", "none").children().css("background", "#F5F5F5");
//                    alert("Patient has been activated")
//                },
//                error: function (a) {
//                    alert("ERROR: " + a.d)
//                }
//            })
//        } else {
//            $(c).toggleButtons("setState", true);
//            alert("Patient is still inactive")
//        }
//    }
    if ($(c).parents().eq(2).children().find("input[id*=hdnPatientStatus]").val() == "Inactive") {
        var d = $(c).parents().eq(2).children().find("input[id*=hdnPatientID]").val();
        if (confirm("Are you sure you want to add this patient to the active patients list?")) {
            $.ajax({
                type: "POST",
                url: "../Handlers/UpdatePatient.ashx",
                data: {
                    "patientId": d,
                    "status": true
                },
                success: function (a) {
                    $(c).parents().eq(2).children().find("input[id*=hdnPatientStatus]").val("Active");
                    $(c).parents().eq(2).css("background-color", "none").children().css("background", "#F5F5F5");
                    alert("Patient has been activated")
                },
                error: function (a) {
                    alert("ERROR: " + a.d)
                }
            })
        } else {
            $(c).toggleButtons("setState", false);
            alert("Patient is still inactive")
        }
    }
    if ($(c).parents().eq(2).children().find("input[id*=hdnPatientStatus]").val() == "Active" && $(c).find("input").attr("id").indexOf("chkDeactivate") > 0) {
        var d = $(c).parents().eq(2).children().find("input[id*=hdnPatientID]").val();
        //if (confirm("do you want to remove this patient from program?")) {
        if (confirm("Are you sure you want to remove this patient from the active patient list?")) {
            $.ajax({
                type: "POST",
                url: "../Handlers/UpdatePatient.ashx",
                data: {
                    "patientId": d,
                    "status": false
                },
                success: function (a) {
//                    $(c).parents().eq(2).children().find("input[id*=hdnPatientStatus]").val("Inactive");
//                    $(c).parents().eq(2).css("display", "none");

                    //$(c).parents().eq(2).children().find("input[id*=hdnPatientStatus]").val("Inactive");
                    $(c).parents().eq(2).css("background-color", "red").children().css("background", "red")
                    alert("Patient has been deactivated")
                },
                error: function (a) {
                    alert("ERROR: " + a.d)
                }
            })
        } else {
            $(c).toggleButtons("setState", false);
            alert("Patient is still active");
        }
    }
}
$(".Services").toggleButtons({
    width: 53,
    font: {
        "font-size": "10px"
    },
    style: {
        enabled: "warning",
        disabled: "danger"
    }
});
$("#ContentPlaceHolder1_btnSubmit").click(function () {
    $("#commentForm").validate()
});
$("#ContentPlaceHolder1_btnSubmit_Pain_Management").click(function () {
    $("#commentForm").validate()
});
$("#ContentPlaceHolder1_btnSubmit_Podiatry").click(function () {
    $("#commentForm").validate()
});
$("#ContentPlaceHolder1_btnPatientSave").click(function () {
    var b = 0;
    $("div.Services").each(function () {
        if ($(this).find("input[type=checkbox]").is(":checked")) {
            b++
        }
    });
    if (b <= 0) {
        alert("Please select atleast one service");
        return false
    }
});

function validate(b) {
    if (b == "PhysioOnly") {
        if ($("#sample_editable_1").find("tr").find("input[value=Active]").length < 1) {
            alert("There is no active patient for this report");
            return false
        }
    } else {
        if (b == "pain") {
            if ($("#sample_editable_2").find("tr").find("input[value=Active]").length < 1) {
                alert("There is no active patient for this report");
                return false
            }
        } else {
            if (b == "pod") {
                //if (Page_ClientValidate()) {
                    if ($("#sample_editable_3").find("tr").find("input[value=Active]").length < 1) {
                        alert("There is no active patient for this report");
                        return false
                    }
                //}
            }
        }
    }
}
function textChanged(a) {
    alert(a)
};

$('#ContentPlaceHolder1_btnphysiotherapyClear').click(function () {
    if ($("#ContentPlaceHolder1_txtSearchPractitioner").val() != "") {
        var b = $("#ContentPlaceHolder1_txtSearchPractitioner").val("");
        sortingTable(b, "#sample_editable_1");
    }
    return false;
});
$('#ContentPlaceHolder1_btnClearpain_Managment').click(function () {
    if ($("#ContentPlaceHolder1_txtSearchPractitioner_Pain_Management").val() != "") {
        var b = $("#ContentPlaceHolder1_txtSearchPractitioner_Pain_Management").val("");
        sortingTable(b, "#sample_editable_2");
    }
    return false;
});
$('#ContentPlaceHolder1_btnClear_Podiatry').click(function () {
    if ($("#ContentPlaceHolder1_txtSearchPodiatry").val() != "") {
        var b = $("#ContentPlaceHolder1_txtSearchPodiatry").val("");
        sortingTable(b, "#sample_editable_3");
    }
    return false;
});