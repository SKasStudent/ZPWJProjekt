CREATE TABLE IF NOT EXISTS drzewa (
                                      id BIGSERIAL PRIMARY KEY,
                                      gatunek VARCHAR(100) NOT NULL,
    wysokosc DOUBLE PRECISION NOT NULL,
    srednica DOUBLE PRECISION NOT NULL,
    sciete BOOLEAN NOT NULL DEFAULT FALSE,
    aktywny BOOLEAN NOT NULL DEFAULT TRUE
    );

CREATE TABLE IF NOT EXISTS pracownicy (
                                          id BIGSERIAL PRIMARY KEY,
                                          imie VARCHAR(100) NOT NULL,
    stanowisko VARCHAR(100) NOT NULL,
    pensja DOUBLE PRECISION NOT NULL,
    aktywny BOOLEAN NOT NULL DEFAULT TRUE
    );

CREATE TABLE IF NOT EXISTS uzytkownicy (
                                           id BIGSERIAL PRIMARY KEY,
                                           login VARCHAR(100) NOT NULL UNIQUE,
    haslo_hash VARCHAR(255) NOT NULL,
    rola VARCHAR(20) NOT NULL DEFAULT 'USER'
    );