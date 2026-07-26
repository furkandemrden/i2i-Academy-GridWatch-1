import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  getHomeStatus,
  getHomeTrend,
  getHomeAppliances,
  getHomeRecommendations,
} from '../api/homes';
import HouseVisual from '../components/HouseVisual';
import ApplianceList from '../components/ApplianceList';
import TrendChart from '../components/TrendChart';
import RecommendationsList from '../components/RecommendationsList';
import styles from './HomeDetail.module.css';

export default function HomeDetail() {
  const { homeId } = useParams();
  const navigate = useNavigate();
  const [status, setStatus] = useState(null);
  const [trend, setTrend] = useState([]);
  const [appliances, setAppliances] = useState([]);
  const [recommendations, setRecommendations] = useState([]);
  const [loading, setLoading] = useState(true);

  async function loadData() {
    const [statusData, trendData, appliancesData, recommendationsData] = await Promise.all([
      getHomeStatus(homeId),
      getHomeTrend(homeId),
      getHomeAppliances(homeId),
      getHomeRecommendations(homeId),
    ]);
    setStatus(statusData);
    setTrend(trendData);
    setAppliances(appliancesData);
    setRecommendations(recommendationsData);
    setLoading(false);
  }

  useEffect(() => {
    loadData();
    const interval = setInterval(loadData, 5000);
    return () => clearInterval(interval);
  }, [homeId]);

  if (loading) {
    return <div className={styles.loading}>Yükleniyor...</div>;
  }

  const hasAnomaly = Object.values(status?.applianceAnomalyFlags ?? {}).some((v) => v === true);

  return (
    <div className={styles.page}>
      <button className={styles.backButton} onClick={() => navigate('/')}>
        ← Ev Listesi
      </button>

      <div className={styles.grid}>
        <div className={styles.leftColumn}>
          <div className={styles.visualCard}>
            <HouseVisual penaltyActive={status?.penaltyActive} hasAnomaly={hasAnomaly} />
          </div>

          <div className={styles.metricsRow}>
            <div className={styles.metricBox}>
              <span className={styles.metricLabel}>Toplam Tüketim</span>
              <span className={styles.metricValue}>
                {Number(status?.accumulatedWatt ?? 0).toFixed(0)} W
              </span>
            </div>
            <div className={styles.metricBox}>
              <span className={styles.metricLabel}>Toplam Maliyet</span>
              <span className={styles.metricValue}>
                {Number(status?.accumulatedCost ?? 0).toFixed(2)}
              </span>
            </div>
          </div>

          <ApplianceList appliances={appliances} anomalyFlags={status?.applianceAnomalyFlags} />
        </div>

        <div className={styles.rightColumn}>
          <TrendChart data={trend} />
          <RecommendationsList recommendations={recommendations} />
        </div>
      </div>
    </div>
  );
}