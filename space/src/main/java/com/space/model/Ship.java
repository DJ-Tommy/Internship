package com.space.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Entity
//@Table(name = "ship")
public class Ship {
    @Id
    @GeneratedValue(generator = "increment")
    private Long id;            //Ship ID
    private String name;        //Ship name (maximum of 50 characters)
    private String planet;      //Parking planet (maximum of 50 characters)
    @Enumerated(EnumType.STRING)
    private ShipType shipType;  //Type of the Ship
    private Date prodDate;      //Release date (should be between 2800 and 3019)
    private Boolean isUsed;     //The Ship is Used or New?
    private Double speed;       //The max Ship speed (between 0,01 and 0,99. Need use Math rounding to two decimal places)
    private Integer crewSize;   //Number of employees (from 1 to 9999)
    private Double rating;      //(Need rounding to two decimal places)

    public Ship() {
    }

    public Ship(String name, String planet, ShipType shipType, Date prodDate, Boolean isUsed, Double speed, Integer crewSize) {
        setName(name);
        setPlanet(planet);
        setShipType(shipType);
        setProdDate(prodDate);
        setIsUsed(isUsed);
        setSpeed(speed);
        setCrewSize(crewSize);
        updateRating();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getIsUsed() {
        return isUsed;
    }

    public Boolean isUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
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

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void updateRating() {
        Date date = new Date();
        double k;
        if (isUsed == null || !isUsed) {
            k = 1.0;
        } else {
            k = 0.5;
        }
        int yearNow = 3019 - 1900;
        rating = 80 * speed * k / (double) (yearNow - prodDate.getYear() + 1.0);
        BigDecimal bd = new BigDecimal(Double.toString(rating));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        rating = bd.doubleValue();
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", planet='" + planet + '\'' +
                ", shipType=" + shipType +
                ", prodDate=" + prodDate +
                ", isUsed=" + isUsed +
                ", speed=" + speed +
                ", crewSize=" + crewSize +
                ", rating=" + rating +
                '}';
    }
}
