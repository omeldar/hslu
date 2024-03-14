BEGIN;

-- Create extension for UUID generation if not exists
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create new table
CREATE TABLE linkedin_posts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    job_link TEXT,
    last_processed_time TIMESTAMPTZ,
    got_summary BOOLEAN,
    got_ner BOOLEAN,
    is_being_worked BOOLEAN,
    job_title TEXT,
    company TEXT,
    job_location TEXT,
    first_seen TIMESTAMPTZ,
    search_city TEXT,
    search_country TEXT,
    search_position TEXT,
    job_level TEXT,
    job_type TEXT
);

-- Insert data from old table to new table with proper data types
INSERT INTO linkedin_posts (
    job_link,
    last_processed_time,
    got_summary,
    got_ner,
    is_being_worked,
    job_title,
    company,
    job_location,
    first_seen,
    search_city,
    search_country,
    search_position,
    job_level,
    job_type
)
SELECT
    job_link,
    last_processed_time::TIMESTAMPTZ,
    (got_summary = 't')::BOOLEAN,
    (got_ner = 't')::BOOLEAN,
    (is_being_worked = 't')::BOOLEAN,
    job_title,
    company,
    job_location,
    first_seen::TIMESTAMPTZ,
    search_city,
    search_country,
    search_position,
    job_level,
    job_type
FROM
    joblisting; -- Replace with your actual old table name

DELETE FROM import_job_summary
WHERE ctid IN (
    SELECT ctid
    FROM (
        SELECT ctid, ROW_NUMBER() OVER (PARTITION BY job_link ORDER BY ctid) as rn
        FROM import_job_summary
    ) t
    WHERE rn > 1
);

COMMIT;