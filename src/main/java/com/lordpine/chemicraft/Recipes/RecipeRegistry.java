package com.lordpine.chemicraft.Recipes;

import Reika.ReactorCraft.ReactorCraft;
import com.lordpine.chemicraft.Fluids.ModFluids;
import net.minecraftforge.fluids.FluidStack;

public class RecipeRegistry {

    public static void registerRecipes() {
        RecipesChemicalReactor.instance.addRecipe(new ChemicalReactorRecipe(new FluidStack(ModFluids.sulfur_dioxide, 100), new FluidStack(ReactorCraft.O, 100), new FluidStack(ModFluids.sulfur_trioxide, 200), 20, null, 1024).setReleasedTemp(20));
    }

}
