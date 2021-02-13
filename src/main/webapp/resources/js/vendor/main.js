
(function ($) {
    "use strict";


    /*==================================================================
   [ Focus input ]*/
    $('.input100').each(function () {
        $(this).on('blur', function () {
            if ($(this).val().trim() != "") {
                $(this).addClass('has-val');
            }
            else {
                $(this).removeClass('has-val');
            }
        })
    })


    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit', function () {
        var check = true;

        for (var i = 0; i < input.length; i++) {
            if (validate(input[i]) == false) {
                showValidate(input[i]);
                check = false;
            }
        }

        return check;
    });


    $('.validate-form .input100').each(function () {
        $(this).focus(function () {
            hideValidate(this);
        });
    });

    function validate(input) {
        if ($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if ($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }
        else {
            if ($(input).val().trim() == '') {
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }

    $('#submit').click(function (event) {

        event.preventDefault();
        var username = document.getElementById('username').value;
        var password = document.getElementById('password').value;
        var data = $("#loginform").serialize();
        console.log(data);

        alert('username '+username+' password '+password);
        $.ajax({
           url: "perform_login",
            type: "POST",
            data: data,
           /*  type: $(this).action,
            url: $(this).target,
            data: data, */

            success: function (data) {
                console.log(data);
                top.location.href = "/homepage";
            },
            error: function (data) {
                document.getElementById('login_err_msg').innerHTML = 'Incorrect Credentials, Try Again !';
                console.log('error while submitting');
                console.log(data);

            }

        });
    });



})(jQuery);