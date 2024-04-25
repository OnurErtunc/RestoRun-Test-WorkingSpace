package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.DiningTable;
import com.restorun.backendapplication.model.Restaurant;
import com.restorun.backendapplication.repository.DiningTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class DiningTableService {
    private final DiningTableRepository diningTableRepository;

    @Autowired
    public DiningTableService(DiningTableRepository diningTableRepository) {
        this.diningTableRepository = diningTableRepository;
    }

    public boolean saveDiningTable(DiningTable diningTable) {
        diningTableRepository.save(diningTable);
        return true;
    }

    public boolean deleteDiningTable(Long id) {
        Optional<DiningTable> diningTable = diningTableRepository.findById(id);
        if (diningTable.isPresent()) {
            diningTableRepository.delete(diningTable.get());
            return true;  // Successfully deleted
        }
        return false;  // No restaurant found to delete
    }

    public Optional<DiningTable> retrieveDiningTableById(Long id) {
        return diningTableRepository.findById(id);
    }

    public boolean updateDiningTable(DiningTable diningTable) {
        return diningTableRepository.findById(diningTable.getId())
                .map(existingDiningTable -> {
                    existingDiningTable.setReservation(existingDiningTable.getReservation());
                    existingDiningTable.setSeatingCapacity(existingDiningTable.getSeatingCapacity());
                    existingDiningTable.setTableNumber(existingDiningTable.getTableNumber());
                    diningTableRepository.save(existingDiningTable);

                    return true; // Indicates success
                })
                .orElse(false); // Indicates failure
    }

    public List<DiningTable> retrieveAllDiningTables() {
        return diningTableRepository.findAll();
    }
}
