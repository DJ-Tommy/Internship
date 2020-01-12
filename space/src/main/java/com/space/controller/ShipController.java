package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipService;

import java.util.List;

public class ShipController {

    private ShipService shipService;

    public List<Ship> getAllUsers() {
        return shipService.getAllShips();
    }
}
