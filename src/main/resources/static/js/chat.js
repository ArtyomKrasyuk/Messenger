import {Storage} from './storage.js';
import {connect, stompClient} from './websocket.js';

let activeChat;

export function appendChat(chat){
    let div = document.createElement('div');
    div.className = 'chat';
    div.innerHTML = `<p class="username">${chat.username}</p><p class="chatid" style="display: none;">${chat.chatid}</p>`;
    div.onclick = onClick;
    document.querySelector('.chats').append(div);
}

export function appendMessage(message){
    let type = null;
    message = changeDate(message);
    if(message.userid == Storage.userid) type = 'my';
    else type = 'other';
    let div = document.createElement('div');
    div.className = `message ${type}`;
    div.innerHTML = `<p class="msg_txt">${message.content}</p><p class="date">${message.date}</p>`;
    document.querySelector('.messages').append(div);
}

export function changeDate(message){
    let date = new Date();
    let offset = -date.getTimezoneOffset();
    date.setSeconds(0);
    date.setMilliseconds(0);
    date.setHours(Number(message.date.slice(0, 2)));
    date.setDate(Number(message.date.slice(6, 8)));
    date.setMonth(Number(message.date.slice(9, 11)) - 1);
    date.setFullYear(Number(message.date.slice(12)));
    date.setMinutes(Number(message.date.slice(3, 5)) + offset);
    let hours = String(date.getHours());
    if(hours.length === 1) hours = '0' + hours;
    let minutes = String(date.getMinutes());
    if(minutes.length === 1) minutes = '0' + minutes;
    let day = String(date.getDate());
    if(day.length === 1) day = '0'+ day;
    let month = String(date.getMonth()+1);
    if(month.length === 1) month = '0' + month;
    let strdate = `${hours}:${minutes} ${day}.${month}.${date.getFullYear()}`;
    message.date = strdate;
    return message;
}

function onClick(e){
    if(activeChat != null) activeChat.style.backgroundColor = '#fff';
    else{
        document.querySelector('.start_message').style.display = 'none';
        document.querySelector('.chat_wrapper').style.display = 'block';
    }
    document.querySelector('.messages').innerHTML = '';
    let chatid = e.currentTarget.children[1].textContent;
    fetch(`http://127.0.0.1:8080/get_messages?chatid=${chatid}`, {credentials: 'include'}).then(response => response.json()).then(messages => {
        for(let elem of messages){
            appendMessage(elem);
        }
    });
    let element = e.currentTarget;
    element.style.backgroundColor = '#06C1AB';
    activeChat = element;
}

async function check(){
    let response = await fetch('http://127.0.0.1:8080/check', {credentials: 'include'});
    let check = false;
    if(response.ok) check = await response.json();
    if(!check) window.location.href = '/';
}

check();

let response = await fetch('http://127.0.0.1:8080/userid', {credentials: 'include'});
if(response.ok) Storage.userid = await response.text();

let chatsResponse = await fetch('http://127.0.0.1:8080/get_chats', {credentials: 'include'});
let chats = null;
if(chatsResponse.ok) chats = await chatsResponse.json();
for(let chat of chats){
    appendChat(chat);
}

connect();

document.querySelector('#input').focus();
document.querySelector('#input').onkeyup = function(e){
    if (e.keyCode === 13) {
        document.querySelector('.submit').click();
    }
};

document.querySelector('.submit').onclick = async function(e){
    let response =  await fetch('http://127.0.0.1:8080/check', {credentials: 'include'});
    let data = null;
    if(response.ok) data = await response.json();
    else data = false;
    if(!data){
        document.querySelector('.exit').click();
        return;
    }
    const message = document.querySelector('#input').value;
    let now = new Date();
    let hours = String(now.getUTCHours());
    if(hours.length === 1){
        hours = '0' + hours;
    }
    let minutes = String(now.getUTCMinutes());
    if(minutes.length === 1){
        minutes = '0' + minutes;
    }
    let day = String(now.getUTCDate());
    if(day.length === 1){
        day = '0'+ day;
    }
    let month = String(now.getUTCMonth()+1);
    if(month.length === 1){
        month = '0' + month;
    }
    let date = `${hours}:${minutes} ${day}.${month}.${now.getUTCFullYear()}`;
    stompClient.publish({destination: '/app/message', body: JSON.stringify({
        'userid': Storage.userid, 
        'chatid': activeChat.children[1].textContent,
        'content': message,
        'date': date,
        'type': 'message'
    })});
    document.querySelector('#input').value = '';
};

document.querySelector('.exit').onclick = function(e){
    fetch('http://127.0.0.1:8080/exit', {credentials: 'include'}).then(response => {if(response.ok) window.location.href = '/';});
}