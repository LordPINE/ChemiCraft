package com.lordpine.chemicraft.Recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class RecipesChemicalReactor {

    private final ArrayList<ChemicalReactorRecipe> recipeList = new ArrayList();
    public static RecipesChemicalReactor instance = new RecipesChemicalReactor();

    public void addRecipe(ChemicalReactorRecipe r){
        recipeList.add(r);
    }

    public ChemicalReactorRecipe getRecipe(FluidStack in1, FluidStack in2, int temp, ItemStack catalyst) {
        for (int i = 0; i < recipeList.size(); i++) {
            ChemicalReactorRecipe r = recipeList.get(i);
            if (this.matchRecipe(r, in1, in2, catalyst, temp))
                return r;
        }
        return null;
    }

    private boolean matchRecipe(ChemicalReactorRecipe r, FluidStack in1, FluidStack in2, ItemStack catalyst, int temp) {
        if (in1 == null || in2 == null) {
            return false;
        }
        if (catalyst != null) {
            if (r.catalystless_efficiency == 0 && r.catalyst != catalyst.getItem()) {
                return false;
            }
            if (r.catalyst == catalyst.getItem()) {
                temp = (int) (temp * r.catalyst_temp_factor);
            }
        } else if (r.catalystless_efficiency == 0) {
            return false;
        }
        if (!(temp >= r.temperature))
            return false;
        return (r.input1.getFluid() == in1.getFluid() && r.input2.getFluid() == in2.getFluid() && r.input1.amount <= in1.amount && r.input2.amount <= in2.amount)
                || (r.input1.getFluid() == in2.getFluid() && r.input2.getFluid() == in1.getFluid() && r.input2.amount <= in1.amount && r.input1.amount <= in2.amount);
    }
}
