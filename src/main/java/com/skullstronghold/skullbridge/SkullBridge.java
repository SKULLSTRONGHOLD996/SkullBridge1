package com.skullstronghold.skullbridge;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(SkullBridge.MOD_ID)
public class SkullBridge {
    public static final String MOD_ID = "skullbridge";

    public SkullBridge() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("skullbridge")
            .requires(source -> source.hasPermission(2))
            .then(Commands.literal("status")
                .executes(ctx -> {
                    CommandSourceStack source = ctx.getSource();
                    MinecraftServer server = source.getServer();
                    int online = server.getPlayerList().getPlayerCount();
                    int max = server.getPlayerList().getMaxPlayers();
                    Runtime runtime = Runtime.getRuntime();
                    long usedMiB = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
                    long allocatedMiB = runtime.totalMemory() / (1024 * 1024);
                    long maxMiB = runtime.maxMemory() / (1024 * 1024);
                    double mspt = server.getAverageTickTime();
                    source.sendSuccess(() -> Component.literal(
                        "SkullBridge | Players: " + online + "/" + max +
                        " | MSPT: " + String.format(java.util.Locale.ROOT, "%.1f", mspt) +
                        " | RAM: " + usedMiB + "/" + allocatedMiB +
                        " MiB allocated (max " + maxMiB + " MiB)"
                    ), false);
                    return 1;
                })));
    }
}
