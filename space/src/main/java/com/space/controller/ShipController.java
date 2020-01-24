package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShipController {

    @Autowired
    private ShipService shipService;

    @GetMapping("/rest/ships")
    public List<Ship> getAll(@RequestParam(name = "name", required = false) String name,
                             @RequestParam(name = "planet", required = false) String planet,
                             @RequestParam(name = "shipType", required = false) ShipType shipType,
                             @RequestParam(name = "after", required = false) Long after,
                             @RequestParam(name = "before", required = false) Long before,
                             @RequestParam(name = "isUsed", required = false) Boolean isUsed,
                             @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
                             @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
                             @RequestParam(name = "minSpeed", required = false) Double minSpeed,
                             @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
                             @RequestParam(name = "minRating", required = false) Double minRating,
                             @RequestParam(name = "maxRating", required = false) Double maxRating,
                             @RequestParam(name = "order", required = false, defaultValue = "ID") ShipOrder order,
                             @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                             @RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize) {
        List<Ship> ships = shipService.getFilteredShipList(name, planet, shipType, after, before,
                isUsed, minCrewSize, maxCrewSize, minSpeed, maxSpeed, minRating, maxRating);
        return shipService.getSortedShipList(ships, order, pageNumber, pageSize);
    }

    @GetMapping("/rest/ships/count")
    public Integer getCount(@RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "planet", required = false) String planet,
                            @RequestParam(name = "shipType", required = false) ShipType shipType,
                            @RequestParam(name = "after", required = false) Long after,
                            @RequestParam(name = "before", required = false) Long before,
                            @RequestParam(name = "isUsed", required = false) Boolean isUsed,
                            @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
                            @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
                            @RequestParam(name = "minSpeed", required = false) Double minSpeed,
                            @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
                            @RequestParam(name = "minRating", required = false) Double minRating,
                            @RequestParam(name = "maxRating", required = false) Double maxRating) {
        List<Ship> ships = shipService.getFilteredShipList(name, planet, shipType, after, before,
                isUsed, minCrewSize, maxCrewSize, minSpeed, maxSpeed, minRating, maxRating);
        return ships.size();
    }

    @GetMapping("/rest/ships/{id}")
    public Ship getShipById(@PathVariable Long id) {
        return checkId(id);
    }

    @PostMapping("/rest/ships/{id}")
    public Ship updateShip(@PathVariable Long id, @RequestBody Ship ship) {
        checkId(id);
        Ship editedShip = shipService.updateShip(id, ship.getName(), ship.getPlanet(), ship.getShipType(), ship.getProdDate() == null? null : ship.getProdDate().getTime(), ship.getUsed(), ship.getSpeed(), ship.getCrewSize());
        System.out.println("EditedShip: " + editedShip);
        if (editedShip == null) {
            throw new BadRequestException();
        }
        return editedShip;
    }

    @DeleteMapping("/rest/ships/{id}")
    public void deleteShip(@PathVariable Long id) {
        checkId(id);
        if (!shipService.deleteShip(id)) {
            throw new BadRequestException();
        }
    }

    @PostMapping("/rest/ships")
    public Ship createShip(@RequestBody Ship ship) {
        System.out.println("Ship for adding: " + ship);
        Ship createdShip = shipService.createShip(ship);
        if (createdShip == null) {
            throw new BadRequestException();
        } else {
            System.out.println("Created ship is: " + createdShip);
            return createdShip;
        }
    }


    private Ship checkId(Long id) {
        if (id < 1) {
            throw new BadRequestException();
        } else {
            Ship ship = shipService.getShipById(id);
            if (ship != null) {
                return ship;
            }
        }
        throw new NotFoundException();
    }


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public class BadRequestException extends RuntimeException {
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public class NotFoundException extends RuntimeException {
    }
}
