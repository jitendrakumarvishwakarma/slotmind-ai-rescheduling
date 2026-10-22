package com.slotmind.controller;

import com.slotmind.model.Slot;
import com.slotmind.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin(origins = "http://localhost:4200")
public class SlotController {
    
    @Autowired
    private SlotService slotService;
    
    @GetMapping
    public List<Slot> getAllSlots() {
        return slotService.getAllSlots();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Slot> getSlotById(@PathVariable String id) {
        Optional<Slot> slot = slotService.getSlotById(id);
        if (slot.isPresent()) {
            return ResponseEntity.ok(slot.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public Slot createSlot(@RequestBody Slot slot) {
        return slotService.createSlot(slot);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Slot> updateSlot(@PathVariable String id, @RequestBody Slot slotDetails) {
        Slot updatedSlot = slotService.updateSlot(id, slotDetails);
        if (updatedSlot != null) {
            return ResponseEntity.ok(updatedSlot);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable String id) {
        slotService.deleteSlot(id);
        return ResponseEntity.noContent().build();
    }
}