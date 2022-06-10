function ajaxRequest(method, request_route, args, function_callback) {
    let httpx = new XMLHttpRequest();
    if (!httpx) {
        alert("Ajax Request Error")
        return false;
    }
    httpx.open(method, request_route, true);
    httpx.onreadystatechange = () => {
        if (httpx.readyState === 4 && httpx.status === 200) {
            let response = JSON.parse(httpx.responseText);
            function_callback(response);
        }
    };
    httpx.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    httpx.setRequestHeader("Access-Control-Allow-Origin", "*");
    let data = convertParamsToUrl(args);
    httpx.send(data);
}

function urlRequest(path, params, method='post') {
    const form = document.createElement('form');
    form.method = method;
    form.action = path;

    for (const key in params) {
        if (params.hasOwnProperty(key)) {
            const hiddenField = document.createElement('input');
            hiddenField.type = 'hidden';
            hiddenField.name = key;
            hiddenField.value = params[key];
            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}

function convertParamsToUrl(args) {
    return Object.keys(args).map(key => key + '=' + args[key]).join('&');
}

export {ajaxRequest, urlRequest};