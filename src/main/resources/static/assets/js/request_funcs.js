function ajaxPost(request_route, args) {
    let httpx = new XMLHttpRequest();

    if (!httpx) {
        alert("Ajax Request Error")
        return false;
    }

    httpx.open('POST', request_route, true);
    httpx.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    let data = convertParamsToUrl(args);
    httpx.send(data);
    httpx.onreadystatechange = alertContents(httpx);
}

function convertParamsToUrl(args){
    return Object.keys(args).map(key => key + '=' + args[key]).join('&');
}

function alertContents(httpx) {
    if (httpx.readyState === XMLHttpRequest.DONE) {
        if (httpx.status === 200) {
            alert(httpx.responseText);
        } else {
            alert('There was a problem with the request.');
        }
    }
}

export {ajaxPost, alertContents};