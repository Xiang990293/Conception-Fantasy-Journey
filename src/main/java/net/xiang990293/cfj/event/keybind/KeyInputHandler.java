package net.xiang990293.cfj.event.keybind;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.xiang990293.cfj.event.WingItemGeneralEvents;
import net.xiang990293.cfj.item.wing.WingItem;
import net.xiang990293.cfj.network.CfjNetworkingContants;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_WING= "key.category.concept_fantasy_journey.wing";
    public static final String KEY_FLY_HIGH = "key.concept_fantasy_journey.wing.fly.high";

    public static KeyBinding wingFlyKeyBinding;

    public static void register(){
        // no usage now, but tent to became wing mode toggle key or open/close wing toggle key.
//        wingFlyKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
//                KEY_FLY_HIGH, // The translation key of the keybinding's name
//                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
//                GLFW.GLFW_KEY_SPACE, // The keycode of the key
//                KEY_CATEGORY_WING // The translation key of the keybinding's category.
//        ));

        KeyInputHandler.registryKeyInput();
    }

    public static void registryKeyInput() {
        /*
         * 作用：
         * 1. 檢測玩家是否按下空白鍵
         * 2. 在鞘翅飛行狀態下，使玩家急速飛升
         * 3. 限制空白鍵的行為
         * */
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while(client.options.jumpKey.wasPressed()){
                PlayerEntity player = client.player;
                NbtCompound nbt = new NbtCompound();
                player.writeNbt(nbt);
//                NbtCompound abilities = nbt.getCompound("abilities");
                NbtCompound flyingTags = nbt.getCompound("flyingTags");
                boolean HasWingOn = player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof WingItem;
//                boolean IsCreativeFlying = abilities.getBoolean("flying");
                boolean IsElytraFlying = flyingTags.getBoolean("elytraFly");
                boolean CreativeFlyed = flyingTags.getBoolean("creativeFlyed");
                if (HasWingOn && IsElytraFlying && (player.getPitch()<=80.0f || player.getPitch()>=-80.0f)) {
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeFloat(0.05f);
                    ClientPlayNetworking.send(CfjNetworkingContants.Wing_Fly_High_ID, buf);
                }

                if(HasWingOn && IsElytraFlying && (player.getPitch()>80.0f || player.getPitch()<-80.0f)){
//                    WingItemGeneralEvents.playerFlyStateChange(player, nbt, true, true, false, false);
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeFloat(1.0f);
                    ClientPlayNetworking.send(CfjNetworkingContants.Wing_Fly_High_ID, buf);

                }

            }
//            if (wingFlyKeyBinding.isPressed()) {
//            }
        });
    }
}
