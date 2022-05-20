import {ajaxRequest} from "../common_funcs/request_funcs.js";
import {removeElementsByClass} from "../common_funcs/DOM_manipulation.js";

let action = document.getElementById('submit-form');

function stopDefaultSendAjax(action) {
    action.preventDefault();


    removeElementsByClass('form-error-message');


    let sendAjax = true;
    let userName = document.getElementById('username')
    let email = document.getElementById('email')
    let password = document.getElementById('password')

    if (userName.value === '') {
        let error = `<span class="form-error-message">Campo obrigatório</span>`;
        userName.insertAdjacentHTML('afterend', error);
        sendAjax = false
    }

    if (email.value === '') {
        let error = `<span class="form-error-message">Campo obrigatório</span>`;
        email.insertAdjacentHTML('afterend', error);
        sendAjax = false
    }

    if (password.value === '') {
        let error = `<span class="form-error-message">Campo obrigatório</span>`;
        password.insertAdjacentHTML('afterend', error);
        sendAjax = false
    }

    if (sendAjax) {
        let args = {
            userName: userName.value,
            email: email.value,
            password: password.value
        };

        ajaxRequest("POST", 'cad/user', args, (res) => {
            if (res['username'] !== '' && res['error'] !== undefined) {
                let span = `<span class="form-error-message">${res['username']}</span>`;
                document.getElementById('username').insertAdjacentHTML('afterend', span);
            }
            ;
            if (res['email'] !== '' && res['error'] !== undefined) {
                let span = `<span class="form-error-message">${res['email']}</span>`;
                document.getElementById('email').insertAdjacentHTML('afterend', span);
            }
            ;
        });
    }
}

action.addEventListener("click", stopDefaultSendAjax)