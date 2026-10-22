package com.slotmind.service;

import com.slotmind.model.Appointment;
import com.slotmind.model.AppointmentStatus;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<Appointment> getAllAppointments();
    Optional<Appointment> getAppointmentById(String id);
    Appointment createAppointment(Appointment appointment);
    Appointment updateAppointment(String id, Appointment appointmentDetails);
    void deleteAppointment(String id);
    List<Appointment> getAppointmentsByStatus(AppointmentStatus status);
    List<Appointment> getAppointmentsByProvider(String providerId);
}