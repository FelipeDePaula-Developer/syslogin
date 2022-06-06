import {ajaxRequest} from "../common_funcs/request_funcs.js";
import {showError} from "../common_funcs/errors_funcs.js";
import {removeElementsByClass} from "../common_funcs/DOM_manipulation.js";

let action = document.getElementById('submit-form-login');
action.addEventListener('click', () => {

    removeElementsByClass('form-error-message');
    let args = {
        email: document.getElementById('user-email').value,
        password: document.getElementById('user-password').value
    };

    ajaxRequest("POST", '/log/user', args, (res) => {
        if (res["success"] === "true") {
            window.location.replace('/dashboard');
        }

        if (res["success"] === "false") {
            showError('Email ou Senha Incorretos', false, "#check-box-remember-me")
        }
    });
});