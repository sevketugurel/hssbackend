-- V6: Create staff and authentication tables (staff, user_account, role, staff_role)

CREATE TABLE staff (
    staff_id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    hire_date DATE NOT NULL,
    termination_date DATE,
    active BOOLEAN DEFAULT true,
    position VARCHAR(100),
    department VARCHAR(100),
    salary DECIMAL(10,2),
    address TEXT,
    emergency_contact_name VARCHAR(100),
    emergency_contact_phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE user_account (
    user_id SERIAL PRIMARY KEY,
    staff_id INT REFERENCES staff(staff_id) ON DELETE CASCADE,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255),
    firebase_uid VARCHAR(128) UNIQUE,
    email VARCHAR(100) UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT true,
    last_login TIMESTAMP,
    login_attempts INT DEFAULT 0,
    locked_until TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE role (
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    permissions TEXT[], -- Array of permission strings
    is_system_role BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE staff_role (
    staff_id INT REFERENCES staff(staff_id) ON DELETE CASCADE,
    role_id INT REFERENCES role(role_id) ON DELETE CASCADE,
    assigned_date DATE DEFAULT CURRENT_DATE,
    assigned_by VARCHAR(100),
    PRIMARY KEY (staff_id, role_id)
);

-- Add indexes for performance
CREATE INDEX idx_staff_email ON staff(email);
CREATE INDEX idx_staff_active ON staff(active);
CREATE INDEX idx_staff_department ON staff(department);
CREATE INDEX idx_user_account_staff_id ON user_account(staff_id);
CREATE INDEX idx_user_account_username ON user_account(username);
CREATE INDEX idx_user_account_firebase_uid ON user_account(firebase_uid);
CREATE INDEX idx_user_account_email ON user_account(email);
CREATE INDEX idx_user_account_active ON user_account(is_active);
CREATE INDEX idx_role_name ON role(name);
CREATE INDEX idx_staff_role_staff_id ON staff_role(staff_id);
CREATE INDEX idx_staff_role_role_id ON staff_role(role_id);

-- Insert default roles
INSERT INTO role (name, description, permissions, is_system_role) VALUES
('ADMIN', 'System Administrator', ARRAY['*'], true),
('VETERINARIAN', 'Veterinarian', ARRAY['ANIMAL_READ', 'ANIMAL_WRITE', 'APPOINTMENT_READ', 'APPOINTMENT_WRITE', 'MEDICAL_READ', 'MEDICAL_WRITE', 'PRESCRIPTION_READ', 'PRESCRIPTION_WRITE'], true),
('STAFF', 'General Staff', ARRAY['ANIMAL_READ', 'APPOINTMENT_READ', 'APPOINTMENT_WRITE', 'OWNER_READ', 'OWNER_WRITE'], true),
('RECEPTIONIST', 'Receptionist', ARRAY['ANIMAL_READ', 'APPOINTMENT_READ', 'APPOINTMENT_WRITE', 'OWNER_READ', 'OWNER_WRITE', 'INVOICE_READ', 'INVOICE_WRITE'], true);
