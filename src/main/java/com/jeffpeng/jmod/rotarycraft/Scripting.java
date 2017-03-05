package com.jeffpeng.jmod.rotarycraft;

import com.jeffpeng.jmod.JMODRepresentation;
import com.jeffpeng.jmod.rotarycraft.actions.AddBlastFurnaceAlloying;
import com.jeffpeng.jmod.rotarycraft.actions.AddBlastFurnaceRecipe;
import com.jeffpeng.jmod.rotarycraft.actions.AddCentrifugeRecipe;
import com.jeffpeng.jmod.rotarycraft.actions.AddCompactorRecipe;
import com.jeffpeng.jmod.rotarycraft.actions.AddDryingBedRecipe;
import com.jeffpeng.jmod.rotarycraft.actions.AddGrinderRecipe;
import com.jeffpeng.jmod.rotarycraft.actions.AddPulseJetRecipe;
import com.jeffpeng.jmod.rotarycraft.actions.AddRockMelterRecipe;
import com.jeffpeng.jmod.rotarycraft.actions.AddWetterRecipe;
import com.jeffpeng.jmod.rotarycraft.actions.PatchSteelTools;
import com.jeffpeng.jmod.primitives.ModScriptObject;

public class Scripting extends ModScriptObject {
	
	public Scripting(JMODRepresentation owner){
		super(owner);
	}
	
	public void addGrinderRecipe(String out, String in){

		new AddGrinderRecipe(owner,out,in);
	}
	
	public void addBlastFurnaceRecipe(String out, int temperature, int speed, float xp, Object recipe){
		new AddBlastFurnaceRecipe(owner,out,temperature,speed,xp,recipe);
	}
	
	public AddBlastFurnaceAlloying addBlastFurnaceAlloying(String out, String main, int temp){
		return new AddBlastFurnaceAlloying(owner,out,main,temp); 
		
	}
	
	public AddCentrifugeRecipe addCentrifugeRecipe(String inputitem){
		return new AddCentrifugeRecipe(owner, inputitem); 
		
	}
	
	public void addPulseJetRecipe(String out, String in){
		new AddPulseJetRecipe(owner,out,in);
	}
	
	public void addCompactorRecipe(String out, String in, int temperature, int pressure){
		new AddCompactorRecipe(owner,out,in,temperature, pressure);
	}
	
	public void addDryingBedRecipe(String out, String in){
		new AddDryingBedRecipe(owner,out,in);
	}
	
	public void addRockMelterRecipe(String out, String in, int temperature, long power){
		new AddRockMelterRecipe(owner,out,in,temperature,power);
	}
	
	public void addLiquefactionRecipe(String out, String in, String fluidin, int time){
		new AddWetterRecipe(owner, out, in, fluidin, time);
	}
	
	public void patchRotarycraftSteelTools(){
		new PatchSteelTools(owner);
	}
	
	
}
