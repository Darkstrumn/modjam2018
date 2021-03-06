package com.rwtema.monkmod.advancements.criteria;

import com.rwtema.monkmod.data.MonkData;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayerMP;

public class MonkRequirementOcean extends MonkRequirementTick {
	private final static int STEP_PER_BLOCK = 64;
	int depth;

	public MonkRequirementOcean(int level, double depth) {
		super(level);
		this.depth = (int) (STEP_PER_BLOCK * depth);
	}

	@Override
	protected void doTick(EntityPlayerMP player, MonkData monkData) {
		if (player.isInsideOfMaterial(Material.WATER)) {
			int progress = monkData.getProgress();
			int air = player.getAir();
			if (air > 295) {
				monkData.setProgress(0);
			} else {
				monkData.increaseProgress(1);
				if (monkData.getProgress() > 280 && (player.world.rand.nextInt(50) == 0)) {
					grantLevel(player);
					player.setAir(300);

				}
			}

		} else {
			monkData.setProgress(0);
		}


	}
}
