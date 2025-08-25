import { Storage } from "./storage.js";
import { appendChat } from "./chat.js";
import { appendMessage } from "./chat.js";

export let stompClient = null;

export function connect(){
    stompClient = new StompJs.Client({
        brokerURL: 'ws://127.0.0.1:8080/ws'
    });
    stompClient.onConnect = frame => {stompClient.subscribe(`/messenger/${Storage.userid}`, onMessage);}
    stompClient.onStompError = onError;
    stompClient.activate();
}

/*function onConnected(){
    stompClient.subscribe(`/messenger/${Storage.userid}`, onMessage);
}*/

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