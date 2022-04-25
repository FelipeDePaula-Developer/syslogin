import {ajaxPost} from "../request_funcs.js";

let action = document.getElementById('submit-form-login');
action.addEventListener('click', () =>{
    let args = {email: document.getElementById('user-email').value,
                password: document.getElementById('user-password').value};

    console.table(args);
    ajaxPost("GET", 'pages/log/user', args, 'pages/dashboard');
});