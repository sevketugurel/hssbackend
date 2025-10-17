-- V1: Create core tables (species, breed, owner, animal)

CREATE TABLE species (
    species_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE breed (
    breed_id SERIAL PRIMARY KEY,
    species_id INT REFERENCES species(species_id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE owner (
    owner_id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE animal (
    animal_id SERIAL PRIMARY KEY,
    owner_id INT REFERENCES owner(owner_id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    species_id INT REFERENCES species(species_id) ON DELETE RESTRICT,
    breed_id INT REFERENCES breed(breed_id) ON DELETE RESTRICT,
    gender VARCHAR(10) CHECK (gender IN ('MALE', 'FEMALE', 'UNKNOWN')),
    birth_date DATE,
    weight DECIMAL(5,2),
    color VARCHAR(50),
    microchip_no VARCHAR(50) UNIQUE,
    allergies TEXT,
    chronic_diseases TEXT,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- Add indexes for performance
CREATE INDEX idx_animal_owner_id ON animal(owner_id);
CREATE INDEX idx_animal_species_id ON animal(species_id);
CREATE INDEX idx_animal_breed_id ON animal(breed_id);
CREATE INDEX idx_animal_microchip ON animal(microchip_no);
CREATE INDEX idx_breed_species_id ON breed(species_id);
CREATE INDEX idx_owner_email ON owner(email);
CREATE INDEX idx_owner_phone ON owner(phone);
