package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class ShipService {

    @Autowired
    private ShipRepository shipRepository;

    public List<Ship> getAllShips() {
        return shipRepository.findAll();
    }

    public List<Ship> getSortedShipList(List<Ship> ships, ShipOrder shipOrder, Integer pageNumber, Integer pageSize) {
        ships.sort(new Comparator<Ship>() {
            @Override
            public int compare(Ship o1, Ship o2) {
                if (shipOrder == ShipOrder.ID) {
                    return o1.getId().compareTo(o2.getId());
                }
                if (shipOrder == ShipOrder.SPEED) {
                    return o1.getSpeed().compareTo(o2.getSpeed());
                }
                if (shipOrder == ShipOrder.DATE) {
                    return o1.getProdDate().compareTo(o2.getProdDate());
                } else {
                    return o1.getRating().compareTo(o2.getRating());
                }
            }
        });
        if (pageSize * pageNumber > ships.size()) {
            return null;
        }
        List<Ship> filteredShips = new ArrayList<>();
        for (int i = pageSize * pageNumber; i < ships.size() && i < pageSize * (pageNumber + 1); i++) {
            filteredShips.add(ships.get(i));
        }
        return filteredShips;
    }

    public List<Ship> getFilteredShipList(String name, String planet, ShipType shipType,
                                          Long after, Long before, Boolean isUsed,
                                          Integer minCrewSize, Integer maxCrewSize,
                                          Double minSpeed, Double maxSpeed, Double minRating,
                                          Double maxRating) {
        List<Ship> ships = shipRepository.findAllByNameContainsAndPlanetContains(name == null ? "" : name, planet == null ? "" : planet);
        List<Ship> filteredShipList = new ArrayList<>();
        Date afterDate = new Date();
        Date beforeDate = new Date();
        if (after != null) {
            afterDate.setTime(after);
        }
        if (before != null) {
            beforeDate.setTime(before);
        }
        for (Ship ship : ships) {
            if ((shipType != null && shipType != ship.getShipType()) ||
                    (after != null && afterDate.getYear() >= ship.getProdDate().getYear()) ||
                    (before != null && beforeDate.getYear() <= ship.getProdDate().getYear()) ||
                    (isUsed != null && ship.getUsed() != isUsed) ||
                    (minCrewSize != null && minCrewSize > ship.getCrewSize()) ||
                    (maxCrewSize != null && maxCrewSize < ship.getCrewSize()) ||
                    (minSpeed != null && minSpeed > ship.getSpeed()) ||
                    (maxSpeed != null && maxSpeed < ship.getSpeed()) ||
                    (minRating != null && minRating > ship.getRating()) ||
                    (maxRating != null && maxRating < ship.getRating())) {
                continue;
            }
            filteredShipList.add(ship);
        }

        return filteredShipList;
    }
}
