import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { listHomes, getHomeStatus } from '../api/homes';
import { useAuth } from '../context/AuthContext';
import HomeCard from '../components/HomeCard';
import styles from './HomeList.module.css';

export default function HomeList() {
  const [homes, setHomes] = useState([]);
  const [statuses, setStatuses] = useState({});
  const [loading, setLoading] = useState(true);
  const { logout } = useAuth();
  const navigate = useNavigate();

  async function loadData() {
    const homesData = await listHomes();
    setHomes(homesData);

    const statusEntries = await Promise.all(
      homesData.map(async (h) => {
        try {
          const s = await getHomeStatus(h.id);
          return [h.id, s];
        } catch {
          return [h.id, null];
        }
      })
    );
    setStatuses(Object.fromEntries(statusEntries));
    setLoading(false);
  }

  useEffect(() => {
    loadData();
    const interval = setInterval(loadData, 5000);
    return () => clearInterval(interval);
  }, []);

  const totalHomes = homes.length;
  const penaltyCount = Object.values(statuses).filter((s) => s?.penaltyActive).length;
  const totalWatt = Object.values(statuses).reduce((sum, s) => sum + Number(s?.accumulatedWatt ?? 0), 0);

  return (
    <div className={styles.page}>
      <header className={styles.header}>
        <div className={styles.brand}>
          <span className={styles.brandMark}>⚡</span>
          <h1 className={styles.brandName}>GridWatch</h1>
        </div>
        <div className={styles.actions}>
          <button className={styles.newButton} onClick={() => navigate('/homes/new')}>
            + Yeni Ev
          </button>
          <button className={styles.logoutButton} onClick={logout}>
            Çıkış
          </button>
        </div>
      </header>

      {!loading && totalHomes > 0 && (
        <div className={styles.summaryStrip}>
          <div className={styles.summaryItem}>
            <span className={styles.summaryValue}>{totalHomes}</span>
            <span className={styles.summaryLabel}>Kayıtlı Ev</span>
          </div>
          <div className={styles.summaryDivider} />
          <div className={styles.summaryItem}>
            <span className={`${styles.summaryValue} ${penaltyCount > 0 ? styles.summaryValueWarn : ''}`}>
              {penaltyCount}
            </span>
            <span className={styles.summaryLabel}>Ceza Tarifesinde</span>
          </div>
          <div className={styles.summaryDivider} />
          <div className={styles.summaryItem}>
            <span className={styles.summaryValue}>{totalWatt.toFixed(0)} W</span>
            <span className={styles.summaryLabel}>Toplam Anlık Tüketim</span>
          </div>
        </div>
      )}

      {loading ? (
        <p className={styles.loading}>Yükleniyor...</p>
      ) : homes.length === 0 ? (
        <p className={styles.empty}>Henüz kayıtlı ev yok. "+ Yeni Ev" ile başlayın.</p>
      ) : (
        <div className={styles.grid}>
          {homes.map((h) => (
            <HomeCard key={h.id} home={h} status={statuses[h.id]} />
          ))}
        </div>
      )}

      <p className={styles.footer}>Powered by Furkan Remzi Demirden</p>
    </div>
  );
}