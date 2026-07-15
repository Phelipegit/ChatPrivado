import { useState } from 'react'
import { Routes, Route, useLocation, Navigate } from 'react-router-dom'
import Perfil from './Perfil'
import Register from './Register'
import Login from './Login'
import Home from './Home'
import NavBar from './Navbar'
import Chat from './Chat'
import Chats from './Chats'
import sendEmailPassword from './SendEmailPassword'
function App() {
  const location = useLocation();
  const rotasSemNavbar = ['/login', '/register','/password/sendEmail','/updatePassword/:id'];
  return (
    <>
    {!rotasSemNavbar.includes(location.pathname) && <NavBar />}
    <Routes>
      <Route path='/updatePassword/:id'></Route>
      <Route path = '*' element = {<Navigate to="/login"/>}></Route>
      <Route path='/password/sendEmail' element={<sendEmailPassword />}></Route>
      <Route path='/profile' element={<Perfil />}></Route>
      <Route path = '/chat/:id' element={<Chat />}></Route>
      <Route path = '/chats' element={<Chats />}></Route>
      <Route path='/register' element ={<Register />}></Route>
      <Route path='/login' element= {<Login />}></Route>
      <Route path='/home' element= {<Home />}></Route>
    </Routes>
    </>
  )
}

export default App
