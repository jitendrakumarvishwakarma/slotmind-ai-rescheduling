package com.slotmind.repository;

import com.slotmind.model.Slot;
import com.slotmind.model.SlotStatus;
import com.slotmind.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, String> {
    List<Slot> findByStatus(SlotStatus status);
    List<Slot> findByProviderAndStatusOrderByStartTimeAsc(Provider provider, SlotStatus status);
    List<Slot> findByStatusOrderByStartTimeAsc(SlotStatus status);
    
    @Query("SELECT s FROM Slot s WHERE s.provider = :provider AND s.status = :status AND s.startTime > :currentTime ORDER BY s.startTime ASC")
    List<Slot> findOpenSlotsForProviderAfterTime(@Param("provider") Provider provider, 
                                                @Param("status") SlotStatus status,
                                                @Param("currentTime") LocalDateTime currentTime);
                                                
    @Query("SELECT s FROM Slot s WHERE s.provider.specialty = :specialty AND s.status = :status AND s.startTime > :currentTime ORDER BY s.startTime ASC")
    List<Slot> findOpenSlotsBySpecialtyAfterTime(@Param("specialty") String specialty,
                                                @Param("status") SlotStatus status,
                                                @Param("currentTime") LocalDateTime currentTime);
}