import styles from './ApplianceList.module.css';

export default function ApplianceList({ appliances, anomalyFlags }) {
  return (
    <div className={styles.wrap}>
      <h3 className={styles.title}>Cihazlar</h3>
      <div className={styles.list}>
        {appliances.map((appliance) => {
          const isAnomaly = anomalyFlags?.[appliance.id] === true;
          return (
            <div key={appliance.id} className={`${styles.row} ${isAnomaly ? styles.rowAnomaly : ''}`}>
              <span className={styles.name}>{appliance.name}</span>
              <span className={styles.limit}>Limit: {appliance.safeLimitWatt} W</span>
              {isAnomaly && <span className={styles.badge}>Anomali</span>}
            </div>
          );
        })}
      </div>
    </div>
  );
}