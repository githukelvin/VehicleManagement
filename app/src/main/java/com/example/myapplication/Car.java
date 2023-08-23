package com.example.myapplication;

public class Car {
    String carType,location,rate,plate,model;

    public Car() {
    }

    public Car(String carType, String location, String rate, String plate, String model) {
        this.carType = carType;
        this.location = location;
        this.rate = rate;
        this.plate = plate;
        this.model = model;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
