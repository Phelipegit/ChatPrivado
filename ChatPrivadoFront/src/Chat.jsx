import { Client } from "@stomp/stompjs";
import { useEffect, useState, useRef } from "react";
import { useParams } from "react-router-dom";
import SockJS from "sockjs-client";
import "./css/Chat.css";

export default function Chat() {
    const API_URL = "https://api.phelipedev.com.br";
    const [msg, setMsg] = useState("");
    const [data, setData] = useState([]);
    const [user, setUser] = useState("");
    const [imgUrl, setImgUrl] = useState("");
    const TOKEN = localStorage.getItem("token");
    const { id } = useParams();
    const stompRef = useRef(null);
    const bottomRef = useRef(null);

    useEffect(() => {
        async function devolverDadosUser() {
            const response = await fetch(`${API_URL}/api/u/chat/${id}/devolverDadosParticipantes`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + TOKEN
                }
            });

            if (!response.ok) {
                window.location.href = "/login";
                return;
            }

            const data = await response.json();
            setUser(data.username);
            setImgUrl(data.imagem_url);
        }

        async function devolverMsgs() {
            const response = await fetch(`${API_URL}/api/u/chat/${id}/mensagens`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + TOKEN
                }
            });

            if (!response.ok) {
                window.location.href = "/login";
                return;
            }

            const data = await response.json();
            setData(data);
        }

        devolverDadosUser();
        devolverMsgs();

        const client = new Client({
            webSocketFactory: () => new SockJS(`${API_URL}/ws`),
            connectHeaders: { Authorization: "Bearer " + TOKEN },
            onConnect: () => {
                client.subscribe(`/topic/chat/${id}`, (frame) => {
                    const novaMensagem = JSON.parse(frame.body);
                    setData((prev) => [...prev, novaMensagem]);
                });
            },
        });

        client.activate();
        stompRef.current = client;

        return () => client.deactivate();
    }, [id]);

    useEffect(() => {
        bottomRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [data]);

    function enviarMsg() {
        if (!stompRef.current?.connected || !msg.trim()) return;

        stompRef.current.publish({
            destination: `/app/chat/${id}`,
            body: JSON.stringify({ message: msg }),
        });

        setMsg("");
    }

    return (
        <div className="chat-container">
            <div className="chat-messages">
                {data.map((element) => {
                    const isMe = element.userSender === user;
                    return (
                        <div key={element.id} className={`chat-message-wrapper ${isMe ? "me" : "other"}`}>
                            {!isMe && (
                                <div className="chat-avatar">
                                    {element.sender?.charAt(0).toUpperCase()}
                                </div>
                            )}
                            {isMe && (
                                <div className="chat-avatar chat-avatar-me">
                                    {imgUrl
                                        ? <img src={imgUrl} alt={user} style={{ width: "100%", height: "100%", borderRadius: "50%", objectFit: "cover" }} />
                                        : user?.charAt(0).toUpperCase()
                                    }
                                </div>
                            )}
                            <div className={`chat-bubble ${isMe ? "me" : "other"}`}>
                                {!isMe && <p className="chat-bubble-sender">{element.sender}</p>}
                                <p>{element.message}</p>
                                <p className="chat-bubble-time">
                                    {new Date(element.createAt).toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" })}
                                </p>
                            </div>
                        </div>
                    );
                })}
                <div ref={bottomRef} />
            </div>

            <div className="chat-input-area">
                <input
                    className="chat-input"
                    value={msg}
                    placeholder="Mensagem"
                    onChange={(e) => setMsg(e.target.value)}
                    onKeyDown={(e) => e.key === "Enter" && enviarMsg()}
                />
                <button className="chat-send-btn" onClick={enviarMsg}>➤</button>
            </div>
        </div>
    );
}