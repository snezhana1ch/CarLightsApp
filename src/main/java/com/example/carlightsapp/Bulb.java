package com.example.carlightsapp;

public class Bulb {
    private String position;
    private double watt;
    private double voltage;

    public Bulb(String position, double watt, double voltage) {
        this.position = position;
        this.watt = watt;
        this.voltage = voltage;
    }

    public Bulb() {

    }
    public String getPosition() {
        return position;
    }

    public double getWatt() {
        return watt;
    }

    public double getVoltage() {
        return voltage;
    }

    public double getCurrentInAmps() {
        // P = U * I  =>  I = P / U
        if(voltage > 0) {
            return watt / voltage;
        }
        return 0;
    }
}