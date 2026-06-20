import { Link } from "react-router-dom";

export default function NavBar() {

    const URL = "http://localhost:5173/";

    function Sair() {
        localStorage.removeItem("token");

        window.location.href="/login";
    }

    return (
        <div style={{display:"flex",paddingLeft:"40px",paddingTop:"15px",gap:"20px"}}>
            <Link to="/home">Início</Link>
            <Link to="/profile">Perfil</Link>
            <Link to="/chats">Chats</Link>
            <button onClick={Sair}>Sair</button>
        </div>
    )
}