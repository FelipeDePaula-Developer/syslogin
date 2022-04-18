import {ajaxPost} from "../request_funcs.js";

let action = document.getElementById('submit-form');
function stopDefaultSendAjax(action){
    action.preventDefault();

    let args = {
        nome: document.getElementById('nome').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };
    ajaxPost('cad/user', args);
}

action.addEventListener("click", stopDefaultSendAjax)