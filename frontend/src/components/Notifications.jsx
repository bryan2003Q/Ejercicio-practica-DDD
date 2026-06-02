import { useState, useEffect } from 'react';

const API_BASE = 'http://localhost:8080/api/notifications';

export default function Notifications() {
  const [notifications, setNotifications] = useState([]);

  const fetchNotifications = async () => {
    try {
      const res = await fetch(`${API_BASE}`);
      const data = await res.json();
      setNotifications(data.reverse());
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchNotifications();
  }, []);

  return (
    <section className="module-card">
      <h1>🔔 Notificaciones</h1>
      <button onClick={fetchNotifications} className="btn-secondary" style={{marginBottom: '15px'}}>Refrescar Alertas</button>
      <div className="list-container">
        {notifications.length === 0 ? (
          <div className="list-item">No hay notificaciones aún.</div>
        ) : (
          notifications.map((n, i) => (
            <div key={i} className="list-item notification">
              <small>{new Date(n.date).toLocaleString()}</small><br />
              {n.message}
            </div>
          ))
        )}
      </div>
    </section>
  );
}
