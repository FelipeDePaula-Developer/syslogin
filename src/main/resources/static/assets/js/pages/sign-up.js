import {ajaxPost} from "../request_funcs.js";

let action = document.getElementById('submit-form');
function stopDefaultSendAjax(action){
    action.preventDefault();
    let args = {
        userName: document.getElementById('user-name').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };
    let ajaxReturn = ajaxPost('cad/user', args);
    if (ajaxReturn){
        location.replace('/');
    }
}

action.addEventListener("click", stopDefaultSendAjax)