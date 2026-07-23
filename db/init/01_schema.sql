-- GridWatch Database Schema
-- Executed automatically on first Postgres container initialization

CREATE TABLE homes (
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(150) NOT NULL,
    contact_email   VARCHAR(255) NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE appliances (
    id              SERIAL PRIMARY KEY,
    home_id         INTEGER NOT NULL REFERENCES homes(id) ON DELETE CASCADE,
    name            VARCHAR(150) NOT NULL,
    safe_limit_watt NUMERIC(10, 2) NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE billing_accounts (
    id                 SERIAL PRIMARY KEY,
    home_id            INTEGER NOT NULL UNIQUE REFERENCES homes(id) ON DELETE CASCADE,
    budget_quota       NUMERIC(12, 2) NOT NULL,
    normal_rate        NUMERIC(10, 4) NOT NULL,
    penalty_rate       NUMERIC(10, 4) NOT NULL,
    is_penalty_active  BOOLEAN NOT NULL DEFAULT FALSE,
    created_at         TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE event_logs (
    id          SERIAL PRIMARY KEY,
    home_id     INTEGER NOT NULL REFERENCES homes(id) ON DELETE CASCADE,
    event_type  VARCHAR(50) NOT NULL,
    details     JSONB,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE consumption_snapshots (
    id             SERIAL PRIMARY KEY,
    home_id        INTEGER NOT NULL REFERENCES homes(id) ON DELETE CASCADE,
    snapshot_date  DATE NOT NULL,
    total_watt     NUMERIC(12, 2) NOT NULL,
    total_cost     NUMERIC(12, 2) NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    UNIQUE (home_id, snapshot_date)
);

CREATE TABLE ai_recommendations (
    id                    SERIAL PRIMARY KEY,
    home_id               INTEGER NOT NULL REFERENCES homes(id) ON DELETE CASCADE,
    recommendation_text   TEXT NOT NULL,
    created_at            TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_appliances_home_id ON appliances(home_id);
CREATE INDEX idx_event_logs_home_id ON event_logs(home_id);
CREATE INDEX idx_consumption_snapshots_home_id ON consumption_snapshots(home_id);
CREATE INDEX idx_ai_recommendations_home_id ON ai_recommendations(home_id);
