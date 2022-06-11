import {checkInputsForm, urlRequest} from "../common_funcs/request_funcs.js";
let action = document.getElementById('submit-form');

function sendRequestCadUser(action) {
    action.preventDefault();
    if(!checkInputsForm()){
        return;
    }

    let userName = document.getElementById('username')
    let email = document.getElementById('email')
    let password = document.getElementById('password')

    let args = {
        userName: userName.value,
        email: email.value,
        password: password.value
    };

    urlRequest("/cad/user", args);
}

action.addEventListener("click", sendRequestCadUser)