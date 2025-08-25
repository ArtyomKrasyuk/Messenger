'use strict'

async function check(){
    let response = await fetch('http://127.0.0.1:8080/check', {credentials: 'include'});
    let check = false;
    if(response.ok) check = await response.json();
    if(check) window.location.href = '/chat';
}

check();

document.querySelector('.first').onclick = function(e){
    let input = document.querySelector('.password_field1');
    if(input.getAttribute('type') == 'password'){
        e.currentTarget.classList.add('view');
        input.setAttribute('type', 'text');
    }
    else{
        e.currentTarget.classList.remove('view');
        input.setAttribute('type', 'password');
    }
}

document.querySelector('.second').onclick = function(e){
    let input = document.querySelector('.password_field2');
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
    let username = document.querySelector('#username').value;
    if(username == ''){
        document.querySelector('.warning').textContent = 'Incorrect username';
        return;
    }
    let phone = document.querySelector('#tel').value;
    if(phone == ''){
        document.querySelector('.warning').textContent = 'Incorrect phone number';
        return;
    }
    let password1 = document.querySelector('#password1').value;
    if(password1 == ''){
        document.querySelector('.warning').textContent = 'Incorrect password';
        return;
    }
    let password2 = document.querySelector('#password2').value;
    if(password2 == ''){
        document.querySelector('.warning').textContent = 'Incorrect password';
        return;
    }
    if(password1 != password2){
        document.querySelector('.warning').textContent = 'Password mismatch';
        return;
    }
    let url = 'http://127.0.0.1:8080/registration';
    let info = {'username': username,'phone': phone, 'password1': password1, 'password2': password2};
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
}