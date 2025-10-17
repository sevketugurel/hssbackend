-- V2: Create medical tables (medical_history, clinical_examination, appointment, lab_tests, lab_results, radiological_imaging)

CREATE TABLE medical_history (
    history_id SERIAL PRIMARY KEY,
    animal_id INT REFERENCES animal(animal_id) ON DELETE CASCADE,
    diagnosis TEXT NOT NULL,
    date DATE NOT NULL,
    treatment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE clinical_examination (
    examination_id SERIAL PRIMARY KEY,
    animal_id INT REFERENCES animal(animal_id) ON DELETE CASCADE,
    date DATE NOT NULL,
    findings TEXT,
    veterinarian_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE appointment (
    appointment_id SERIAL PRIMARY KEY,
    animal_id INT REFERENCES animal(animal_id) ON DELETE CASCADE,
    date_time TIMESTAMP NOT NULL,
    subject TEXT,
    veterinarian_id INT,
    status VARCHAR(20) DEFAULT 'SCHEDULED' CHECK (status IN ('SCHEDULED', 'CONFIRMED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED', 'NO_SHOW')),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE lab_tests (
    test_id SERIAL PRIMARY KEY,
    animal_id INT REFERENCES animal(animal_id) ON DELETE CASCADE,
    test_name VARCHAR(100) NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE lab_results (
    result_id SERIAL PRIMARY KEY,
    test_id INT REFERENCES lab_tests(test_id) ON DELETE CASCADE,
    result TEXT,
    value VARCHAR(100),
    unit VARCHAR(20),
    normal_range VARCHAR(50),
    interpretation TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE radiological_imaging (
    image_id SERIAL PRIMARY KEY,
    animal_id INT REFERENCES animal(animal_id) ON DELETE CASCADE,
    date DATE NOT NULL,
    type VARCHAR(50) NOT NULL,
    image_url TEXT,
    comment TEXT,
    file_size BIGINT,
    mime_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- Add indexes for performance
CREATE INDEX idx_medical_history_animal_id ON medical_history(animal_id);
CREATE INDEX idx_medical_history_date ON medical_history(date);
CREATE INDEX idx_clinical_examination_animal_id ON clinical_examination(animal_id);
CREATE INDEX idx_clinical_examination_date ON clinical_examination(date);
CREATE INDEX idx_appointment_animal_id ON appointment(animal_id);
CREATE INDEX idx_appointment_date_time ON appointment(date_time);
CREATE INDEX idx_appointment_veterinarian_id ON appointment(veterinarian_id);
CREATE INDEX idx_appointment_status ON appointment(status);
CREATE INDEX idx_lab_tests_animal_id ON lab_tests(animal_id);
CREATE INDEX idx_lab_tests_date ON lab_tests(date);
CREATE INDEX idx_lab_tests_status ON lab_tests(status);
CREATE INDEX idx_lab_results_test_id ON lab_results(test_id);
CREATE INDEX idx_radiological_imaging_animal_id ON radiological_imaging(animal_id);
CREATE INDEX idx_radiological_imaging_date ON radiological_imaging(date);
CREATE INDEX idx_radiological_imaging_type ON radiological_imaging(type);
