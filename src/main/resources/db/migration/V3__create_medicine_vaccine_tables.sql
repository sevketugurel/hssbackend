-- V3: Create medicine and vaccine tables (medicine, prescription, vaccine, vaccination_record, pathology_findings)

CREATE TABLE medicine (
    medicine_id SERIAL PRIMARY KEY,
    medicine_name VARCHAR(100) NOT NULL,
    active_substance VARCHAR(100),
    usage_area VARCHAR(100),
    administration_route VARCHAR(50),
    dosage_form VARCHAR(50),
    strength VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE prescription (
    prescription_id SERIAL PRIMARY KEY,
    animal_id INT REFERENCES animal(animal_id) ON DELETE CASCADE,
    medicine_id INT REFERENCES medicine(medicine_id) ON DELETE RESTRICT,
    date DATE NOT NULL,
    medicines TEXT NOT NULL,
    dosage TEXT NOT NULL,
    instructions TEXT,
    duration_days INT,
    status VARCHAR(20) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'COMPLETED', 'CANCELLED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE vaccine (
    vaccine_id SERIAL PRIMARY KEY,
    vaccine_name VARCHAR(100) NOT NULL,
    administration_route VARCHAR(50),
    protection_period INTERVAL,
    age_requirement_months INT,
    booster_required BOOLEAN DEFAULT false,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE vaccination_record (
    vaccination_record_id SERIAL PRIMARY KEY,
    animal_id INT REFERENCES animal(animal_id) ON DELETE CASCADE,
    vaccine_id INT REFERENCES vaccine(vaccine_id) ON DELETE RESTRICT,
    vaccine_name VARCHAR(100) NOT NULL,
    date DATE NOT NULL,
    next_due_date DATE,
    batch_number VARCHAR(50),
    veterinarian_name VARCHAR(100),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE pathology_findings (
    pathology_id SERIAL PRIMARY KEY,
    animal_id INT REFERENCES animal(animal_id) ON DELETE CASCADE,
    report TEXT NOT NULL,
    date DATE NOT NULL,
    pathologist_name VARCHAR(100),
    findings_summary TEXT,
    recommendations TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- Add indexes for performance
CREATE INDEX idx_prescription_animal_id ON prescription(animal_id);
CREATE INDEX idx_prescription_medicine_id ON prescription(medicine_id);
CREATE INDEX idx_prescription_date ON prescription(date);
CREATE INDEX idx_prescription_status ON prescription(status);
CREATE INDEX idx_vaccination_record_animal_id ON vaccination_record(animal_id);
CREATE INDEX idx_vaccination_record_vaccine_id ON vaccination_record(vaccine_id);
CREATE INDEX idx_vaccination_record_date ON vaccination_record(date);
CREATE INDEX idx_vaccination_record_next_due ON vaccination_record(next_due_date);
CREATE INDEX idx_pathology_findings_animal_id ON pathology_findings(animal_id);
CREATE INDEX idx_pathology_findings_date ON pathology_findings(date);
CREATE INDEX idx_medicine_name ON medicine(medicine_name);
CREATE INDEX idx_vaccine_name ON vaccine(vaccine_name);
