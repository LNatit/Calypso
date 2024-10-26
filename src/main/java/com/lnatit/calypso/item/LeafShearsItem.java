package com.lnatit.calypso.item;
import com.google.common.collect.Sets;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.neoforged.neoforge.common.ItemAbilities.*;

public class LeafShearsItem extends Item {
    public static final Set<ItemAbility> ACTIONS = Stream.of(
//            SHEARS_DIG, // Loot
            SHEARS_HARVEST, // Beehive
//            SHEARS_REMOVE_ARMOR, // Wolf Armor
//            SHEARS_CARVE, // Pumpkin to Carved
            SHEARS_DISARM, // Break Tripwire without trigger it
            SHEARS_TRIM // Disable plants growth
    ).collect(Collectors.toCollection(Sets::newIdentityHashSet));

    public LeafShearsItem(Properties properties) {
		super(properties);
    }

    public static Tool createToolProperties() {
        // TODO modify break speed
        return new Tool(
                List.of(
                        Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 12.0F),
                        Tool.Rule.overrideSpeed(BlockTags.LEAVES, 12.0F),
//                        Tool.Rule.overrideSpeed(BlockTags.WOOL, 5.0F),
                        Tool.Rule.overrideSpeed(List.of(Blocks.VINE, Blocks.GLOW_LICHEN), 1.6F)
                ),
                1.0F,
                1
        );
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if (!level.isClientSide && !state.is(BlockTags.FIRE)) {
            stack.hurtAndBreak(1, miningEntity, EquipmentSlot.MAINHAND);
        }

        return state.is(BlockTags.LEAVES)
                || state.is(Blocks.COBWEB)
                || state.is(Blocks.SHORT_GRASS)
                || state.is(Blocks.FERN)
                || state.is(Blocks.DEAD_BUSH)
                || state.is(Blocks.HANGING_ROOTS)
                || state.is(Blocks.VINE)
                || state.is(Blocks.TRIPWIRE);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ACTIONS.contains(itemAbility);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState from = level.getBlockState(blockpos);
        BlockState to = from.getToolModifiedState(context, net.neoforged.neoforge.common.ItemAbilities.SHEARS_TRIM, false);
        if (to != null) {
            Player player = context.getPlayer();
            ItemStack itemstack = context.getItemInHand();
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
            }

            level.setBlockAndUpdate(blockpos, to);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(context.getPlayer(), to));
            if (player != null) {
                itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useOn(context);
    }
}
