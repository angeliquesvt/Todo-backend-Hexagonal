CREATE TABLE IF NOT EXISTS Todo (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(250) NOT NULL,
    isCompleted boolean DEFAULT FALSE,
    rank integer NOT NULL
);