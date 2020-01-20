package com.space.repository;

import com.space.config.AppConfig;
import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShipDao {

    private AppConfig appConfig;

    private List<Ship> ships;

    public ShipDao() {
        ships = generateShips();
        try {
            Connection connection = appConfig.dataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Ship> generateShips() {
        List<Ship> testListShips = new ArrayList<>();
        ShipType[] shipTypes = ShipType.values();
        for (int i = 1; i < 20; i++) {
            Date date = new Date();
            date.setYear(1000);
            date.setTime(date.getTime() + i * 20_000_000_000L);
            boolean isUsed;
            if (i % 3 == 0) {
                isUsed = false;
            } else {
                isUsed = true;
            }
            testListShips.add(new Ship("Ship" + i, "planet" + i % 4,
                    shipTypes[i % shipTypes.length], date, isUsed, 0.04 * i, i * 33));
        }
        for (Ship ship : testListShips) {
            System.out.println(ship);
        }
        return testListShips;
    }


    public List<Ship> getAllShips() {
        return ships;
    }

}
