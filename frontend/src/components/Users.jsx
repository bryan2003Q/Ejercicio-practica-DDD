import { useState, useEffect } from 'react';

const API_BASE = 'http://localhost:8080/api/users';

export default function Users() {
  const [users, setUsers] = useState([]);
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');

  const fetchUsers = async () => {
    try {
      const res = await fetch(`${API_BASE}`);
      const data = await res.json();
      setUsers(data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const addUser = async () => {
    if (!name || !email) return alert('Llena todos los campos');
    await fetch(`${API_BASE}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, email })
    });
    setName('');
    setEmail('');
    fetchUsers();
  };

  return (
    <section className="module-card">
      <h1>👥 Módulo Usuarios</h1>
      <div className="form-group" style={{marginTop: '20px'}}>
        <input placeholder="Nombre" value={name} onChange={e => setName(e.target.value)} />
        <input placeholder="Email" type="email" value={email} onChange={e => setEmail(e.target.value)} />
        <button onClick={addUser} className="btn-primary">Crear Usuario</button>
      </div>
      <div className="list-container">
        {users.map((u, i) => (
          <div key={i} className="list-item">
            <strong>ID {u.id}:</strong> {u.name} ({u.email})
          </div>
        ))}
      </div>
    </section>
  );
}
