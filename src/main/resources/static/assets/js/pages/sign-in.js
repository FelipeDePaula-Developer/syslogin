import {ajaxRequest} from "../common_funcs/request_funcs.js";
import {showError} from "../common_funcs/errors_funcs.js";

let action = document.getElementById('submit-form-login');
action.addEventListener('click', () =>{
    let args = {email:  document.getElementById('user-email').value,
                password: document.getElementById('user-password').value};

    ajaxRequest("POST", 'pages/log/user', args, (res) =>{
        if (res["success"] === "true"){
            window.location.replace('/dashboard');
        }

        if (res["success"] === "false"){
            showError('Email ou Senha Incorretos', false, "#check-box-remember-me")
        }
    });
});