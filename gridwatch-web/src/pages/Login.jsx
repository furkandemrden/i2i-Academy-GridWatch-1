import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import styles from './Login.module.css';

export default function Login() {
  const [username, setUsername] = useState('admin');
  const [password, setPassword] = useState('admin');
  const [error, setError] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    const success = login(username, password);
    if (success) {
      navigate('/');
    } else {
      setError('Kullanıcı adı veya şifre hatalı.');
    }
  };

  return (
    <div className={styles.page}>
      <div className={styles.gridLines} />

      <div className={styles.card}>
        <div className={styles.brand}>
          <span className={styles.brandMark}>⚡</span>
          <h1 className={styles.brandName}>GridWatch</h1>
        </div>
        <p className={styles.tagline}>Enerji izleme ve tasarruf paneli</p>

        <form onSubmit={handleSubmit} className={styles.form}>
          <label className={styles.label}>
            Kullanıcı Adı
            <input
              className={styles.input}
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              autoFocus
            />
          </label>

          <label className={styles.label}>
            Şifre
            <input
              className={styles.input}
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </label>

          {error && <p className={styles.error}>{error}</p>}

          <button type="submit" className={styles.submitButton}>
            Giriş Yap
          </button>
        </form>
      </div>

      <p className={styles.footer}>Powered by Furkan Remzi Demirden</p>
    </div>
  );
}