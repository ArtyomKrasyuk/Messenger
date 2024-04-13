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