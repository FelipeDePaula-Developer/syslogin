function ajaxPost(request_route, args) {
    let httpx = new XMLHttpRequest();

    if (!httpx) {
        alert("Ajax Request Error")
        return false;
    }
    httpx.open('POST', request_route, true);
    httpx.onreadystatechange = () => {
        if (httpx.readyState === 4 && httpx.status === 200 && httpx.responseText === 'true') {
            return true;
        }
    };
    httpx.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    httpx.setRequestHeader("Access-Control-Allow-Origin", "*");
    let data = convertParamsToUrl(args);
    httpx.send(data);
}

function convertParamsToUrl(args) {
    return Object.keys(args).map(key => key + '=' + args[key]).join('&');
}

export {ajaxPost};