-- V8: Create system tables (reminder, report_schedule)

CREATE TABLE reminder (
    reminder_id SERIAL PRIMARY KEY,
    appointment_id INT NOT NULL REFERENCES appointment(appointment_id) ON DELETE CASCADE,
    send_time TIMESTAMP NOT NULL,
    channel VARCHAR(10) NOT NULL CHECK (channel IN ('EMAIL', 'SMS', 'PUSH')),
    status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'SENT', 'DELIVERED', 'FAILED', 'CANCELLED')),
    message TEXT,
    recipient_email VARCHAR(100),
    recipient_phone VARCHAR(20),
    sent_at TIMESTAMP,
    error_message TEXT,
    retry_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE report_schedule (
    report_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    frequency VARCHAR(20) NOT NULL CHECK (frequency IN ('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY', 'CUSTOM')),
    cron_expression VARCHAR(100),
    last_run TIMESTAMP,
    next_run TIMESTAMP,
    is_active BOOLEAN DEFAULT true,
    report_type VARCHAR(50) DEFAULT 'GENERAL' CHECK (report_type IN ('GENERAL', 'FINANCIAL', 'MEDICAL', 'INVENTORY', 'APPOINTMENT')),
    parameters JSONB, -- Store report parameters as JSON
    email_recipients TEXT[], -- Array of email addresses
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- Add indexes for performance
CREATE INDEX idx_reminder_appointment_id ON reminder(appointment_id);
CREATE INDEX idx_reminder_send_time ON reminder(send_time);
CREATE INDEX idx_reminder_status ON reminder(status);
CREATE INDEX idx_reminder_channel ON reminder(channel);
CREATE INDEX idx_report_schedule_active ON report_schedule(is_active);
CREATE INDEX idx_report_schedule_frequency ON report_schedule(frequency);
CREATE INDEX idx_report_schedule_next_run ON report_schedule(next_run);
CREATE INDEX idx_report_schedule_type ON report_schedule(report_type);

-- Insert default report schedules
INSERT INTO report_schedule (name, description, frequency, cron_expression, report_type, is_active) VALUES
('Daily Appointment Summary', 'Daily summary of appointments', 'DAILY', '0 8 * * *', 'APPOINTMENT', true),
('Weekly Financial Report', 'Weekly financial summary', 'WEEKLY', '0 9 * * 1', 'FINANCIAL', true),
('Monthly Inventory Report', 'Monthly inventory status', 'MONTHLY', '0 10 1 * *', 'INVENTORY', true),
('Quarterly Medical Statistics', 'Quarterly medical statistics', 'QUARTERLY', '0 9 1 1,4,7,10 *', 'MEDICAL', true);
