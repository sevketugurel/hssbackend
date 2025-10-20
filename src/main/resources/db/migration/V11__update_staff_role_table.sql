-- V11: Update staff_role table to use single primary key

-- Drop existing staff_role table
DROP TABLE IF EXISTS staff_role CASCADE;

-- Recreate staff_role table with single primary key
CREATE TABLE staff_role (
    staff_role_id SERIAL PRIMARY KEY,
    staff_id INT REFERENCES staff(staff_id) ON DELETE CASCADE,
    role_id INT REFERENCES role(role_id) ON DELETE CASCADE,
    assigned_date DATE DEFAULT CURRENT_DATE,
    assigned_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    UNIQUE(staff_id, role_id)
);

-- Add indexes for performance
CREATE INDEX idx_staff_role_staff_id ON staff_role(staff_id);
CREATE INDEX idx_staff_role_role_id ON staff_role(role_id);
