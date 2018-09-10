package com.lordpine.chemicraft.TileEntities;

import Reika.DragonAPI.ASM.APIStripper;
import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Power.ShaftPowerReceiver;
import Reika.RotaryCraft.Auxiliary.Interfaces.FrictionHeatable;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityFurnaceHeater;
import com.lordpine.chemicraft.Blocks.ModBlocks;
import com.lordpine.chemicraft.Recipes.ChemicalReactorRecipe;
import com.lordpine.chemicraft.Recipes.RecipesChemicalReactor;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@APIStripper.Strippable(value = {"mcp.mobius.waila.api.IWailaDataProvider"})
public class TileEntityChemicalReactor extends TileEntityBase implements ShaftPowerReceiver, IFluidHandler, TemperatureTE, FrictionHeatable, IWailaDataProvider, IInventory {

    public int iotick = 512;

    public int min_torque = 1;
    public long MIN_POWER = 1024;
    public long MIN_SPEED = 1;
    private int torque;
    private int omega;
    private long power;
    private float temperature;
    private static Set<Item> catalysts = new HashSet<>();

    public int getProgressTime() {
        return progressTime;
    }

    private int progressTime;

    private static Set<Fluid> valid_fluids = new HashSet<>();

    private HybridTank[] input_tanks = new HybridTank[2];

    private HybridTank output_tank = new HybridTank("output", 32000);

    private final StepTimer tempTimer = new StepTimer(20);

    public TileEntityChemicalReactor() {
        super();
        input_tanks[0] = new HybridTank("input_1", 32000);
        input_tanks[1] = new HybridTank("input_2", 32000);
        temperature = 30;
    }

    public static void addValidFluid(Fluid fluid) {
        valid_fluids.add(fluid);
    }

    public static void addValidCatalyst(Item item) {
        catalysts.add(item);
    }

    public HybridTank[] getInput_tanks() {
        return input_tanks;
    }

    public HybridTank getOutput_tank() {
        return output_tank;
    }

    @Override
    public Block getTileEntityBlockID() {
        return ModBlocks.chemical_reactor;
    }

    @Override
    public void updateEntity(World world, int x, int y, int z, int meta) {
        //this.input_tanks[0].addLiquid(1, ModFluids.PVC);
        if (iotick > 0)
            iotick -= 8;
        tempTimer.update();
        if (tempTimer.checkCap()) {
            this.updateTemperature(world, x, y, z, meta);
        }
        if (power >= MIN_POWER && omega >= MIN_SPEED) {
           this.doOperation();
        }
        else {
            progressTime = 0;
            min_torque = 1;
        }

    }

    private void doOperation() {
        ChemicalReactorRecipe r = RecipesChemicalReactor.instance.getRecipe(this.getInput_tanks()[0].getFluid(), this.getInput_tanks()[1].getFluid(), (int)this.temperature, null);
        if (r != null) {
            min_torque = r.required_torque;
            if (torque >= min_torque) {
                progressTime++;
                if (progressTime >= this.getOperationTime()) {
                    if (output_tank.fill(r.output, false) == r.output.amount) {
                        output_tank.fill(r.output, true);
                        if (input_tanks[0].getActualFluid() == r.input1.getFluid()) {
                            input_tanks[0].drain(r.input1.amount, true);
                            input_tanks[1].drain(r.input2.amount, true);
                        } else {
                            input_tanks[1].drain(r.input1.amount, true);
                            input_tanks[0].drain(r.input2.amount, true);
                        }
                        progressTime = 0;
                    }else{
                        progressTime--;
                    }
                }
            }
        } else {
            progressTime = 0;
            min_torque = 1;
        }
    }

    private int getOperationTime() {
        int val = (int)(Math.exp(-temperature/500)*(700 - 38.5 * ReikaMathLibrary.logbase2(this.omega + 1)));
        return val > 0 ? val : 1;
    }

    @Override
    protected void animateWithTick(World world, int x, int y, int z) {

    }

    @Override
    public int getRedstoneOverride() {
        return 0;
    }

    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     *
     * @param p_94041_1_
     * @param p_94041_2_
     */
    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return 1;
    }

    /**
     * Returns the stack in slot i
     *
     * @param p_70301_1_
     */
    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        return null;
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     *
     * @param p_70298_1_
     * @param p_70298_2_
     */
    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     *
     * @param p_70304_1_
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param p_70299_1_
     * @param p_70299_2_
     */
    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {

    }

    public String getInventoryName()
    {
        return "machine.chemical_reactor";
    }

    /**
     * Returns if the inventory is named
     */
    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    @Override
    public int getInventoryStackLimit() {
        return 0;
    }


    /**
     * Fills fluid into internal tanks, distribution is left entirely to the IFluidHandler.
     *
     * @param from     Orientation the Fluid is pumped in from.
     * @param resource FluidStack representing the Fluid and maximum amount of fluid to be filled.
     * @param doFill   If false, fill will only be simulated.
     * @return Amount of resource that was (or would have been, if simulated) filled.
     */
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (!valid_fluids.contains(resource.getFluid())) {
            return 0;
        }
        if (from == getLeft(getFacing())) {
            return input_tanks[0].fill(resource, doFill);
        } else if (from == getRight(getFacing())) {
            return input_tanks[1].fill(resource, doFill);
        } else {
            return 0;
        }
    }

    /**
     * Drains fluid out of internal tanks, distribution is left entirely to the IFluidHandler.
     *
     * @param from     Orientation the Fluid is drained to.
     * @param resource FluidStack representing the Fluid and maximum amount of fluid to be drained.
     * @param doDrain  If false, drain will only be simulated.
     * @return FluidStack representing the Fluid and amount that was (or would have been, if
     * simulated) drained.
     */
    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (from == ForgeDirection.UP) {
            return output_tank.drain(resource.amount, doDrain);
        } else {
            return null;
        }
    }

    /**
     * Drains fluid out of internal tanks, distribution is left entirely to the IFluidHandler.
     * <p>
     * This method is not Fluid-sensitive.
     *
     * @param from     Orientation the fluid is drained to.
     * @param maxDrain Maximum amount of fluid to drain.
     * @param doDrain  If false, drain will only be simulated.
     * @return FluidStack representing the Fluid and amount that was (or would have been, if
     * simulated) drained.
     */
    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (from == ForgeDirection.UP) {
            return output_tank.drain(maxDrain, doDrain);
        } else {
            return null;
        }
    }

    /**
     * Returns true if the given fluid can be inserted into the given direction.
     * <p>
     * More formally, this should return true if fluid is able to enter from the given direction.
     *
     * @param from
     * @param fluid
     */
    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return (from == getLeft(getFacing())|| from == getRight(getFacing())) && valid_fluids.contains(fluid);
    }

    /**
     * Returns true if the given fluid can be extracted from the given direction.
     * <p>
     * More formally, this should return true if fluid is able to leave from the given direction.
     *
     * @param from
     * @param fluid
     */
    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return from == ForgeDirection.UP;
    }

    /**
     * Returns an array of objects which represent the internal tanks. These objects cannot be used
     * to manipulate the internal tanks. See {@link FluidTankInfo}.
     *
     * @param from Orientation determining which tanks should be queried.
     * @return Info for the relevant internal tanks.
     */
    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (from == ForgeDirection.UP) {
            return new FluidTankInfo[]{new FluidTankInfo(output_tank)};
        } else if (from == getLeft(getFacing())) {
            return new FluidTankInfo[]{new FluidTankInfo(input_tanks[0])};
        } else if (from == getRight(getFacing())) {
            return new FluidTankInfo[]{new FluidTankInfo(input_tanks[0])};
        } else {
            return new FluidTankInfo[0];
        }
    }


    @Override
    protected String getTEName() {
        return this.getInventoryName();
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    /**
     * RC machines set your machine's rotational omega with this.
     *
     * @param omega
     */
    @Override
    public void setOmega(int omega) {
        this.omega = omega;
    }

    /**
     * RC machines set your machine's torque with this.
     *
     * @param torque
     */
    @Override
    public void setTorque(int torque) {
        this.torque = torque;
    }

    /**
     * RC machines set your machine's power with this.
     * You do not need to calculate power=omega*torque;
     * RC code will do that for you.
     *
     * @param power
     */
    @Override
    public void setPower(long power) {
        this.power = power;
    }

    /**
     * When there is no input machine. Usually used to set power, omega, torque = 0
     */
    @Override
    public void noInputMachine() {
        this.torque = 0;
        this.omega = 0;
        this.power = 0;
    }

    /**
     * Can the machine receive power from this side. Usually either "dir == facing" for 1D machines and "true" for omnidirectional machines.
     *
     * @param dir
     */
    @Override
    public boolean canReadFrom(ForgeDirection dir) {
        return dir == ForgeDirection.DOWN;
    }

    /**
     * Whether your machine is able to receive power right now. If this returns false, no other power code is called.
     */
    @Override
    public boolean isReceiving() {
        return true;
    }

    /**
     * The minimum torque the machine requires to operate. Also controls flywheel deceleration; a value of zero means flywheels driving it
     * never decelerate, yielding infinite energy. To have no effective minimum, pick a value of one; a torque of zero yields zero power.
     * Pick something reasonable, preferably as realistic as possible.
     *
     * @param available
     */
    @Override
    public int getMinTorque(int available) {
        return min_torque;
    }

    /**
     * For fetching the current rotational omega. This can be called from
     * both client and server, so ensure that the TE is prepared for that.
     */
    @Override
    public int getOmega() {
        return this.omega;
    }

    /**
     * For fetching the current torque. This can be called from
     * both client and server, so ensure that the TE is prepared for that.
     */
    @Override
    public int getTorque() {
        return this.torque;
    }

    /**
     * For fetching the current power value. This can be called from
     * both client and server, so ensure that the TE is prepared for that.
     */
    @Override
    public long getPower() {
        return this.power;
    }

    /**
     * Analogous to TileEntityIOMachine's "iotick". Used to control I/O render opacity.
     */
    @Override
    public int getIORenderAlpha() {
        return iotick;
    }

    /**
     * Analogous to TileEntityIOMachine's "iotick". Used to control I/O render opacity.
     * This one is called by tools.
     *
     * @param io
     */
    @Override
    public void setIORenderAlpha(int io) {
        iotick = io;
    }

    @Override
    protected void writeSyncTag(NBTTagCompound NBT)
    {
        super.writeSyncTag(NBT);
        NBT.setInteger("torque", torque);
        NBT.setInteger("omega", omega);
        NBT.setLong("power", power);
        NBT.setInteger("io", iotick);
        NBT.setFloat("temperature", temperature);
        NBT.setInteger("processtime", progressTime);
        this.input_tanks[0].writeToNBT(NBT);
        this.input_tanks[1].writeToNBT(NBT);
        this.output_tank.writeToNBT(NBT);
    }

    @Override
    protected void readSyncTag(NBTTagCompound NBT)
    {
        super.readSyncTag(NBT);
        torque = NBT.getInteger("torque");
        omega = NBT.getInteger("omega");
        power = NBT.getLong("power");
        iotick = NBT.getInteger("io");
        temperature = NBT.getFloat("temperature");
        progressTime = NBT.getInteger("processtime");

        if (torque < 0 || torque == Double.POSITIVE_INFINITY || torque == Double.NaN)
            torque = 0;
        if (omega < 0 || omega == Double.POSITIVE_INFINITY || omega == Double.NaN)
            omega = 0;
        this.input_tanks[0] = (HybridTank)this.input_tanks[0].readFromNBT(NBT);
        this.input_tanks[1] = (HybridTank)this.input_tanks[1].readFromNBT(NBT);
        this.output_tank = (HybridTank)this.output_tank.readFromNBT(NBT);
    }


    /**
     * Actions to take on overheat
     *
     * @param world
     * @param x
     * @param y
     * @param z
     */
    @Override
    public void onOverheat(World world, int x, int y, int z) {
        overheat(world, x, y, z);
    }

    /**
     * Can the friction heater heat this machine
     */
    @Override
    public boolean canBeFrictionHeated() {
        return true;
    }

    /**
     * A simple multiplication factor to control the heat gain for a given power value; at the 1x default, 67MW will reach 2000C, the maximum
     * achievable by the Friction Heater. Note that heat from power is a logarithmic function, and that nlogx = log(x^n), and thus power
     * requirements are actually a power function (= ^1/f), so a multiplier of 0.5x will see the required power be squared, and a multiplier of
     * 0.25 will cause the requirement to be raised to the fourth power. Multipliers greater than one are also functional, though take care
     * not to pick a multiplier so large that the required power for a given temperature rounds to zero (temperatures are integers).
     */
    @Override
    public float getMultiplier() {
        return 0.25f;
    }

    /**
     * If you have "cool to ambient" behavior, and it has a timer, this resets it so as to pause such logic.
     */
    @Override
    public void resetAmbientTemperatureTimer() {
        //tempTimer.reset();
    }

    @Override
    public void updateTemperature(World world, int x, int y, int z, int meta) {
        ForgeDirection frictionheaterside = ReikaWorldHelper.checkForAdjTile(world, x, y, z, TileEntityFurnaceHeater.class, true);
        int Tamb = 0;
        if (frictionheaterside == null) {
            Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);

            if (RotaryAux.isNextToWater(world, x, y, z)) {
                Tamb /= 2;
            }
            ForgeDirection iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.ice);
            if (iceside == null)
                iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.packed_ice);
            if (iceside != null) {
                if (Tamb > 0)
                    Tamb /= 4;
                ReikaWorldHelper.changeAdjBlock(world, x, y, z, iceside, Blocks.flowing_water, 0);
            }
            int Tadd = 0;
            if (RotaryAux.isNextToFire(world, x, y, z)) {
                Tadd += Tamb >= 100 ? 100 : 200;
            }
            if (RotaryAux.isNextToLava(world, x, y, z)) {
                Tadd += Tamb >= 100 ? 400 : 600;
            }
            Tamb += Tadd;

            } else {
                int dx = x+frictionheaterside.offsetX;
                int dy = y+frictionheaterside.offsetY;
                int dz = z+frictionheaterside.offsetZ;
                TileEntityFurnaceHeater te = (TileEntityFurnaceHeater)world.getTileEntity(dx, dy, dz);
                Tamb = te.getTemperature();
        }

        temperature -= (temperature - Tamb) / 10;
        if (temperature > 100) {
            ForgeDirection side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.snow);
            if (side == null)
                side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.snow_layer);
            if (side != null)
                ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, Blocks.air, 0);
            side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.ice);
            if (side != null)
                ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, Blocks.flowing_water, 0);
        }
        ChemicalReactorRecipe r = RecipesChemicalReactor.instance.getRecipe(this.getInput_tanks()[0].getFluid(), this.getInput_tanks()[1].getFluid(), (int)this.temperature, null);
        if (r != null) {
            min_torque = r.required_torque;
            if (torque >= min_torque) {
                temperature += r.temperature_released;
            }
        }
        }

    @Override
    public void addTemperature(int temp) {
        //temperature += temp;
    }

    @Override
    public int getThermalDamage() {
        return 0;
    }

    @Override
    public void overheat(World world, int x, int y, int z) {
        List<EntityLivingBase> entitylist = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - 2,y - 2,z - 2, x + 2,  y + 2,  z + 2));
        for (EntityLivingBase entity : entitylist) {
            entity.setFire(10);
        }
        world.createExplosion(null,x + 0.5d, y + 0.5d, z + 0.5d, 2.0f, true);
    }

    @Override
    public boolean canBeCooledWithFins() {
        return true;
    }

    @Override
    public boolean allowExternalHeating() {
        return true;
    }

    @Override
    public int getTemperature() {
        return (int)temperature;
    }

    /**
     * For overheating tests. Not all tiles actually fail on overheat; some just clamp the temperature.
     */
    @Override
    public int getMaxTemperature() {
        return 1000;
    }

    @Override
    public void setTemperature(int temp) {
        temperature = temp;
    }

    private ForgeDirection getFacing() {
        return this.getBlockMetadata() == 1 ? ForgeDirection.EAST : this.getBlockMetadata() == 2 ? ForgeDirection.SOUTH : this.getBlockMetadata() == 3 ? ForgeDirection.WEST : ForgeDirection.NORTH;
    }

    private ForgeDirection getLeft(ForgeDirection dir) {
        return dir.getRotation(ForgeDirection.UP);
    }

    private ForgeDirection getRight(ForgeDirection dir) {
        return dir.getRotation(ForgeDirection.DOWN);
    }


    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }


    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }


    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        currenttip.add(String.format("Temperature: %dC", this.getTemperature()));
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        return tag;
    }
}
