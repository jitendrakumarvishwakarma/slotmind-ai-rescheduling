INSERT INTO providers (id, name, specialty, status) VALUES
  ('PRV-001', 'Dr. Sarah Chen',    'GENERAL',   'AVAILABLE'),
  ('PRV-002', 'Dr. James Wilson',  'PEDIATRIC', 'AVAILABLE'),
  ('PRV-003', 'Dr. Priya Sharma',  'PEDIATRIC', 'BUSY'),
  ('PRV-004', 'Dr. Rahul Verma',   'GENERAL',   'AVAILABLE'),
  ('PRV-005', 'Dr. Ananya Iyer',   'SPECIALIST','BUSY');

INSERT INTO slots (id, provider_id, start_time, duration_minutes, status) VALUES
  ('SLT-001', 'PRV-001', CURRENT_TIMESTAMP() + INTERVAL '60' MINUTE,  30, 'BOOKED'),
  ('SLT-002', 'PRV-001', CURRENT_TIMESTAMP() + INTERVAL '120' MINUTE, 30, 'BOOKED'),
  ('SLT-003', 'PRV-003', CURRENT_TIMESTAMP() + INTERVAL '90' MINUTE,  45, 'BOOKED'),
  ('SLT-004', 'PRV-002', CURRENT_TIMESTAMP() + INTERVAL '180' MINUTE, 30, 'OPEN'),
  ('SLT-005', 'PRV-004', CURRENT_TIMESTAMP() + INTERVAL '150' MINUTE, 30, 'OPEN'),
  ('SLT-006', 'PRV-005', CURRENT_TIMESTAMP() + INTERVAL '240' MINUTE, 60, 'BOOKED');

INSERT INTO appointments (id, patient_name, appointment_type, provider_id, slot_id, status, created_at) VALUES
  ('APT-001', 'John Martinez',  'ROUTINE',    'PRV-001', 'SLT-001', 'CONFIRMED', CURRENT_TIMESTAMP()),
  ('APT-002', 'Emily Watson',   'FOLLOW_UP',  'PRV-001', 'SLT-002', 'CONFIRMED', CURRENT_TIMESTAMP()),
  ('APT-003', 'Aisha Patel',    'URGENT',     'PRV-003', 'SLT-003', 'CONFIRMED', CURRENT_TIMESTAMP()),
  ('APT-004', 'Robert Kim',     'ROUTINE',    'PRV-005', 'SLT-006', 'CONFIRMED', CURRENT_TIMESTAMP());