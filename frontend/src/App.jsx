import { useState } from 'react'
import Users from './components/Users'
import Books from './components/Books'
import Notifications from './components/Notifications'

function App() {
  const [currentModule, setCurrentModule] = useState('home')

  const goHome = () => setCurrentModule('home')

  return (
    <>
      <div className="background-shapes">
        <div className="shape shape-1"></div>
        <div className="shape shape-2"></div>
        <div className="shape shape-3"></div>
      </div>
      
      <main className="glass-container">
        {currentModule !== 'home' && (
          <div className="back-link" onClick={goHome}>← Volver al Menú</div>
        )}

        {currentModule === 'home' && (
          <div className="text-center">
            <header>
              <h1>Bienvenido al Sistema React</h1>
              <p>Selecciona el módulo al que deseas acceder:</p>
            </header>
            <div className="nav-menu">
              <div className="nav-card" onClick={() => setCurrentModule('users')}>
                <h2>👥 Usuarios</h2>
                <p>Gestionar miembros</p>
              </div>
              <div className="nav-card" onClick={() => setCurrentModule('books')}>
                <h2>📚 Libros</h2>
                <p>Catálogo y Préstamos</p>
              </div>
              <div className="nav-card" onClick={() => setCurrentModule('notifications')}>
                <h2>🔔 Notificaciones</h2>
                <p>Historial de alertas</p>
              </div>
            </div>
          </div>
        )}

        {currentModule === 'users' && <Users />}
        {currentModule === 'books' && <Books />}
        {currentModule === 'notifications' && <Notifications />}
      </main>
    </>
  )
}

export default App
