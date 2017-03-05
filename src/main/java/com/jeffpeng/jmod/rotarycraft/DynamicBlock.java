package com.jeffpeng.jmod.rotarycraft;


import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import Reika.ReactorCraft.API.NeutronShield;
import Reika.RotaryCraft.API.Interfaces.SurrogateBedrock;

import com.jeffpeng.jmod.JMODRepresentation;
import com.jeffpeng.jmod.interfaces.ISettingsReceiver;


public class DynamicBlock extends com.jeffpeng.jmod.types.blocks.DynamicBlock implements SurrogateBedrock, NeutronShield{
	
	private static final double MAXIMUM_NEUTRON_ABSORPTION_CHANCE = 0.9;
	
	private boolean surrogateBedrock = false;
	private float bedrockDustYield = 100;
	private double neutronAbsorptionChance = 0;
	private boolean spawnsRadiation = true;

	public DynamicBlock(JMODRepresentation owner, Material mat) {
		super(owner, mat);
	}

	@Override
	public void processSettings(ISettingsReceiver settings) {
		super.processSettings(settings);
		if(settings.hasSetting("fakeBedrock"))		this.surrogateBedrock	= settings.getBoolean("fakeBedrock");
		if(settings.hasSetting("bedrockDustYield")) this.bedrockDustYield 	= settings.getFloat("bedrockDustYield");
		if(settings.hasSetting("neutronAbsorptionChance"))
			this.neutronAbsorptionChance 	= Math.min(settings.getFloat("neutronAbsorptionChance"),MAXIMUM_NEUTRON_ABSORPTION_CHANCE);
		if(settings.hasSetting("spawnsRadiation"))	this.spawnsRadiation	= settings.getBoolean("spawnsRadiation");
		
	}

	@Override
	public double getAbsorptionChance(String type) {
		return this.neutronAbsorptionChance;
	}

	@Override
	public double getRadiationSpawnMultiplier(World world, int x, int y, int z, String type) {
		if(this.spawnsRadiation) return 1; return 0;
	}

	@Override
	public boolean isBedrock(World world, int x, int y, int z) {
		return this.surrogateBedrock;
	}
	
	@Override
	public float getYield(World world, int x, int y, int z) {
 		return this.bedrockDustYield;
	}
	
	

}
