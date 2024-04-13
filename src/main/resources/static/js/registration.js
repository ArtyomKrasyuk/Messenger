'use strict'

let curdate = new Date();
let offset = -curdate.getTimezoneOffset();
document.cookie = `TimezoneOffset=${offset}`;

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