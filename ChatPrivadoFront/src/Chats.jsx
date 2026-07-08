import { useEffect, useState } from "react"
import './css/Chats.css'
export default function Chats() {

    const TOKEN = localStorage.getItem("token");
    const API_URL = "https://api.phelipedev.com.br";
    const [data, setData] = useState([]);
    useEffect(() => {

        if (TOKEN == "") {
            window.location.href = "/login";
        }

        async function devolverChats() {
            const response = await fetch(`${API_URL}/api/u/devolverAllChats`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + TOKEN
                }
            })

            if (!response.ok) {
                window.location.href = "/login";
                return;
            }

            const data = await response.json();

            setData(data);
        }
        devolverChats();
    }, []);

    function irParaChat(uuid) {
        window.location.href = `/chat/${uuid}`;
    }

    return (
        <div className="chats-list">
            {data.map((element) => (
                <button key={element.id_chat} className="button-chat" onClick={() => irParaChat(element.id_chat)}>
                    <img className="chat-avatar" src={element.perfil_imagem} alt="foto" />
                    <p className="chat-username">{element.usernameOutro}</p>
                </button>
            ))}
        </div>
    )
}