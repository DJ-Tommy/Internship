package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipDao;

import java.util.List;

public class ShipService {

    private ShipDao shipDao;

    public List<Ship> getAllShips() {
        return shipDao.getAllShips();
    }
}
