package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;

@Controller
public class ShipController {

    @Autowired
    private ShipService shipService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getShips() {
//        model.put("table",shipService.getAllShips());
        List<Ship> ships = shipService.getAll();
        for (Ship ship : ships) {
            System.out.println(ship);
        }
        return new ResponseEntity<>(ships, HttpStatus.FOUND);
    }
    @RequestMapping(method = RequestMethod.POST)
    public String addShip(@ModelAttribute Ship ship, Model model) {
        return "index";
    }

}
