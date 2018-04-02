package com.rwtema.monkmod.advancements.criteria;

import com.rwtema.monkmod.MonkManager;
import com.rwtema.monkmod.advancements.MonkRequirement;
import com.rwtema.monkmod.data.MonkData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MonkRequirementBreakBlockBareHanded extends MonkRequirement {
	final StatePredicate states;
	final int numRequired;

	public MonkRequirementBreakBlockBareHanded(int level, StatePredicate states, int numRequired) {
		super(level);
		this.states = states;
		this.numRequired = numRequired;
	}

	@SubscribeEvent
	public void onBreak(BlockEvent.BreakEvent event) {
		if (!event.getWorld().isRemote && states.test(event.getWorld(), event.getPos(), event.getState())) {
			EntityPlayer player = event.getPlayer();
			if (player instanceof EntityPlayerMP && player.getHeldItemMainhand().isEmpty()) {
				MonkData monkData = MonkManager.get(player);
				if (monkData.getLevel() == (this.levelToGrant - 1)) {
					int progress = monkData.getProgress() + 1;
					monkData.setProgress(progress);
					if (progress >= numRequired) {
						grantLevel((EntityPlayerMP) event.getPlayer());
					}
				}
			}
		}
	}
}
