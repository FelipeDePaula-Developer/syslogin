/**
 * Função para exibição do erro padrão do sistema nas telas de cadastro
 *
 * element @string
 * elementIdentification @string
 * errorText @string
 */

function showError(errorText, element, elementIdentification = ''){
    console.log(errorText);
    console.log(element);
    console.log(elementIdentification);
    let error = `<span class="form-error-message">${errorText}</span>`;
    if (elementIdentification !== ''){
        document.querySelector(elementIdentification).insertAdjacentHTML('afterend', error);
    } else{
        element.insertAdjacentHTML('afterend', error);
    }
}

export {showError};