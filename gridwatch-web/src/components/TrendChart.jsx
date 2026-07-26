import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import styles from './TrendChart.module.css';

export default function TrendChart({ data }) {
  const chartData = data.map((d) => ({
    date: d.date,
    watt: Number(d.totalWatt),
    cost: Number(d.totalCost),
  }));

  if (chartData.length === 0) {
    return (
      <div className={styles.wrap}>
        <h3 className={styles.title}>Tüketim Geçmişi</h3>
        <p className={styles.empty}>Henüz yeterli veri birikmedi.</p>
      </div>
    );
  }

  return (
    <div className={styles.wrap}>
      <h3 className={styles.title}>Tüketim Geçmişi</h3>
      <ResponsiveContainer width="100%" height={220}>
        <LineChart data={chartData}>
          <CartesianGrid strokeDasharray="3 3" stroke="rgba(158, 169, 183, 0.1)" />
          <XAxis dataKey="date" stroke="#9CA9B7" fontSize={11} />
          <YAxis stroke="#9CA9B7" fontSize={11} />
          <Tooltip
            contentStyle={{
              background: '#16263D',
              border: '1px solid rgba(158, 169, 183, 0.2)',
              borderRadius: 8,
              fontSize: 12,
            }}
          />
          <Line type="monotone" dataKey="watt" stroke="#3E7CB1" strokeWidth={2} dot={false} name="Watt" />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}