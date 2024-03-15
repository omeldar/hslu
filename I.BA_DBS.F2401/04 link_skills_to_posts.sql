DO $$
DECLARE
    rec RECORD;
    _skill_id UUID;
BEGIN

    CREATE INDEX ON import_job_skills (job_link);
    CREATE INDEX ON linkedin_posts (job_link);

    -- Step 1: Create a new table for unique skills
    CREATE TABLE unique_job_skills (
        skill_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
        skill_name TEXT UNIQUE
    );

    -- Step 2: Create an associative table to map skills to linkedin_posts
    CREATE TABLE linkedin_post_skills (
        post_id UUID, -- Assuming the linkedin_posts table has a UUID field named post_id
        skill_id UUID,
        PRIMARY KEY (post_id, skill_id),
        FOREIGN KEY (post_id) REFERENCES linkedin_posts (id) ON DELETE CASCADE,
        FOREIGN KEY (skill_id) REFERENCES unique_job_skills (skill_id) ON DELETE CASCADE
    );

    -- Step 3: Populate the unique_skills table with unique skills
    -- Insert unique skills and create associations in a loop
    FOR rec IN SELECT DISTINCT unnest(string_to_array(job_skills, ',')) AS skill, job_link
        FROM import_job_skills -- Replace with the actual table name
    LOOP
        -- Trim whitespace from the skill name
        rec.skill := TRIM(rec.skill);

        -- Attempt to insert the skill if it doesn't exist
        INSERT INTO unique_job_skills (skill_name)
        VALUES (rec.skill)
        ON CONFLICT (skill_name) DO NOTHING;

        -- Retrieve the skill_id
        SELECT skill_id INTO _skill_id FROM unique_job_skills WHERE skill_name = rec.skill;

        -- Insert the association into the junction table, with the correct post_id from linkedin_posts
        INSERT INTO linkedin_post_skills (post_id, skill_id)
        SELECT id, _skill_id
        FROM linkedin_posts
        WHERE job_link = rec.job_link
        ON CONFLICT DO NOTHING;
    END LOOP;
END;
$$;
