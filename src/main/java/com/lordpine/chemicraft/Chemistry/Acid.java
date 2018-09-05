package com.lordpine.chemicraft.Chemistry;

public class Acid extends Chemical {

    public float pKa;

    public Acid(String name, float mass, float viscosity, float pKa) {
        super(name, mass, viscosity);
        this.pKa = pKa;
    }

    public float getpKa() {
        return pKa;
    }

    public void setpKa(float pKa) {
        this.pKa = pKa;
    }



}
