package com.lordpine.chemicraft.Recipes;

import com.lordpine.chemicraft.TileEntities.TileEntityChemicalReactor;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidStack;

public class ChemicalReactorRecipe {

    public FluidStack input1;
    public FluidStack input2;
    public FluidStack output;
    public Item catalyst;

    public float catalystless_efficiency;
    public int temperature;
    public int required_torque;
    public float temperature_released;
    public float catalyst_temp_factor;



    public ChemicalReactorRecipe(FluidStack input1, FluidStack input2, FluidStack output, int temperature, Item catalyst, int required_torque) {
        this.input1 = input1;
        this.input2 = input2;
        TileEntityChemicalReactor.addValidFluid(input1.getFluid());
        TileEntityChemicalReactor.addValidFluid(input2.getFluid());
        this.output = output;
        this.catalyst = catalyst;
        if (catalyst != null) {

        }
        this.temperature = temperature;
        this.required_torque = required_torque;

        temperature_released = 0;
        catalyst_temp_factor = 1;
        catalystless_efficiency = 1;
    }

    /**Temperature released * 10 is the temperature above ambient that will be reached.**/
    public ChemicalReactorRecipe setReleasedTemp(float temp) {
        temperature_released = temp;
        return this;
    }

    public ChemicalReactorRecipe setCatalystless_efficiency(float catalystless_efficiency) {
        this.catalystless_efficiency = catalystless_efficiency;
        return this;
    }

    public ChemicalReactorRecipe setCatalyst_temp_factor(float catalyst_temp_factor) {
        this.catalyst_temp_factor = catalyst_temp_factor;
        return this;
    }

}
