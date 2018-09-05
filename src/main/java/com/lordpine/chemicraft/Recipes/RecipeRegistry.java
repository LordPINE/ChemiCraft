package com.lordpine.chemicraft.Recipes;

import Reika.ReactorCraft.ReactorCraft;
import com.lordpine.chemicraft.Fluids.ModFluids;
import net.minecraftforge.fluids.FluidStack;

public class RecipeRegistry {

    public static void registerRecipes() {
        RecipesChemicalReactor.instance.addRecipe(new ChemicalReactorRecipe(new FluidStack(ModFluids.sulfur_dioxide, 1), new FluidStack(ReactorCraft.O, 1), new FluidStack(ModFluids.sulfur_trioxide, 2), 20, null, 1));
    }

}
