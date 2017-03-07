package com.jeffpeng.jmod.enderutilities;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import com.jeffpeng.jmod.JMOD;
import com.jeffpeng.jmod.JMODPlugin;
import com.jeffpeng.jmod.JMODPluginContainer;
import com.jeffpeng.jmod.util.Reflector;

import fi.dy.masa.enderutilities.item.tool.ItemEnderSword;
import fi.dy.masa.enderutilities.item.tool.ItemEnderTool;
import fi.dy.masa.enderutilities.item.tool.ItemEnderTool.ToolType;

public class Plugin extends JMODPlugin {

	public Plugin(JMODPluginContainer container) {
		super(container);
	}
	
	@Override
	public Float getRepairAmount(Item item){
		
		if(item instanceof ItemEnderSword) return 1F/2F; else if(item instanceof ItemEnderTool){
			ItemEnderTool enderTool = ((ItemEnderTool)item);
			ToolType enderToolType = enderTool.getToolType(new ItemStack(item));
			if(enderToolType == ToolType.PICKAXE || enderToolType == ToolType.AXE) return 1F/3F; else
			if(enderToolType == ToolType.HOE) return 1F/2F; else
			if(enderToolType == ToolType.SHOVEL) return 1F;
		}
		return null;
	}
	
	@Override
	public boolean patchTool(Item item, String itemname){

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
				JMOD.LOG.info("[tool patcher (enderutilities)] " + itemname + " is a " + ((ItemTool) item).getToolMaterialName() + " tool (" + item.getClass().getName() + ")");
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean updateToolMaterial(Item item, ToolMaterial toolmat){
		if (item instanceof ItemTool && item.getClass().getCanonicalName().contains("fi.dy.masa.enderutilities")){
			Reflector endertoolreflector = new Reflector(item, ItemEnderTool.class);
			endertoolreflector.set("material", toolmat).set("field_77865_bY",toolmat.getDamageVsEntity()+2F).set("field_77864_a", toolmat.getEfficiencyOnProperMaterial());
			return true;
		}
		return false;
	}

}
