import {ajaxPost} from "../common_funcs/request_funcs.js";

let action = document.getElementById('submit-form-login');
action.addEventListener('click', () =>{
    let args = {email:  document.getElementById('user-email').value,
                password: document.getElementById('user-password').value};

    ajaxPost("POST", 'pages/log/user', args, 'pages/dashboard');
});