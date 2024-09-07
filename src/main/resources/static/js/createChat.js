'use strict'

async function check(){
    let response = await fetch('http://127.0.0.1:8080/check', {credentials: 'include'});
    let check = false;
    if(response.ok) check = await response.json();
    if(!check) window.location.href = '/';
}

document.querySelector('.submit').onclick = async function(e){
    let phone = document.querySelector('#tel').value;
    if(phone == ''){
        document.querySelector('.warning').textContent = 'Incorrect phone number';
        return;
    }
    let url = 'http://127.0.0.1:8080/create_chat';
    let info = {'phone': phone};
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
    else if(message == 'redirect') window.location.href = '/';
    else document.querySelector('.warning').textContent = message;
}