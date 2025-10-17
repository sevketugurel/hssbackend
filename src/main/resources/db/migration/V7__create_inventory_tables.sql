-- V7: Create inventory tables (stock_product, stock_transaction, equipment, maintenance)

CREATE TABLE stock_product (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    barcode VARCHAR(50) UNIQUE,
    lot_no VARCHAR(50),
    production_date DATE,
    expiration_date DATE,
    min_stock INT DEFAULT 0,
    max_stock INT DEFAULT 0,
    current_stock INT DEFAULT 0,
    unit_cost DECIMAL(10,2),
    selling_price DECIMAL(10,2),
    category VARCHAR(50) DEFAULT 'GENERAL' CHECK (category IN ('MEDICINE', 'VACCINE', 'SUPPLY', 'EQUIPMENT', 'GENERAL')),
    supplier VARCHAR(100),
    location VARCHAR(100),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE stock_transaction (
    transaction_id SERIAL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES stock_product(product_id) ON DELETE RESTRICT,
    transaction_date TIMESTAMP NOT NULL,
    quantity INT NOT NULL,
    unit_cost DECIMAL(10,2),
    total_cost DECIMAL(10,2),
    type VARCHAR(10) NOT NULL CHECK (type IN ('IN', 'OUT', 'ADJUSTMENT', 'TRANSFER')),
    related_entity VARCHAR(50), -- e.g., 'PRESCRIPTION', 'SALE', 'PURCHASE'
    related_id INT, -- ID of the related entity
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE equipment (
    equipment_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    model VARCHAR(100),
    serial_no VARCHAR(100) UNIQUE,
    purchase_date DATE,
    warranty_expiry DATE,
    location VARCHAR(100),
    status VARCHAR(20) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'MAINTENANCE', 'RETIRED', 'BROKEN')),
    last_maintenance_date DATE,
    next_maintenance_date DATE,
    purchase_cost DECIMAL(10,2),
    current_value DECIMAL(10,2),
    supplier VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE maintenance (
    maintenance_id SERIAL PRIMARY KEY,
    equipment_id INT NOT NULL REFERENCES equipment(equipment_id) ON DELETE CASCADE,
    maintenance_date DATE NOT NULL,
    maintenance_type VARCHAR(50) DEFAULT 'ROUTINE' CHECK (maintenance_type IN ('ROUTINE', 'REPAIR', 'CALIBRATION', 'INSPECTION')),
    performed_by VARCHAR(100),
    cost DECIMAL(10,2),
    notes TEXT,
    next_maintenance_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- Add indexes for performance
CREATE INDEX idx_stock_product_name ON stock_product(name);
CREATE INDEX idx_stock_product_barcode ON stock_product(barcode);
CREATE INDEX idx_stock_product_category ON stock_product(category);
CREATE INDEX idx_stock_product_expiration ON stock_product(expiration_date);
CREATE INDEX idx_stock_product_active ON stock_product(is_active);
CREATE INDEX idx_stock_transaction_product_id ON stock_transaction(product_id);
CREATE INDEX idx_stock_transaction_date ON stock_transaction(transaction_date);
CREATE INDEX idx_stock_transaction_type ON stock_transaction(type);
CREATE INDEX idx_equipment_name ON equipment(name);
CREATE INDEX idx_equipment_serial ON equipment(serial_no);
CREATE INDEX idx_equipment_status ON equipment(status);
CREATE INDEX idx_equipment_location ON equipment(location);
CREATE INDEX idx_maintenance_equipment_id ON maintenance(equipment_id);
CREATE INDEX idx_maintenance_date ON maintenance(maintenance_date);
CREATE INDEX idx_maintenance_type ON maintenance(maintenance_type);

-- Add trigger to update current_stock when transactions occur
CREATE OR REPLACE FUNCTION update_stock_quantity()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.type = 'IN' THEN
        UPDATE stock_product 
        SET current_stock = current_stock + NEW.quantity
        WHERE product_id = NEW.product_id;
    ELSIF NEW.type = 'OUT' THEN
        UPDATE stock_product 
        SET current_stock = current_stock - NEW.quantity
        WHERE product_id = NEW.product_id;
    ELSIF NEW.type = 'ADJUSTMENT' THEN
        UPDATE stock_product 
        SET current_stock = NEW.quantity
        WHERE product_id = NEW.product_id;
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_stock_quantity
    AFTER INSERT ON stock_transaction
    FOR EACH ROW
    EXECUTE FUNCTION update_stock_quantity();
