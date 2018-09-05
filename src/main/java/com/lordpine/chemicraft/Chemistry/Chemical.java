package com.lordpine.chemicraft.Chemistry;

public class Chemical {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    private String name;
    private float mass;

    public float getViscosity() {
        return viscosity;
    }

    public void setViscosity(float viscosity) {
        this.viscosity = viscosity;
    }

    private float viscosity;

    public Chemical(String name, float mass, float viscosity) {
        this.name = name;
        this.mass = mass;
        this.viscosity = viscosity;
    }

}
