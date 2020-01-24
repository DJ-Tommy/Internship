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

    public Ship getShipById(Long id) {
        return shipRepository.findFirstById(id);
    }

    private Boolean checkParams(String name, String planet, Long prodDate,
                                Double speed, Integer crewSize) {

        if ((name != null && (name.length() < 1 || name.length() > 50)) ||
                (planet != null && (planet.length() < 1 || planet.length() > 50)) ||
                (prodDate != null && prodDate < 1) ||
                (speed != null && (speed < 0.01 || speed > 0.99)) ||
                (crewSize != null && (crewSize < 1 || crewSize > 9999))
        ) {
            return false;
        }
        return true;
    }

    public Ship updateShip(Long id, String name, String planet, ShipType shipType, Long prodDate,
                           Boolean isUsed, Double speed, Integer crewSize) {
        Ship ship = getShipById(id);

        if (name == null && planet == null && shipType == null && prodDate == null
                && isUsed == null && speed == null && crewSize == null) {
            return ship;
        }
        ship.setId(id);

        if (!checkParams(name, planet, prodDate, speed, crewSize)) {
            return null;
        }
        if (name != null) {
            ship.setName(name);
        }

        if (planet != null) {
            ship.setPlanet(planet);
        }

        if (shipType != null) {
            ship.setShipType(shipType);
        }

        if (prodDate != null) {
            Date date = new Date();
            date.setTime(prodDate);
            ship.setProdDate(date);
        }

        if (isUsed != null) {
            ship.setIsUsed(isUsed);
        }

        if (speed != null) {
            ship.setSpeed(speed);
        }

        if (crewSize != null) {
            ship.setCrewSize(crewSize);
        }

        ship.updateRating();
        shipRepository.save(ship);
        return ship;
    }

    public Boolean deleteShip(Long id) {
        try {
            shipRepository.delete(getShipById(id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Ship createShip(Ship ship) {
        if (ship.getName() == null || ship.getPlanet() == null ||
                ship.getShipType() == null || ship.getProdDate() == null ||
                ship.getSpeed() == null || ship.getCrewSize() == null ||
                !checkParams(ship.getName(), ship.getPlanet(), ship.getProdDate().getTime(),
                        ship.getSpeed(), ship.getCrewSize())) {
            System.out.println("The ship contains incorrect parameters");
            return null;
        }
        if (ship.getUsed() == null) {
            ship.setIsUsed(false);
        }
        ship.updateRating();
        ship = shipRepository.save(ship);

        System.out.println("Was created ship: " + ship);
        ship.setId(41L);
        return ship;
    }
}
