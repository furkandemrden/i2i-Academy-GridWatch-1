import { useNavigate } from 'react-router-dom';
import styles from './HomeCard.module.css';

export default function HomeCard({ home, status }) {
  const navigate = useNavigate();
  const isPenalty = status?.penaltyActive ?? false;
  const watt = status?.accumulatedWatt ?? 0;

  return (
    <div
      className={`${styles.card} ${isPenalty ? styles.penalty : ''}`}
      onClick={() => navigate(`/homes/${home.id}`)}
    >
      <div className={styles.iconWrap}>
        <svg viewBox="0 0 64 64" className={styles.houseIcon}>
          <path
            d="M32 6 L58 26 V56 H6 V26 Z"
            fill="none"
            stroke="currentColor"
            strokeWidth="2.5"
            strokeLinejoin="round"
          />
          <rect x="26" y="38" width="12" height="18" fill="currentColor" opacity="0.15" />
          <circle className={styles.statusDot} cx="32" cy="20" r="3" />
        </svg>
      </div>

      <div className={styles.info}>
        <h3 className={styles.name}>{home.name}</h3>
        <p className={styles.email}>{home.contactEmail}</p>
      </div>

      <div className={styles.metrics}>
        <span className={styles.watt}>{watt.toFixed(0)} W</span>
        <span className={`${styles.badge} ${isPenalty ? styles.badgePenalty : styles.badgeNormal}`}>
          {isPenalty ? 'Ceza Tarifesi' : 'Normal'}
        </span>
      </div>
    </div>
  );
}