import { useEffect, useRef ,useState } from "react";
import './css/Perfil.css'
export default function Perfil() {

    const API_URL = "https://chatprivado-x4wz.onrender.com";

    const[username,setUsername] = useState();
    const[fotoPerfil,setFoto] = useState(); 

    const inputRef = useRef(null);

    useEffect(() => {
        async function buscarDados() {
            const token = localStorage.getItem("token");

            const response = await fetch(`${API_URL}/api/u/devolverDados`, {
                method:"GET",
                headers: {
                "Content-Type":"application/json",
                "Authorization": "Bearer " + token
                }
        });

        if(!response.ok) {
            window.location.href = "/login";
            return;
        }
        
        const data = await response.json();

        setUsername(data.username);
        setFoto(data.imagem_url);
    }
    buscarDados();
    },[]);

    async function handleTrocarFoto(e) {
    const file = e.target.files[0];
    if (!file) return;

    // Converte para base64
    const base64 = await new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result.split(",")[1]);
        reader.onerror = () => reject("Erro ao ler arquivo");
        reader.readAsDataURL(file);
    });

    const token = localStorage.getItem("token");

    const response = await fetch(`${API_URL}/api/u/updatePng`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({ base64: base64 })
    });

    const data = await response.json();

    if (data.success) {
        setColor("green");
        setMsg("Foto atualizada com sucesso!");
        setFoto(data.imagem_url);
    } else {
        setColor("red");
        setMsg(data.message || "Erro ao atualizar foto.");
    }
}

    return (
        <div id="principal">
            <h1 id="recepcao-usuario">Bem-vindo, {username}</h1>
            <img id="foto-usuario" src={fotoPerfil}></img>

            <input
                type="file"
                accept="image/*"
                ref={inputRef}
                style={{ display: 'none' }}
                onChange={handleTrocarFoto}
            />

            <button onClick={() => inputRef.current.click()}>Trocar foto</button>
        </div>
    )
}