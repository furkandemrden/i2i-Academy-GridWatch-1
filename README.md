- **gridwatch-core** — Ana backend. Ev/cihaz kaydı, tarife ve anomali kural motoru,
  Ignite tabanlı anlık metrik yönetimi, Gemini entegrasyonu, REST API'ler.
- **gridwatch-sensors** — Kayıtlı ev ve cihazlar için gerçekçi, periyodik telemetri
  (watt) verisi üreten, Core'dan tamamen bağımsız simülasyon servisi.
- **gridwatch-web** — React tabanlı dashboard: ev listesi, canlı animasyonlu ev
  görselleştirmesi, tüketim trend grafiği, AI tasarruf önerileri.

## Teknoloji Yığını

| Katman | Teknoloji |
|---|---|
| Backend | Java 21, Spring Boot 4, Spring Data JPA, Spring Kafka |
| Mesajlaşma | Apache Kafka (KRaft modu) |
| Bellek-içi veri | Apache Ignite (thin client) |
| Veritabanı | PostgreSQL 16 |
| AI | Google Gemini API |
| Frontend | React 19, Vite, React Router, Recharts, Axios |
| Altyapı | Docker Compose |

## Kurulum

### Ön Gereksinimler
- Java 21+
- Node.js 18+
- Docker Desktop
- Ücretsiz bir [Google Gemini API key](https://aistudio.google.com/apikey)

### 1. Altyapıyı Başlat

```bash
docker compose up -d
```

Bu komut PostgreSQL, Kafka ve Ignite container'larını ayağa kaldırır.
Veritabanı şeması (`db/init/01_schema.sql`) ilk açılışta otomatik uygulanır.

### 2. Backend'i Başlat

```bash
cd gridwatch-core
export GEMINI_API_KEY=kendi_api_key_in
./mvnw spring-boot:run
```

API varsayılan olarak `http://localhost:8081` üzerinde çalışır.
Swagger dokümantasyonu: `http://localhost:8081/swagger-ui/index.html`

### 3. Telemetri Simülatörünü Başlat

Ayrı bir terminalde:

```bash
cd gridwatch-sensors
./mvnw spring-boot:run
```

### 4. Frontend'i Başlat

Ayrı bir terminalde:

```bash
cd gridwatch-web
npm install
npm run dev
```

Uygulama `http://localhost:5173` üzerinde açılır.

## Giriş Bilgileri

Demo amaçlı basit bir giriş ekranı bulunmaktadır:

- **Kullanıcı adı:** `admin`
- **Şifre:** `admin`

## Temel Özellikler

- Yeni ev ve cihaz kaydı (REST API + form arayüzü)
- Gerçek zamanlı telemetri simülasyonu ve işleme (Kafka + Ignite)
- Bütçe kotası aşımında otomatik ceza tarifesine geçiş
- Bir cihazın güvenli limiti üst üste 3 kez aşmasında anomali tespiti
- Anomali/ceza durumunda Gemini ile Türkçe, kişiselleştirilmiş tasarruf önerisi üretimi
- Günlük tüketim geçmişi ve trend grafiği
- Canlı, animasyonlu ev görselleştirmesi (klima çalışması, tarife durumu)

---

Powered by Furkan Remzi Demirden