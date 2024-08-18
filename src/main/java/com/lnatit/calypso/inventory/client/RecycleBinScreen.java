package com.lnatit.calypso.inventory.client;

import com.lnatit.calypso.inventory.RecycleBinMenu;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.LinkedList;
import java.util.List;

import static com.lnatit.calypso.Calypso.MODID;

public class RecycleBinScreen extends AbstractContainerScreen<RecycleBinMenu>
{
    public static final ResourceLocation FLAME_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID,
                                                                                              "container/recycle_bin/burning_flame"
    );
    public static final ResourceLocation SPARK_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID,
                                                                                              "container/recycle_bin/destruction_spark"
    );
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MODID,
                                                                                         "textures/gui/container/recycle_bin.png"
    );

    public RecycleBinScreen(RecycleBinMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        /*
          TODO 标题位置调整
          image 对应贴图， font 对应文字
         */
        this.titleLabelX = this.imageWidth - 8 - this.font.width(title);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int x = i + 159;
        int y = j + 57;
        guiGraphics.blitSprite(FLAME_SPRITE, x, y, 32, 32);
        guiGraphics.blit(TEXTURE, i, j, 0, 0, 180, this.imageHeight);

        this.menu.setDestoryCount(Sparkle.renderSparkles(this.menu.getDestroyCount(), guiGraphics, x, y));
    }

    /**
     * TODO 修改火花的视觉效果
     */
    private static class Sparkle
    {
        // TODO 火花从第五帧才开始出现，感觉有点迟滞，如果想调整火花出现的时机，请在 0~4 之间调整这个值
        static final int START_FRAME = 1;
        static final int MAX_FRAMES = 30 - START_FRAME;
        static final long MS_PER_FRAME = 50;
        static final List<Sparkle> SPARKLES = new LinkedList<>();

        static int renderSparkles(int spCount, GuiGraphics guiGraphics, int flameX, int flameY) {
            int diff = spCount - SPARKLES.size();
            for (int i = 0; i < diff; i++) {
                SPARKLES.add(new Sparkle());
            }
            // TODO 修改火花和火苗的相对位置，让两者视觉上对齐
            SPARKLES.removeIf(s -> s.render(guiGraphics, flameX + 9, flameY + -9));
            return SPARKLES.size();
        }

        final long startTime;

        private Sparkle() {
            startTime = Util.getMillis();
        }

        boolean render(GuiGraphics guiGraphics, int sparkleX, int sparkleY) {
            long currTime = Util.getMillis();
            int frame = (int) ((currTime - startTime) / MS_PER_FRAME);
            if (frame >= MAX_FRAMES) {
                return true;
            }
            guiGraphics.blitSprite(SPARK_SPRITE, 32, 960, 0, 32 * (START_FRAME + frame), sparkleX, sparkleY, 32, 32);
            return false;
        }
    }
}
