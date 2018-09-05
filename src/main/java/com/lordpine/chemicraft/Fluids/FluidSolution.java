package com.lordpine.chemicraft.Fluids;

import com.lordpine.chemicraft.Chemistry.Chemical;

public class FluidSolution extends FluidChemical {

    private float concentration;
    private Chemical solvent;

    //Concentration in Molar
    public FluidSolution(String fluidname, Chemical chemical, float concentration, Chemical solvent) {
        super(fluidname, chemical);
        this.concentration = concentration;
        this.solvent = solvent;
        this.setViscosity((int)(solvent.getViscosity()*1000));
    }

    public float getConcentration() {
        return concentration;
    }

    public void setConcentration(float concentration) {
        this.concentration = concentration;
    }
}
