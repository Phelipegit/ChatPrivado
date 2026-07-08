import { useEffect, useState } from "react";
import "./css/Home.css"
export default function Home() {

    const API_URL = "https://api.phelipedev.com.br";
    const TOKEN = localStorage.getItem("token");
    const[dataUsers,setData] = useState([]);

    useEffect(() => {
        async function buscarUsers() {
            const response = await fetch(`${API_URL}/api/u/devolverAllUsuarios`, {
                method:"GET",
                headers: {
                    "Content-Type":"application/json",
                    "Authorization":"Bearer " + TOKEN
                }
            });

            if(!response.ok) {
                window.location.href = "/login";
                return;
            }

            const data = await response.json();

            setData(data);
        }
        buscarUsers();
    },[]);

    async function criarChat(username) {
        const response = await fetch(`${API_URL}/api/u/chat/criarChat`, {
            method:"POST",
            headers: {
                "Content-Type":"application/json",
                "Authorization": "Bearer " + TOKEN
            },
            body: JSON.stringify({"userUsername":username})
        });

        const data = await response.json();

        if(!data.success) {
            window.location.href = "/login"
        }

        const id = data.chat_id;

        window.location.href = `/chat/${id}`;
    }   

    return (
        <div id="div-principal">
            {dataUsers.map((element) => (
                <div key={element.username}>
                <p>{element.username}</p>
                <button className="button-foto" onClick={() => criarChat(element.username)}>
                    <img  id="img"  src={element.imagem_url} alt="fotoPerfil"></img>
                </button>
                </div>
            ))
            }
        </div>
    )
}