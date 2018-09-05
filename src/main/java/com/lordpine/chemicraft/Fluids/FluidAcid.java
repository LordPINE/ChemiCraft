package com.lordpine.chemicraft.Fluids;

import com.lordpine.chemicraft.Chemistry.Acid;
import com.lordpine.chemicraft.Chemistry.Chemical;

public class FluidAcid extends FluidSolution{

    private float pH;

    public FluidAcid(String fluidname, Acid chemical, float concentration, Chemical solvent) {
        super(fluidname, chemical, concentration, solvent);
        this.pH = this.calcPH(chemical);
    }

    public float calcPH(Acid chemical) {
        float pKa = chemical.getpKa();
        float Ka = (float)Math.pow(10, -pKa);
        float pH = (float)-Math.log((-Ka + Math.sqrt(Ka*Ka - 4 * this.getConcentration() * Ka))/2);
        return pH;
    }

    public float getpH() {
        return pH;
    }

    public void setpH(float pH) {
        this.pH = pH;
    }

}
