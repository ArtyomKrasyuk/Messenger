'use strict'

let curdate = new Date();
let offset = -curdate.getTimezoneOffset();
document.cookie = `TimezoneOffset=${offset}`;

document.querySelector('.img_container').onclick = function(e){
    let input = document.querySelector('.last');
    if(input.getAttribute('type') == 'password'){
        e.currentTarget.classList.add('view');
        input.setAttribute('type', 'text');
    }
    else{
        e.currentTarget.classList.remove('view');
        input.setAttribute('type', 'password');
    }
}
/*
document.querySelector('.submit').onclick = async function(e){
    let tel = document.querySelector('#tel').value;
    if(tel == ''){
        document.querySelector('.warning').value = 'Incorrect phone number';
        return;
    }
    let password = document.querySelector('#password').value;
    if(password == ''){
        document.querySelector('.warning').value = 'Incorrect password';
        return;
    }
    let url = 'http://127.0.0.1:8080/';
    let info = {'tel': tel, 'password': password};
    let response = fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
          },
        body: JSON.stringify(info)
    });
    let warning = null;
    if(response.ok)  warning = await response.text();
    document.querySelector('.warning').value = warning;
}*/