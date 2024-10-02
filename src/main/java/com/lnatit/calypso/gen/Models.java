package com.lnatit.calypso.gen;

import com.lnatit.calypso.block.BlockRegistry;
import com.lnatit.calypso.block.CapacityFurnaceBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.lnatit.calypso.Calypso.MODID;

public class Models
{
    public static class ModelProvider extends ItemModelProvider
    {
        public ModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, MODID, existingFileHelper);
        }

        // Register Item Models
        @Override
        protected void registerModels() {

        }
    }

    public static class StateProvider extends BlockStateProvider
    {
        public StateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
            super(output, MODID, exFileHelper);
        }

        // Register Block Models
        @Override
        protected void registerStatesAndModels() {
            this.simpleBlock(BlockRegistry.PHOTO_STAND_PART.get(), models().getBuilder("photo_stand_part").renderType("cutout").ao(false));
            this.simpleBlockWithItem(BlockRegistry.PHOTO_STAND_CORE.get(), cubeAll(BlockRegistry.PHOTO_STAND_CORE.get()));

            // 最后在这里调用方法生成对应模型
            this.registerCapacityFurnace();
            this.registerRecycleBin();
        }

        private void registerCapacityFurnace()
        {
            // 获得方块注册项和对应方块
            var holder = BlockRegistry.CAPACITY_FURNACE;
            Block block = holder.get();

            // 定义纹理文件路径
            ResourceLocation front = new ResourceLocation(MODID, "block/capacity_furnace_front");
            ResourceLocation side = new ResourceLocation(MODID, "block/capacity_furnace_side");
            ResourceLocation top = new ResourceLocation(MODID, "block/capacity_furnace_top");
            ResourceLocation front_on = new ResourceLocation(MODID, "block/capacity_furnace_front_on");

            // 生成对应模型
            // 模型名称
            String name = holder.getId().getPath();
            // 默认模型（未点燃）
            ModelFile defaultModel = models().orientable(name , side, front, top);
            // 点燃的模型
            ModelFile onModel = models().orientable(name + "_on", side, front_on, top);

            // 生成方块模型定义文件
            // 熔炉为水平可旋转方块，因此调用 horizontalBlock
            // 第二个参数为方块状态到方块模型的映射函数，即方块状态为点燃时（ LIT 为 true ）使用点燃的模型，否则使用默认模型
            this.horizontalBlock(block, state -> state.getValue(CapacityFurnaceBlock.LIT) ? onModel : defaultModel);

            // 最后注册方块对应的物品模型，拿在手中是默认的未点燃模型
            this.simpleBlockItem(block, defaultModel);
        }

        private void registerRecycleBin()
        {
            // 获得方块注册项和对应方块
            var holder = BlockRegistry.RECYCLE_BIN;
            Block block = holder.get();

            // 好像有个叫uncheckedmodel还是啥，可以智能补全一下试试看
            ModelFile model = new ModelFile.UncheckedModelFile("calypso:block/recycle_bin");

            this.horizontalBlock(block, model);

            // 最后注册方块和物品模型
            this.simpleBlockItem(block, model);
        }
    }
}
