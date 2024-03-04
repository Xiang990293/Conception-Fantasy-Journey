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

    private static final Identifier BUTTON_DISABLED_TEXTURE = new Identifier(ConceptFantasyJourney.MOD_ID, "gui/concept_simulator_button_disabled.png");
    private static final Identifier BUTTON_SELECTED_TEXTURE = new Identifier(ConceptFantasyJourney.MOD_ID, "gui/concept_simulator_button_disabled.png");
    private static final Identifier BUTTON_HIGHLIGHTED_TEXTURE = new Identifier(ConceptFantasyJourney.MOD_ID, "gui/concept_simulator_button_highlighted.png");
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



    public ButtonWidget button1;
    public ButtonWidget button2;
    public ConceptSimulatorScreen.StartCalculatingButtonWidget startCalculatingButton;
    public ConceptSimulatorScreen.SwitchSimulatingButtonWidget switchSimulatingButton;
    @Override
    protected void init() {
        super.init();
        this.buttons.clear();
        startCalculatingButton = new ConceptSimulatorScreen.StartCalculatingButtonWidget(this.x + 116, this.y + 62);
        switchSimulatingButton = new ConceptSimulatorScreen.SwitchSimulatingButtonWidget(this.x + 116, this.y + 30);
        this.addButton(startCalculatingButton);
        this.addButton(switchSimulatingButton);
//        this.addButton(new ConceptSimulatorScreen.StopSimulatingButtonWidget(this.x + 100, this.y + 30));

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
                identifier = ConceptSimulatorScreen.BUTTON_DISABLED_TEXTURE;
            } else if (this.isHovered()) {
                identifier = ConceptSimulatorScreen.BUTTON_HIGHLIGHTED_TEXTURE;
            } else {
                identifier = ConceptSimulatorScreen.BUTTON_TEXTURE;
            }

            context.drawTexture(identifier, this.getX(), this.getY(),1 ,0 ,0, 16, 16, this.width, this.height);
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
    abstract static class BaseSwitchWidget extends BaseButtonWidget {
        public static boolean isOn = false;
        protected BaseSwitchWidget(int x, int y) {
            super(x, y);
        }

        protected BaseSwitchWidget(int x, int y, Text message) {
            super(x, y, message);
        }

        public void switchState(){
            isOn = !isOn;
        }

        public boolean getState(){
            return isOn;
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
            context.drawTexture(this.texture, this.getX(), this.getY(),1 ,0 ,0, 16, 16, this.width, this.height);
        }
    }

    @Environment(EnvType.CLIENT)
    abstract static class IconSwitchWidget extends ConceptSimulatorScreen.BaseSwitchWidget {
        protected final Identifier textureOn;
        protected final Identifier textureOff;
        protected Identifier texture;

        protected IconSwitchWidget(int x, int y, Identifier textureOn, Identifier textureOff, Text message) {
            super(x, y, message);
            this.textureOn = textureOn;
            this.textureOff = textureOff;
            this.texture = textureOff;
        }

        protected IconSwitchWidget(int x, int y, Identifier texture, Text message) {
            super(x, y, message);
            this.textureOn = texture;
            this.textureOff = texture;
            this.texture = texture;
        }

        protected void renderExtra(DrawContext context) {
            context.drawTexture(this.texture, this.getX(), this.getY(),1 ,0 ,0, 16, 16, this.width, this.height);
        }
    }

    //介面內的按鈕，一個負責開始計算，一個負責開始/暫停模擬
    @Environment(EnvType.CLIENT)
    class StartCalculatingButtonWidget extends ConceptSimulatorScreen.IconButtonWidget {
        public StartCalculatingButtonWidget(int x, int y) {
            super(x, y, ConceptSimulatorScreen.START_CALCULATING_TEXTURE, ScreenTexts.EMPTY);
        }

        public void onPress() {
            ConceptSimulatorBlockEntity.isCalculating = true;
//            ConceptSimulatorBlockEntity.
        }

        public void tick(int level) {
        }
    }

    @Environment(EnvType.CLIENT)
    class SwitchSimulatingButtonWidget extends ConceptSimulatorScreen.IconSwitchWidget {
        public SwitchSimulatingButtonWidget(int x, int y) {
            super(x, y, ConceptSimulatorScreen.STOP_SIMULATING_TEXTURE, ConceptSimulatorScreen.START_SIMULATING_TEXTURE, ScreenTexts.EMPTY);
            texture = (ConceptSimulatorBlockEntity.isSimulating? textureOn : textureOff);
            this.active = ConceptSimulatorBlockEntity.isCalculated;
            this.setDisabled(ConceptSimulatorBlockEntity.isCalculated);
        }

        public void onPress() {
            switchState();
            ConceptSimulatorBlockEntity.isSimulating = getState();
            texture = ((texture == textureOn)? textureOff : textureOn);
        }

        public void tick(int level) {

        }
    }

//    @Environment(EnvType.CLIENT)
//    class StopSimulatingButtonWidget extends ConceptSimulatorScreen.IconButtonWidget {
//        public StopSimulatingButtonWidget(int x, int y) {
//            super(x, y, ConceptSimulatorScreen.STOP_SIMULATING_TEXTURE, ScreenTexts.EMPTY);
//            this.setDisabled(ConceptSimulatorBlockEntity.isCalculating);
//            this.active = false;
//        }
//
//        public void onPress() {
//            ConceptSimulatorBlockEntity.isSimulating = false;
//        }
//
//        public void tick(int level) {
//        }
//    }

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