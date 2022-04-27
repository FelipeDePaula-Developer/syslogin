function ajaxPost(method, request_route, args, route_callback = '') {
    let httpx = new XMLHttpRequest();

    if (!httpx) {
        alert("Ajax Request Error")
        return false;
    }
    httpx.open(method, request_route, true);
    httpx.onreadystatechange = () => {
        if (httpx.readyState === 4 && httpx.status === 200 && httpx.responseText === 'true') {
            location.replace(route_callback);
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