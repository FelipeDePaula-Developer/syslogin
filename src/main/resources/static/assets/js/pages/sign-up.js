import {ajaxRequest} from "../common_funcs/request_funcs.js";

let action = document.getElementById('submit-form');
function stopDefaultSendAjax(action){
    action.preventDefault();
    let args = {
        userName: document.getElementById('user-name').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };
    ajaxRequest("POST",'cad/user', args, (res) =>{
        if ((res['email'] !== ''|| res['username'] !== '') && res['error'] !== undefined){
            alert(res);
        };
    });
}

action.addEventListener("click", stopDefaultSendAjax)