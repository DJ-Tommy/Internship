package com.space.model;

import java.util.Date;

public class Ship {
    private Long id;            //Ship ID
    private String name;        //Ship name (maximum of 50 characters)
    private String planet;      //Parking planet (maximum of 50 characters)
    private ShipType shipType;  //Type of the Ship
    private Date prodDate;      //Release date (should be between 2800 and 3019)
    private Boolean isUsed;     //The Ship is Used or New?
    private Double speed;       //The max Ship speed (between 0,01 and 0,99. Need use Math rounding to two decimal places)
    private Integer crewSize;   //Number of employees (from 1 to 9999)
    private Double rating;      //(Need rounding to two decimal places)

    public Ship() {
    }

    public Ship(Long id, String name, String planet, ShipType shipType, Date prodDate, Boolean isUsed, Double speed, Integer crewSize) {
        setName(name);
        setPlanet(planet);
        setShipType(shipType);
        setProdDate(prodDate);
        setUsed(isUsed);
        setSpeed(speed);
        setCrewSize(crewSize);
        createID();
        countRating();
    }

    public Long getId() {
        return id;
    }

    private void createID() {
        id = 3L;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    private void countRating() {
        Date date = new Date();
        double k = isUsed ? 0.5 : 1.0;
        int yearNow = date.getYear() + 1000;
        rating = 80 * speed * k / (yearNow - prodDate.getYear() + 1);
    }
}
