package com.jeffpeng.jmod.enderutilities;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import com.jeffpeng.jmod.JMOD;
import com.jeffpeng.jmod.JMODPlugin;
import com.jeffpeng.jmod.JMODPluginContainer;
import com.jeffpeng.jmod.forgeevents.JMODGetRepairAmountEvent;
import com.jeffpeng.jmod.forgeevents.JMODPatchToolEvent;
import com.jeffpeng.jmod.forgeevents.JMODUpdateToolMaterialEvent;
import com.jeffpeng.jmod.util.Reflector;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fi.dy.masa.enderutilities.item.tool.ItemEnderSword;
import fi.dy.masa.enderutilities.item.tool.ItemEnderTool;
import fi.dy.masa.enderutilities.item.tool.ItemEnderTool.ToolType;

public class Plugin extends JMODPlugin {

	public Plugin(JMODPluginContainer container) {
		super(container);
	}
	
	@SubscribeEvent
	public void getRepairAmount(JMODGetRepairAmountEvent event){
		if(!Loader.isModLoaded("enderutilities")) return;
		Item item = event.getItem();
		if(item instanceof ItemEnderSword) event.setRepairAmount(1F/2F);
		else if(item instanceof ItemEnderTool){
			ItemEnderTool enderTool = ((ItemEnderTool)item);
			ToolType enderToolType = enderTool.getToolType(new ItemStack(item));
			if(enderToolType == ToolType.PICKAXE || enderToolType == ToolType.AXE) event.setRepairAmount(1F/3F); else
			if(enderToolType == ToolType.HOE) event.setRepairAmount(1F/2F); else
			if(enderToolType == ToolType.SHOVEL) event.setRepairAmount(1F);
		}
		return;
	}
	
	@SubscribeEvent
	public void patchTool(JMODPatchToolEvent event){
		if(!Loader.isModLoaded("enderutilities")) return;
		Item item = event.item;
		if (item instanceof ItemTool && item.getClass().getCanonicalName().contains("fi.dy.masa.enderutilities")){
			ToolMaterial toolmat = null;
			String toolmatname = ((ItemTool) item).getToolMaterialName();

			if (toolmatname == null) {
			} else {
				try {
					toolmat = ToolMaterial.valueOf(toolmatname);
				} catch (IllegalArgumentException e) {
					toolmat = null;
				}
			}
			
			if (toolmat == null) {
			} else {
				// Update the tool material
				Reflector endertoolreflector = new Reflector(item, ItemEnderTool.class);
				endertoolreflector.set("material", toolmat).set("field_77865_bY",toolmat.getDamageVsEntity()+2F).set("field_77864_a", toolmat.getEfficiencyOnProperMaterial());
				if (item.getMaxDamage() > 0) item.setMaxDamage(toolmat.getMaxUses());
				JMOD.LOG.info("[tool patcher (enderutilities)] " + event.itemname + " is a " + ((ItemTool) item).getToolMaterialName() + " tool (" + item.getClass().getName() + ")");
			}
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void updateToolMaterial(JMODUpdateToolMaterialEvent event){
		Item item = event.item;
		if(!Loader.isModLoaded("enderutilities")) return;
		if (item instanceof ItemTool && item.getClass().getCanonicalName().contains("fi.dy.masa.enderutilities")){
			Reflector endertoolreflector = new Reflector(item, ItemEnderTool.class);
			endertoolreflector.set("material", event.toolmat).set("field_77865_bY",event.toolmat.getDamageVsEntity()+2F).set("field_77864_a", event.toolmat.getEfficiencyOnProperMaterial());
			event.setCanceled(true);
		}
	}
}
