import {ajaxPost} from "../common_funcs/request_funcs.js";

let action = document.getElementById('submit-form');
function stopDefaultSendAjax(action){
    action.preventDefault();
    let args = {
        userName: document.getElementById('user-name').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };
    ajaxPost("POST",'cad/user', args, '/pages/dashboard');
}

action.addEventListener("click", stopDefaultSendAjax)