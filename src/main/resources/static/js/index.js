'use strict'


async function check(){
    let response = await fetch('http://127.0.0.1:8080/check', {credentials: "include"});
    let check = false;
    if(response.ok) check = await response.json();
    if(check) window.location.href = '/chat';
}

check();

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

document.querySelector('.submit').onclick = async function(e){
    let phone = document.querySelector('#tel').value;
    if(phone == ''){
        document.querySelector('.warning').textContent = 'Incorrect phone number';
        return;
    }
    let password = document.querySelector('#password').value;
    if(password == ''){
        document.querySelector('.warning').textContent = 'Incorrect password';
        return;
    }
    let url = 'http://127.0.0.1:8080/';
    let info = {'phone': phone, 'password': password};
    let response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
          },
        body: JSON.stringify(info),
        credentials: 'include'
    });
    let message = null;
    if(response.ok) message = await response.text();
    if(message == 'ok') window.location.href = '/chat';
    else document.querySelector('.warning').textContent = message;
    check();
}