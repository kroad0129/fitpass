const express = require('express');
const http = require('http');
const socketIo = require('socket.io');

const app = express();
const server = http.createServer(app);
const io = socketIo(server);

let chatHistory = {}; // 채팅 기록을 저장할 객체

io.on('connection', (socket) => {
    console.log('사용자가 연결되었습니다: ' + socket.id);

    socket.on('joinRoom', ({ roomId, username }) => {
        socket.join(roomId);
        console.log(`${username}님이 방에 참가했습니다: ${roomId}`);

        // 채팅방에 입장할 때 채팅 기록을 전송
        if (chatHistory[roomId]) {
            socket.emit('chatHistory', chatHistory[roomId]);
        } else {
            chatHistory[roomId] = []; // 방이 처음 생성될 때 초기화
        }

        socket.on('message', ({ username, message }) => {
            console.log(`메시지 받음 - 사용자: ${username}, 메시지: ${message}`);
            const msg = { username, message };
            chatHistory[roomId].push(msg);
            io.to(roomId).emit('message', msg);
        });

        socket.on('disconnect', () => {
            console.log(`${username}님이 방을 떠났습니다: ${roomId}`);
        });
    });
});

server.listen(10001, () => {
    console.log('서버가 10001번 포트에서 실행 중입니다');
});
