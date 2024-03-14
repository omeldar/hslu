BEGIN;

CREATE TABLE joblisting (
    job_link VARCHAR(255),
    last_processed_time VARCHAR(255),
    got_summary VARCHAR(255),
    got_ner VARCHAR(255),
    is_being_worked VARCHAR(255),
    job_title VARCHAR(255),
    company VARCHAR(255),
    job_location VARCHAR(255),
    first_seen TIMESTAMP,
    search_city VARCHAR(255),
    search_country VARCHAR(255),
    search_position VARCHAR(255),
    job_level VARCHAR(255),
    job_type VARCHAR(255)
);

CREATE TABLE import_job_summary (
    job_link VARCHAR(255),
    job_summary VARCHAR(511)
);

CREATE TABLE import_job_skills (
    job_link VARCHAR(255),
    job_skills VARCHAR(511)
);

COMMIT;