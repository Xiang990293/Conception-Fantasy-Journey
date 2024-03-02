package net.xiang990293.cfj.screen;

import com.google.common.collect.Lists;
//import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.DrawContext;
import net.xiang990293.cfj.block.entity.ConceptSimulatorBlockEntity;

import java.util.List;
import java.util.Optional;

public class ConceptSimulatorScreen extends HandledScreen<ConceptSimulatorScreenHandler> {

    static final Identifier BUTTON_DISABLED_TEXTURE = new Identifier("container/beacon/button_disabled");
    static final Identifier BUTTON_SELECTED_TEXTURE = new Identifier("container/beacon/button_selected");
    static final Identifier BUTTON_HIGHLIGHTED_TEXTURE = new Identifier(ConceptFantasyJourney.MOD_ID, "gui/concept_simulator_button_highlighted.png");
    private static final Identifier BUTTON_TEXTURE = new Identifier(ConceptFantasyJourney.MOD_ID, "gui/concept_simulator_button.png");
    private static final Identifier START_SIMULATING_TEXTURE = new Identifier(ConceptFantasyJourney.MOD_ID, "gui/concept_simulator_start_simulating_icon.png");
    private static final Identifier STOP_SIMULATING_TEXTURE = new Identifier(ConceptFantasyJourney.MOD_ID, "gui/concept_simulator_stop_simulating_icon.png");
    private static final Identifier START_CALCULATING_TEXTURE = new Identifier(ConceptFantasyJourney.MOD_ID, "gui/concept_simulator_start_calculating_icon.png");
    private static final Identifier TEXTURE = new Identifier(ConceptFantasyJourney.MOD_ID, "textures/gui/concept_simulator.png");
    private final List<ConceptSimulatorScreen.ConceptSimulatorButtonWidget> buttons = Lists.newArrayList();

    public ConceptSimulatorScreen(ConceptSimulatorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    private <T extends ClickableWidget & ConceptSimulatorScreen.ConceptSimulatorButtonWidget> void addButton(T button) {
        this.addDrawableChild(button);
        this.buttons.add(button);
    }

    @Override
    protected void init() {
        super.init();
        this.buttons.clear();
        this.addButton(new ConceptSimulatorScreen.StartCalculatingButtonWidget(this.x + 116, this.y + 62));
        this.addButton(new ConceptSimulatorScreen.StartSimulatingButtonWidget(this.x + 190, this.y + 107));
        this.addButton(new ConceptSimulatorScreen.StopSimulatingButtonWidget(this.x + 210, this.y + 107));

//        //製作一般的按鈕，像是在設定頁面中看到的那種長條形按鈕。
//        button1 = ButtonWidget.builder(Text.literal("Button 1"), button -> {
//                    System.out.println("You clicked button1!");
//                })
//                .dimensions(width / 2 - 205, 20, 200, 20)
//                .tooltip(Tooltip.of(Text.literal("Tooltip of button1")))
//                .build();
//        button2 = ButtonWidget.builder(Text.literal("Button 2"), button -> {
//                    System.out.println("You clicked button2!");
//                })
//                .dimensions(width / 2 + 5, 20, 200, 20)
//                .tooltip(Tooltip.of(Text.literal("Tooltip of button2")))
//                .build();
//
//        addDrawableChild(button1);
//        addDrawableChild(button2);
    }

    public ButtonWidget button1;
    public ButtonWidget button2;

    @Environment(EnvType.CLIENT)
    interface ConceptSimulatorButtonWidget {
        void tick(int level);
    }

    void tickButtons() {
        int i = this.handler.getProperties();
        this.buttons.forEach((button) -> {
            button.tick(i);
        });
    }

//    public void tick(int level) {
//    }

    @Environment(EnvType.CLIENT)
    abstract static class BaseButtonWidget extends PressableWidget implements ConceptSimulatorScreen.ConceptSimulatorButtonWidget {
        private boolean disabled;

        private boolean isDisabled() {
            return this.disabled;
        }

        protected BaseButtonWidget(int x, int y) {
            super(x, y, 16, 16, ScreenTexts.EMPTY);
        }

        protected BaseButtonWidget(int x, int y, Text message) {
            super(x, y, 16, 16, message);
        }

        public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            Identifier identifier;
            if (!this.active) {
                identifier = ConceptSimulatorScreen.BUTTON_DISABLED_TEXTURE;
            } else if (this.disabled) {
                identifier = ConceptSimulatorScreen.BUTTON_SELECTED_TEXTURE;
            } else if (this.isSelected()) {
                identifier = ConceptSimulatorScreen.BUTTON_HIGHLIGHTED_TEXTURE;
            } else {
                identifier = ConceptSimulatorScreen.BUTTON_TEXTURE;
            }

//            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
//            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
//            RenderSystem.setShaderTexture(0, identifier);

            context.drawTexture(identifier, this.getX(), this.getY(),1 ,0 ,0, this.width, this.height, 256, 256);
            this.renderExtra(context);
        }

        protected abstract void renderExtra(DrawContext context);

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        public void appendClickableNarrations(NarrationMessageBuilder builder) {
            this.appendDefaultNarrations(builder);
        }
    }

    @Environment(EnvType.CLIENT)
    abstract static class IconButtonWidget extends ConceptSimulatorScreen.BaseButtonWidget {
        private final Identifier texture;

        protected IconButtonWidget(int x, int y, Identifier texture, Text message) {
            super(x, y, message);
            this.texture = texture;
        }

        protected void renderExtra(DrawContext context) {
//            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
//            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
//            RenderSystem.setShaderTexture(0, this.texture);
            context.drawTexture(this.texture, this.getX(), this.getY(), 0, 0, 16, 16);
        }
    }

    //介面內的按鈕，一個負責開始計算，一個負責開始模擬，一個負責暫停模擬
    @Environment(EnvType.CLIENT)
    class StartCalculatingButtonWidget extends ConceptSimulatorScreen.IconButtonWidget {
        public StartCalculatingButtonWidget(int x, int y) {
            super(x, y, ConceptSimulatorScreen.START_CALCULATING_TEXTURE, ScreenTexts.EMPTY);
        }

        public void onPress() {
            ConceptSimulatorBlockEntity.isCalculating = true;
        }

        public void tick(int level) {
        }
    }

    @Environment(EnvType.CLIENT)
    class StartSimulatingButtonWidget extends ConceptSimulatorScreen.IconButtonWidget {
        public StartSimulatingButtonWidget(int x, int y) {
            super(x, y, ConceptSimulatorScreen.START_SIMULATING_TEXTURE, ScreenTexts.EMPTY);
        }

        public void onPress() {
            ConceptSimulatorBlockEntity.isSimulating = true;
        }

        public void tick(int level) {
        }
    }

    @Environment(EnvType.CLIENT)
    class StopSimulatingButtonWidget extends ConceptSimulatorScreen.IconButtonWidget {
        public StopSimulatingButtonWidget(int x, int y) {
            super(x, y, ConceptSimulatorScreen.STOP_SIMULATING_TEXTURE, ScreenTexts.EMPTY);
        }

        public void onPress() {
            ConceptSimulatorBlockEntity.isSimulating = false;
        }

        public void tick(int level) {
        }
    }

    // 背景繪製，
    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
//        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
//        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
//        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        renderProgressBar(context, x, y);
    }

    private void renderProgressBar(DrawContext context, int x, int y) {
        context.drawTexture(TEXTURE, x + 98, y + 68, 0, 166, handler.getScaledProgress(), 4);
        if (handler.isCalculating()) {
            context.drawTexture(TEXTURE, x + 98, y + 68, 0, 166, handler.getScaledProgress(), 4);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}