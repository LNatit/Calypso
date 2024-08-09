package com.lnatit.calypso.client.block;

import com.lnatit.calypso.block.entity.CutoutPhotoStandBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.MatrixUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;

import java.util.List;

import static com.lnatit.calypso.Calypso.MODID;

public class CutoutPhotoStandBlockEntityRenderer implements BlockEntityRenderer<CutoutPhotoStandBlockEntity>
{
    ItemStack stack = new ItemStack(Items.RAIL);

    public CutoutPhotoStandBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CutoutPhotoStandBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0, 0, 0);

        BakedModel p_model = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(
                ResourceLocation.fromNamespaceAndPath(MODID, "photo_stand/default")));

        if (!p_model.isCustomRenderer()) {
            boolean flag1;
            flag1 = true;

            for (var model : p_model.getRenderPasses(null, flag1)) {
                RenderType renderType = RenderType.CUTOUT;
                VertexConsumer vertexconsumer;

                vertexconsumer = bufferSource.getBuffer(renderType);

                RandomSource randomsource = RandomSource.create();
                long i = 42L;

                for (Direction direction : Direction.values()) {
                    randomsource.setSeed(i);
                    this.renderQuadList(poseStack, vertexconsumer, model.getQuads(null, direction, randomsource),
                                        packedLight, packedOverlay
                    );
                }

                randomsource.setSeed(i);
                this.renderQuadList(poseStack, vertexconsumer, model.getQuads(null, null, randomsource), packedLight,
                                    packedOverlay
                );

            }
        }
        poseStack.popPose();
    }

    public void renderQuadList(PoseStack poseStack, VertexConsumer buffer, List<BakedQuad> quads, int combinedLight, int combinedOverlay) {
        PoseStack.Pose posestack$pose = poseStack.last();

        for (BakedQuad bakedquad : quads) {
            int i = -1;

            float f = (float) FastColor.ARGB32.alpha(i) / 255.0F;
            float f1 = (float) FastColor.ARGB32.red(i) / 255.0F;
            float f2 = (float) FastColor.ARGB32.green(i) / 255.0F;
            float f3 = (float) FastColor.ARGB32.blue(i) / 255.0F;
            buffer.putBulkData(posestack$pose, bakedquad, f1, f2, f3, f, combinedLight, combinedOverlay,
                               true
            ); // Neo: pass readExistingColor=true
        }
    }
}
