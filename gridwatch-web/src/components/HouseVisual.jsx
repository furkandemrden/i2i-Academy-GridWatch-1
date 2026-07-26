import styles from './HouseVisual.module.css';

export default function HouseVisual({ penaltyActive, hasAnomaly }) {
  return (
    <div className={styles.wrap}>
      <svg viewBox="0 0 240 200" className={styles.house}>
        {/* Çatı */}
        <path
          d="M20 90 L120 20 L220 90"
          fill="none"
          stroke="currentColor"
          strokeWidth="4"
          strokeLinejoin="round"
          strokeLinecap="round"
          className={styles.roofLine}
        />
        {/* Gövde */}
        <rect x="35" y="90" width="170" height="95" rx="4" className={styles.body} />

        {/* Pencereler */}
        <rect x="55" y="112" width="34" height="34" rx="3" className={`${styles.window} ${penaltyActive ? styles.windowPenalty : ''}`} />
        <rect x="151" y="112" width="34" height="34" rx="3" className={`${styles.window} ${penaltyActive ? styles.windowPenalty : ''}`} />

        {/* Kapı */}
        <rect x="103" y="140" width="34" height="45" rx="2" className={styles.door} />
        <circle cx="129" cy="163" r="2" className={styles.doorknob} />

        {/* Baca */}
        <rect x="165" y="40" width="16" height="35" className={styles.chimney} />

        {/* Klima ünitesi (dış duvarda) */}
        <g className={styles.acUnit}>
          <rect x="35" y="118" width="16" height="22" rx="2" fill="#2a3a52" stroke="currentColor" strokeWidth="1.5" />
          {/* Soğuk hava parçacıkları */}
          <g className={styles.airPuffs}>
            <circle cx="20" cy="122" r="2.5" className={styles.puff1} />
            <circle cx="14" cy="128" r="2" className={styles.puff2} />
            <circle cx="20" cy="134" r="2.5" className={styles.puff3} />
          </g>
        </g>

        {/* Anomali uyarı ikonu */}
        {hasAnomaly && (
          <g className={styles.warningIcon}>
            <circle cx="200" cy="55" r="14" fill="#D97A3F" />
            <text x="200" y="60" textAnchor="middle" fontSize="16" fontWeight="700" fill="#0F1B2D">!</text>
          </g>
        )}
      </svg>

      <div className={styles.statusLabel}>
        <span className={`${styles.dot} ${penaltyActive ? styles.dotPenalty : styles.dotNormal}`} />
        {penaltyActive ? 'Ceza Tarifesi Aktif' : 'Normal Tüketim'}
      </div>
    </div>
  );
}