package com.slotmind.service.impl;

import com.slotmind.model.Appointment;
import com.slotmind.model.AppointmentStatus;
import com.slotmind.model.Provider;
import com.slotmind.repository.AppointmentRepository;
import com.slotmind.repository.ProviderRepository;
import com.slotmind.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private ProviderRepository providerRepository;
    
    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    @Override
    public Optional<Appointment> getAppointmentById(String id) {
        return appointmentRepository.findById(id);
    }
    
    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    
    @Override
    public Appointment updateAppointment(String id, Appointment appointmentDetails) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointment.setPatientName(appointmentDetails.getPatientName());
            appointment.setAppointmentType(appointmentDetails.getAppointmentType());
            appointment.setProvider(appointmentDetails.getProvider());
            appointment.setSlot(appointmentDetails.getSlot());
            appointment.setStatus(appointmentDetails.getStatus());
            return appointmentRepository.save(appointment);
        }
        return null;
    }
    
    @Override
    public void deleteAppointment(String id) {
        appointmentRepository.deleteById(id);
    }
    
    @Override
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }
    
    @Override
    public List<Appointment> getAppointmentsByProvider(String providerId) {
        Optional<Provider> providerOptional = providerRepository.findById(providerId);
        if (providerOptional.isPresent()) {
            Provider provider = providerOptional.get();
            return appointmentRepository.findByProviderAndStatus(provider, AppointmentStatus.CONFIRMED);
        }
        return List.of();
    }
}