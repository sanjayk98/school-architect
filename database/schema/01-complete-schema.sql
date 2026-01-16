-- School Architect Database Schema
CREATE EXTENSION IF NOT EXISTS vector;

CREATE TYPE fsc_status AS ENUM ('Draft', 'ConfigInProgress', 'RegistrationOpen', 'Closed');
CREATE TYPE week_type AS ENUM ('Instructional', 'Exam', 'Holiday');
CREATE TYPE staff_role AS ENUM ('PRIMARY', 'SUPPORT', 'VOLUNTEER');

CREATE TABLE academic_batch (
    batch_id UUID PRIMARY KEY,
    batch_year INT NOT NULL,
    duration_start DATE NOT NULL,
    duration_end DATE NOT NULL,
    fsc_status fsc_status NOT NULL DEFAULT 'Draft',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE school_site (
    site_id UUID PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    address_line1 TEXT NOT NULL,
    city TEXT NOT NULL,
    state TEXT NOT NULL,
    postal_code TEXT NOT NULL,
    is_online BOOLEAN NOT NULL DEFAULT FALSE,
    is_payable BOOLEAN NOT NULL DEFAULT FALSE,
    embedding vector(1536),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE instructional_offering (
    offering_id UUID PRIMARY KEY,
    batch_id UUID NOT NULL REFERENCES academic_batch(batch_id),
    site_id UUID NOT NULL REFERENCES school_site(site_id),
    grade_id UUID NOT NULL,
    tuition_fee_amount NUMERIC(12,2) NOT NULL,
    tuition_fee_currency TEXT NOT NULL DEFAULT 'USD',
    max_total_capacity INT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE section (
    section_id UUID PRIMARY KEY,
    offering_id UUID NOT NULL REFERENCES instructional_offering(offering_id) ON DELETE CASCADE,
    label TEXT NOT NULL,
    section_capacity INT NOT NULL,
    status TEXT NOT NULL DEFAULT 'PLANNED',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE staff_assignment (
    staff_assignment_id UUID PRIMARY KEY,
    section_id UUID NOT NULL REFERENCES section(section_id) ON DELETE CASCADE,
    user_id UUID NOT NULL,
    role staff_role NOT NULL,
    compliance_status TEXT NOT NULL,
    assigned_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Capacity enforcement trigger
CREATE OR REPLACE FUNCTION trg_check_offering_capacity()
RETURNS TRIGGER AS $$
DECLARE
    v_total INT;
    v_max INT;
BEGIN
    SELECT max_total_capacity INTO v_max
    FROM instructional_offering
    WHERE offering_id = COALESCE(NEW.offering_id, OLD.offering_id);
    
    SELECT COALESCE(SUM(section_capacity), 0) INTO v_total
    FROM section
    WHERE offering_id = COALESCE(NEW.offering_id, OLD.offering_id);
    
    IF v_total > v_max THEN
        RAISE EXCEPTION 'Capacity exceeded: % > %', v_total, v_max;
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_section_capacity
AFTER INSERT OR UPDATE OR DELETE ON section
FOR EACH ROW EXECUTE FUNCTION trg_check_offering_capacity();

-- Sample data
INSERT INTO academic_batch VALUES 
    ('11111111-1111-1111-1111-111111111111', 2026, '2026-01-01', '2026-12-31', 'Draft'),
    ('22222222-2222-2222-2222-222222222222', 2025, '2025-01-01', '2025-12-31', 'RegistrationOpen');

INSERT INTO school_site (site_id, name, address_line1, city, state, postal_code) VALUES
    ('33333333-3333-3333-3333-333333333333', 'Pleasanton Campus', '123 Main St', 'Pleasanton', 'CA', '94566'),
    ('44444444-4444-4444-4444-444444444444', 'Virtual Campus', 'Online', 'Virtual', 'CA', '00000');
