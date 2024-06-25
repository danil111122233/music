CREATE TABLE music_artist (
    id                  SERIAL      PRIMARY KEY,
    name                TEXT        NOT NULL,
    debut_year          INTEGER     NOT NULL,
    email               TEXT        NOT NULL,
    password            TEXT        NOT NULL
);

CREATE TABLE album (
    id                  SERIAL      PRIMARY KEY,
    music_artist_id     INTEGER     NOT NULL REFERENCES music_artist(id) ON DELETE CASCADE,
    release             DATE        NOT NULL,
    title               TEXT        NOT NULL
);

CREATE TABLE award (
    id                  SERIAL      PRIMARY KEY,
    music_artist_id     INTEGER     NOT NULL REFERENCES music_artist(id) ON DELETE CASCADE,
    year                INTEGER     NOT NULL,
    place               INTEGER     NOT NULL
);

CREATE TABLE nomination_artists (
    id                  SERIAL      PRIMARY KEY,
    music_artist_id     INTEGER     NOT NULL REFERENCES music_artist(id) ON DELETE CASCADE,
    award_id            INTEGER     NOT NULL REFERENCES award(id) ON DELETE CASCADE,
    name                TEXT        NOT NULL
);
