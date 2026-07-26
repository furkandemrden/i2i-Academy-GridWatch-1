import styles from './RecommendationsList.module.css';

export default function RecommendationsList({ recommendations }) {
  return (
    <div className={styles.wrap}>
      <h3 className={styles.title}>AI Tasarruf Önerileri</h3>
      {recommendations.length === 0 ? (
        <p className={styles.empty}>Henüz bir öneri üretilmedi.</p>
      ) : (
        <div className={styles.list}>
          {recommendations.map((rec) => (
            <div key={rec.id} className={styles.item}>
              <p className={styles.text}>{rec.recommendationText}</p>
              <span className={styles.date}>
                {new Date(rec.createdAt).toLocaleString('tr-TR')}
              </span>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}