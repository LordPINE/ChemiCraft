package com.lordpine.chemicraft.Recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ChemicalReactorRecipe {

    public FluidStack input1;
    public FluidStack input2;
    public FluidStack output;
    public ItemStack catalyst;
    public float catalystless_efficiency;
    public int temperature;

    public ChemicalReactorRecipe(FluidStack input1, FluidStack input2, FluidStack output, int temperature, ItemStack catalyst, float catalystless_efficiency) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.catalyst = catalyst;
        this.catalystless_efficiency = catalystless_efficiency;
        this.temperature = temperature;
    }

}
