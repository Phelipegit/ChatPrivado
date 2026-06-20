import { useState } from "react";
import "./css/Login.css"

export default function Login() {
  const API_URL = "https://chatprivado-x4wz.onrender.com";
  const [showPassword, setShowPassword] = useState(false);
  const [form, setForm] = useState({ email: "", password: "" });
  const [color, setColor] = useState("red");
  const [msg, setMsg] = useState("");

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();

    if (form.email.trim() === "" || form.password.trim() === "") {
      setMsg("Valores não podem ser vazios");
      return;
    }

    const response = await fetch(`${API_URL}/auth/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(form),
    });

    const data = await response.json();

    if (data.success) {
      setColor("green");
      setMsg(data.message);
      localStorage.setItem("token", data.token);
      setTimeout(() => {
        window.location.href = "/home";
      }, 2000);
    } else {
      setColor("red");
      setMsg(data.message || "Email ou senha inválidos");
    }
  }

  return (
    <div className="page">
      <div className="card">
        <div className="brand"></div>

        <div style={{ textAlign: "center" }}>
          <h1>Entre na sua conta</h1>
          <p className="subtitle">Preencha os campos para continuar.</p>
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
          <label htmlFor="password">Senha</label>
          <div className="input-wrap">
            <input
              type={showPassword ? "text" : "password"}
              id="password"
              name="password"
              placeholder="Mínimo 8 caracteres"
              value={form.password}
              onChange={handleChange}
              autoComplete="current-password"
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
          <p style={{ color: color, textAlign: "center" }}>{msg}</p>
        </div>

        <button className="btn-register" onClick={handleSubmit}>
          Entrar
        </button>

        <p className="footer">
          Não tem uma conta? <a href="/register">Cadastrar</a>
        </p>
      </div>
    </div>
  );
}