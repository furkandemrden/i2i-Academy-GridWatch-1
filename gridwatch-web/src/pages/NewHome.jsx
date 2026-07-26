import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { registerHome } from '../api/homes';
import styles from './NewHome.module.css';

const emptyAppliance = () => ({ name: '', safeLimitWatt: '' });

export default function NewHome() {
  const [name, setName] = useState('');
  const [contactEmail, setContactEmail] = useState('');
  const [budgetQuota, setBudgetQuota] = useState('');
  const [normalRate, setNormalRate] = useState('');
  const [penaltyRate, setPenaltyRate] = useState('');
  const [appliances, setAppliances] = useState([emptyAppliance()]);
  const [error, setError] = useState('');
  const [submitting, setSubmitting] = useState(false);
  const navigate = useNavigate();

  const updateAppliance = (index, field, value) => {
    setAppliances((prev) =>
      prev.map((a, i) => (i === index ? { ...a, [field]: value } : a))
    );
  };

  const addAppliance = () => setAppliances((prev) => [...prev, emptyAppliance()]);

  const removeAppliance = (index) =>
    setAppliances((prev) => prev.filter((_, i) => i !== index));

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSubmitting(true);

    try {
      const payload = {
        name,
        contactEmail,
        budgetQuota: parseFloat(budgetQuota),
        normalRate: parseFloat(normalRate),
        penaltyRate: parseFloat(penaltyRate),
        appliances: appliances.map((a) => ({
          name: a.name,
          safeLimitWatt: parseFloat(a.safeLimitWatt),
        })),
      };
      const created = await registerHome(payload);
      navigate(`/homes/${created.id}`);
    } catch (err) {
      setError('Ev oluşturulamadı. Bilgileri kontrol edip tekrar deneyin.');
      setSubmitting(false);
    }
  };

  return (
    <div className={styles.page}>
      <button className={styles.backButton} onClick={() => navigate('/')}>
        ← Geri
      </button>

      <div className={styles.card}>
        <h1 className={styles.title}>Yeni Ev Kaydet</h1>
        <p className={styles.subtitle}>
          Ev bilgilerini ve izlenecek cihazları gir.
        </p>

        <form onSubmit={handleSubmit} className={styles.form}>
          <div className={styles.row}>
            <label className={styles.label}>
              Ev Adı
              <input
                className={styles.input}
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </label>
            <label className={styles.label}>
              İletişim E-postası
              <input
                className={styles.input}
                type="email"
                value={contactEmail}
                onChange={(e) => setContactEmail(e.target.value)}
                required
              />
            </label>
          </div>

          <div className={styles.row}>
            <label className={styles.label}>
              Bütçe Kotası
              <input
                className={styles.input}
                type="number"
                step="0.01"
                value={budgetQuota}
                onChange={(e) => setBudgetQuota(e.target.value)}
                required
              />
            </label>
            <label className={styles.label}>
              Normal Tarife
              <input
                className={styles.input}
                type="number"
                step="0.0001"
                value={normalRate}
                onChange={(e) => setNormalRate(e.target.value)}
                required
              />
            </label>
            <label className={styles.label}>
              Ceza Tarifesi
              <input
                className={styles.input}
                type="number"
                step="0.0001"
                value={penaltyRate}
                onChange={(e) => setPenaltyRate(e.target.value)}
                required
              />
            </label>
          </div>

          <div className={styles.applianceSection}>
            <div className={styles.applianceSectionHeader}>
              <h3 className={styles.applianceTitle}>Cihazlar</h3>
              <button type="button" className={styles.addButton} onClick={addAppliance}>
                + Cihaz Ekle
              </button>
            </div>

            {appliances.map((appliance, index) => (
              <div key={index} className={styles.applianceRow}>
                <input
                  className={styles.input}
                  placeholder="Cihaz adı (örn. Klima)"
                  value={appliance.name}
                  onChange={(e) => updateAppliance(index, 'name', e.target.value)}
                  required
                />
                <input
                  className={styles.input}
                  type="number"
                  step="0.01"
                  placeholder="Güvenli limit (W)"
                  value={appliance.safeLimitWatt}
                  onChange={(e) => updateAppliance(index, 'safeLimitWatt', e.target.value)}
                  required
                />
                {appliances.length > 1 && (
                  <button
                    type="button"
                    className={styles.removeButton}
                    onClick={() => removeAppliance(index)}
                  >
                    ✕
                  </button>
                )}
              </div>
            ))}
          </div>

          {error && <p className={styles.error}>{error}</p>}

          <button type="submit" className={styles.submitButton} disabled={submitting}>
            {submitting ? 'Kaydediliyor...' : 'Evi Kaydet'}
          </button>
        </form>
      </div>
    </div>
  );
}