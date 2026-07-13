import { useState } from "react"

export default function UpdatePassword() {
    const API_URL = "api.phelipedev.com.br"
    const [email,setEmail] = useState("");
    const [message,setMessage] = useState("");
    const [success,setSucess] = useState(false);

    async function enviarEmailToken() {
        const response = await fetch(`${API_URL} + /password/sendEmail`,{
            method:"POST",
            headers: {
                "Content-Type":"application/json"
            },
            body: JSON.stringify({"email":email})
        });

        const data = await response.json();

        

        if(data.success) {
            setMessage("Um link para redefinição de senha foi enviado para seu e-mail");
            setSucess(true);
        }
    }

    return (
        <div style={{display:"flex",alignItems:"center",justifyContent:"center"}}>
            <h1>Redefinição de senha</h1>
            <strong>Insira seu e-mail</strong>
            <input placeholder="seu@email.com" onChange={(a => (a.target.value))}/>
            <p style={{color: success ? "green" : "red"}}>{message}</p>
            <button onClick={enviarEmailToken()}>Enviar e-mail de verificação</button>
        </div>
    )
}
