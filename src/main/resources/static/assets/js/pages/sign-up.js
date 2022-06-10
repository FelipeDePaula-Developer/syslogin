import {ajaxRequest, urlRequest} from "../common_funcs/request_funcs.js";
import {removeElementsByClass} from "../common_funcs/DOM_manipulation.js";
import {showError} from "../common_funcs/errors_funcs.js";

let action = document.getElementById('submit-form');

function sendRequest(action) {
    action.preventDefault();
    removeElementsByClass('form-error-message');
    let send = true;
    let userName = document.getElementById('username')
    let email = document.getElementById('email')
    let password = document.getElementById('password')

    if (userName.value === '') {
        showError("Campo obrigatório", userName);
        send = false
    }

    if (email.value === '') {
        showError("Campo obrigatório", email);
        send = false
    }

    if (password.value === '') {
        showError("Campo obrigatório", password);
        send = false
    }

    if (send) {
        let args = {
            userName: userName.value,
            email: email.value,
            password: password.value
        };

        urlRequest("cad/user", args);
    }
}

action.addEventListener("click", sendRequest)