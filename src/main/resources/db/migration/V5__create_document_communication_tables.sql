-- V5: Create document and communication tables (document, communication)

CREATE TABLE document (
    document_id SERIAL PRIMARY KEY,
    owner_id INT REFERENCES owner(owner_id) ON DELETE CASCADE,
    animal_id INT REFERENCES animal(animal_id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    document_type VARCHAR(50) DEFAULT 'GENERAL' CHECK (document_type IN ('GENERAL', 'CERTIFICATE', 'REPORT', 'CONSENT', 'CONTRACT', 'OTHER')),
    file_url TEXT,
    file_name VARCHAR(255),
    file_size BIGINT,
    mime_type VARCHAR(50),
    date DATE NOT NULL,
    is_archived BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE communication (
    communication_id SERIAL PRIMARY KEY,
    owner_id INT REFERENCES owner(owner_id) ON DELETE CASCADE,
    subject VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    date TIMESTAMP NOT NULL,
    communication_type VARCHAR(20) DEFAULT 'EMAIL' CHECK (communication_type IN ('EMAIL', 'SMS', 'PHONE', 'LETTER', 'IN_PERSON')),
    status VARCHAR(20) DEFAULT 'SENT' CHECK (status IN ('DRAFT', 'SENT', 'DELIVERED', 'READ', 'FAILED')),
    priority VARCHAR(10) DEFAULT 'NORMAL' CHECK (priority IN ('LOW', 'NORMAL', 'HIGH', 'URGENT')),
    response_required BOOLEAN DEFAULT false,
    response_received BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- Add indexes for performance
CREATE INDEX idx_document_owner_id ON document(owner_id);
CREATE INDEX idx_document_animal_id ON document(animal_id);
CREATE INDEX idx_document_type ON document(document_type);
CREATE INDEX idx_document_date ON document(date);
CREATE INDEX idx_document_archived ON document(is_archived);
CREATE INDEX idx_communication_owner_id ON communication(owner_id);
CREATE INDEX idx_communication_date ON communication(date);
CREATE INDEX idx_communication_type ON communication(communication_type);
CREATE INDEX idx_communication_status ON communication(status);
CREATE INDEX idx_communication_priority ON communication(priority);
