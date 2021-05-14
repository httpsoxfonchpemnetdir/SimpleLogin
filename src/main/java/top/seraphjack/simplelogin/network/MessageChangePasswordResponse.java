package top.seraphjack.simplelogin.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import top.seraphjack.simplelogin.client.PasswordHolder;

import java.util.function.Supplier;

public class MessageChangePasswordResponse {
    private final boolean success;

    public boolean success() {
        return success;
    }

    public MessageChangePasswordResponse(boolean success) {
        this.success = success;
    }

    public static void encode(MessageChangePasswordResponse msg, PacketBuffer buf) {
        buf.writeBoolean(msg.success);
    }

    public static MessageChangePasswordResponse decode(PacketBuffer buf) {
        return new MessageChangePasswordResponse(buf.readBoolean());
    }

    public static void handle(MessageChangePasswordResponse message, Supplier<NetworkEvent.Context> ctx) {
        if (message.success()) {
            PasswordHolder.instance().applyPending();
        } else {
            PasswordHolder.instance().dropPending();
        }
        ctx.get().setPacketHandled(true);
    }
}
