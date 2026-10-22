package com.slotmind.repository;

import com.slotmind.model.Appointment;
import com.slotmind.model.AppointmentStatus;
import com.slotmind.model.Provider;
import com.slotmind.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    List<Appointment> findByStatus(AppointmentStatus status);
    List<Appointment> findByProviderAndStatus(Provider provider, AppointmentStatus status);
    List<Appointment> findBySlotAndStatus(Slot slot, AppointmentStatus status);
}