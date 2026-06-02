import { useState, useEffect } from 'react';

const API_BASE = 'http://localhost:8080/api/books';

export default function Books() {
  const [books, setBooks] = useState([]);
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [rentBookId, setRentBookId] = useState('');
  const [rentUserId, setRentUserId] = useState('');

  const fetchBooks = async () => {
    try {
      const res = await fetch(`${API_BASE}`);
      const data = await res.json();
      setBooks(data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  const addBook = async () => {
    if (!title || !author) return alert('Llena todos los campos');
    await fetch(`${API_BASE}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ title, author })
    });
    setTitle('');
    setAuthor('');
    fetchBooks();
  };

  const rentBook = async () => {
    if (!rentBookId || !rentUserId) return alert('ID de libro y usuario necesarios');
    const res = await fetch(`${API_BASE}/${rentBookId}/rent?userId=${rentUserId}`, {
      method: 'POST'
    });
    const text = await res.text();
    if (!res.ok) alert("Error: " + text);
    else {
      alert("Éxito: " + text + "\nRevisa Notificaciones");
      fetchBooks();
    }
  };

  return (
    <>
      <section className="module-card">
        <h1>📚 Módulo Libros</h1>
        <div className="form-group" style={{marginTop: '20px'}}>
          <input placeholder="Título" value={title} onChange={e => setTitle(e.target.value)} />
          <input placeholder="Autor" value={author} onChange={e => setAuthor(e.target.value)} />
          <button onClick={addBook} className="btn-primary">Crear Libro</button>
        </div>
        <div className="list-container" style={{marginBottom: '20px'}}>
          {books.map((b, i) => (
            <div key={i} className="list-item">
              <strong>ID {b.id}:</strong> {b.title} <br />
              <small>Estado: {b.status} {b.rentedByUserId && `(User: ${b.rentedByUserId})`}</small>
            </div>
          ))}
        </div>
      </section>

      <section className="module-card">
        <h2>Prestar Libro (Acoplamiento)</h2>
        <div className="form-group">
          <input placeholder="ID Libro" type="number" value={rentBookId} onChange={e => setRentBookId(e.target.value)} />
          <input placeholder="ID Usuario" type="number" value={rentUserId} onChange={e => setRentUserId(e.target.value)} />
          <button onClick={rentBook} className="btn-secondary">Prestar y Notificar</button>
        </div>
      </section>
    </>
  );
}
