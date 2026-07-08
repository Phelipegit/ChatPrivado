import { useEffect, useState } from "react";
import "./css/Register.css";

export default function Register() {
  const API_URL = "https://api.phelipedev.com.br";
  const [showPassword, setShowPassword] = useState(false);
  const [form, setForm] = useState({ email: "", username: "", password: "" });
  const [color,setColor] = useState("red");
  const [msg,setMsg] = useState("");

  useEffect(() => {
    const token = localStorage.getItem("token");
    if(token) {
      window.location.href= "/home";
    }
  },[])
  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();

    if(form.email.trim() == "" || form.username.trim() == "" || form.password.trim() == "") {
        setMsg("Valores não podem serem vazios");
        return;
    }

    const response = await fetch(`${API_URL}/auth/register`, {
        method:"POST",
        headers: {
            "Content-Type":"application/json"
        },
        body: JSON.stringify(form)
    });

    const data = await response.json();
    
    setMsg(data.message);

    if(data.success) {
        setColor("green");
        setTimeout(() => {
            window.location.href = "/login";
        },2000);
    }
  }

  return (
    <div className="page">
      <div className="card">
        <div className="brand">
        </div>

        <div style={{textAlign:"center"}}>
            <h1>Crie sua conta</h1>
            <p className="subtitle">Preencha os campos para começar.</p>
        </div>

        <div className="field">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            name="email"
            placeholder="seu@email.com"
            value={form.email}
            onChange={handleChange}
            autoComplete="email"
          />
        </div>

        <div className="field">
          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            name="username"
            placeholder="usuario"
            value={form.username}
            onChange={handleChange}
            autoComplete="username"
          />
        </div>

        <div className="field">
          <label htmlFor="password">Senha</label>
          <div className="input-wrap">
            <input
              type={showPassword ? "text" : "password"}
              id="password"
              name="password"
              placeholder="Mínimo 8 caracteres"
              value={form.password}
              onChange={handleChange}
              autoComplete="new-password"
            />
            <button
              type="button"
              className="toggle-pw"
              onClick={() => setShowPassword(!showPassword)}
              aria-label="Mostrar senha"
            >
              {showPassword ? "👁️" : "👁️"}
            </button>
          </div>
          <p style={{color:color,textAlign:"center"}}>{msg}</p>
        </div>

        <button className="btn-register" onClick={handleSubmit}>
          Criar conta
        </button>

        <p className="footer">
          Já tem uma conta? <a href="/login">Entrar</a>
        </p>
      </div>
    </div>
  );
}
