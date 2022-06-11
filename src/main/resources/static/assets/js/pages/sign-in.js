import {checkInputsForm, urlRequest} from "../common_funcs/request_funcs.js";

function sendRequestLogUser(action) {
    action.preventDefault();
    if(!checkInputsForm()){
        return;
    }

    let args = {
        email: document.getElementById('user-email').value,
        password: document.getElementById('user-password').value
    };

    urlRequest("/log/user", args);
}

let action = document.getElementById('submit-form-login');
action.addEventListener('click', sendRequestLogUser);
