BEGIN;

-- Step 1: Create a new table for job summaries with a UUID primary key
CREATE TABLE job_summaries (
    summary_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    job_link TEXT NOT NULL UNIQUE,
    summary TEXT
);

-- Step 2: Insert data from the existing summary table into the new table
INSERT INTO job_summaries (job_link, summary)
SELECT job_link, job_summary
FROM import_job_summary; -- Replace with the actual name of your existing job summary table

-- Step 3: Add a UUID column to the linkedin_posts table to hold the foreign key
ALTER TABLE linkedin_posts
ADD COLUMN summary_id UUID;

-- Step 4: Update the linkedin_posts table to set the foreign key reference
UPDATE linkedin_posts
SET summary_id = job_summaries.summary_id
FROM job_summaries
WHERE linkedin_posts.job_link = job_summaries.job_link;

-- Step 5: Add the foreign key constraint to the linkedin_posts table
ALTER TABLE linkedin_posts
ADD CONSTRAINT fk_summary_id
FOREIGN KEY (summary_id)
REFERENCES job_summaries (summary_id)
ON DELETE SET NULL ON UPDATE CASCADE;

COMMIT;