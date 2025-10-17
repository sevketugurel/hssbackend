-- V4: Create billing tables (invoice, invoice_item)

CREATE TABLE invoice (
    invoice_id SERIAL PRIMARY KEY,
    owner_id INT REFERENCES owner(owner_id) ON DELETE CASCADE,
    invoice_number VARCHAR(50) UNIQUE NOT NULL,
    date DATE NOT NULL,
    due_date DATE,
    amount DECIMAL(10,2) NOT NULL DEFAULT 0,
    tax_amount DECIMAL(10,2) DEFAULT 0,
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0,
    status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'PAID', 'OVERDUE', 'CANCELLED', 'REFUNDED')),
    payment_method VARCHAR(50),
    payment_date DATE,
    description TEXT,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE invoice_item (
    invoice_item_id SERIAL PRIMARY KEY,
    invoice_id INT REFERENCES invoice(invoice_id) ON DELETE CASCADE,
    description VARCHAR(200) NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10,2) NOT NULL,
    vat_rate DECIMAL(5,2) DEFAULT 0,
    vat_amount DECIMAL(10,2) DEFAULT 0,
    line_total DECIMAL(10,2) NOT NULL,
    item_type VARCHAR(50) DEFAULT 'SERVICE' CHECK (item_type IN ('SERVICE', 'MEDICINE', 'VACCINE', 'CONSULTATION', 'SURGERY', 'LAB_TEST', 'OTHER')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- Add indexes for performance
CREATE INDEX idx_invoice_owner_id ON invoice(owner_id);
CREATE INDEX idx_invoice_date ON invoice(date);
CREATE INDEX idx_invoice_status ON invoice(status);
CREATE INDEX idx_invoice_number ON invoice(invoice_number);
CREATE INDEX idx_invoice_due_date ON invoice(due_date);
CREATE INDEX idx_invoice_item_invoice_id ON invoice_item(invoice_id);
CREATE INDEX idx_invoice_item_type ON invoice_item(item_type);

-- Add trigger to calculate invoice totals
CREATE OR REPLACE FUNCTION calculate_invoice_totals()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE invoice 
    SET 
        amount = COALESCE((
            SELECT SUM(line_total) 
            FROM invoice_item 
            WHERE invoice_id = NEW.invoice_id
        ), 0),
        tax_amount = COALESCE((
            SELECT SUM(vat_amount) 
            FROM invoice_item 
            WHERE invoice_id = NEW.invoice_id
        ), 0),
        total_amount = COALESCE((
            SELECT SUM(line_total) 
            FROM invoice_item 
            WHERE invoice_id = NEW.invoice_id
        ), 0)
    WHERE invoice_id = NEW.invoice_id;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_calculate_invoice_totals
    AFTER INSERT OR UPDATE OR DELETE ON invoice_item
    FOR EACH ROW
    EXECUTE FUNCTION calculate_invoice_totals();
