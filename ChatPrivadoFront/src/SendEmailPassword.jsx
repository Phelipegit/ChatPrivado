import { useState } from "react"
import "./css/SendEmailPassword.css"
export default function SendEmailPassword() {
    const API_URL = "https://api.phelipedev.com.br";
    const [email, setEmail] = useState("");
    const [message, setMessage] = useState("");
    const [success, setSucess] = useState(false);

    async function enviarEmailToken() {

        if (email.trim() == "") {
            setMessage("Insira um e-mail válido");
            return;
        }
        try {
            const response = await fetch(`${API_URL}/auth/password/sendEmail`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ "email": email })
            });

            const data = await response.json();

            setMessage(data.message);

            if (data.success) {
                setSucess(true);
            }
        }catch(err) {
            setMessage(err);
        }
    }

    return (
        <div id="container-principal" style={{ display: "flex", alignItems: "center", justifyContent: "center" }}>
            <h1>Redefinição de senha</h1>
            <strong>Insira seu e-mail</strong>
            <label>E-mail</label>
            <input placeholder="seu@email.com" onChange={(a => setEmail(a.target.value))} />
            <p style={{ color: success ? "green" : "red" }}>{message}</p>
            <button onClick={() => enviarEmailToken()}>Redefinir senha</button>
        </div>
    )
}
