import { Storage } from "./storage.js";
import { appendChat } from "./chat.js";
import { appendMessage } from "./chat.js";

export let stompClient = null;

export function connect(){
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected(){
    stompClient.subscribe(`/messenger/${Storage.userid}`, onMessage);
}

function onMessage(mes){
    let body = JSON.parse(mes.body);
    if(body.type === 'message'){
        appendMessage(body);
    }
    else if(body.type === 'chat'){
        appendChat(body);
    }
}
    
function onError(){
    alert("Connection Error!");
}