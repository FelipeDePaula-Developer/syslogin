import {ajaxRequest} from "../common_funcs/request_funcs.js";
import {removeElementsByClass} from "../common_funcs/DOM_manipulation.js";
import {showError} from "../common_funcs/errors_funcs.js";

let action = document.getElementById('submit-form');

function stopDefaultSendAjax(action) {
    action.preventDefault();
    removeElementsByClass('form-error-message');
    let sendAjax = true;
    let userName = document.getElementById('username')
    let email = document.getElementById('email')
    let password = document.getElementById('password')

    if (userName.value === '') {
        showError("Campo obrigatório", userName);
        sendAjax = false
    }

    if (email.value === '') {
        showError("Campo obrigatório", email);
        sendAjax = false
    }

    if (password.value === '') {
        showError("Campo obrigatório", password);
        sendAjax = false
    }

    if (sendAjax) {
        let args = {
            userName: userName.value,
            email: email.value,
            password: password.value
        };

        ajaxRequest("POST", 'cad/user', args, (res) => {
            if  (res['success'] === 'true'){
                window.location.replace('/dashboard')
            }

            if (res['username'] !== '' && res['error'] !== undefined) {
                // let span = `<span class="form-error-message">${res['username']}</span>`;
                // document.getElementById('username').insertAdjacentHTML('afterend', span);
                showError(res['username'], '', '#username');
            }

            if (res['email'] !== '' && res['error'] !== undefined) {
                // let span = `<span class="form-error-message">${res['email']}</span>`;
                // document.getElementById('email').insertAdjacentHTML('afterend', span);
                showError(res['email'], '', '#email');
            }
        });
    }
}

action.addEventListener("click", stopDefaultSendAjax)