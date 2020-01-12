package com.space.repository;

import com.space.model.Ship;

import java.util.List;

public class ShipDao {

    private List<Ship> ships; // = Arrays.asList(new Ship(), new Ship());

    public List<Ship> getAllShips() {
        return ships;
    }

}
