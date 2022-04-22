function ajaxPost(request_route, args, return_route = '') {
    let httpx = new XMLHttpRequest();

    if (!httpx) {
        alert("Ajax Request Error")
        return false;
    }
    httpx.open('POST', request_route, true);
    httpx.onreadystatechange = () => {
        if (httpx.readyState === 4 && httpx.status === 200) {
            console.table(httpx);
            let ajaxReturn = JSON.parse(httpx.responseText);
            console.table(ajaxReturn);
            var ajaxSucess = true;
            Object.values(ajaxReturn).forEach((campo) => {
                if (campo == null) {
                    ajaxSucess = false;
                }
            })
            if (ajaxReturn && return_route !== '') {
                location.replace(return_route);
            }
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