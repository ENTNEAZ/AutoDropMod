package xyz.nacldragon.autodropmod.mixin;


import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import xyz.nacldragon.autodropmod.AutoDrop;

@Mixin(ShulkerBoxScreen.class)
public abstract class ShulkerBoxScreenMixin
        extends HandledScreen<ShulkerBoxScreenHandler>
        implements ScreenHandlerProvider<ShulkerBoxScreenHandler>
{
    private final int rows = 3;
    private int mode;
    public ShulkerBoxScreenMixin(
            ShulkerBoxScreenHandler container, PlayerInventory playerInventory,
            Text name)
    {
        super(container, playerInventory, name);
    }
    @Override
    protected void init()
    {
        super.init();


        addDrawableChild(new ButtonWidget(x + backgroundWidth - 171, y + 4, 25, 12,
                new LiteralText("All"), b -> dropAll()));

        addDrawableChild(new ButtonWidget(x + backgroundWidth - 141, y + 4, 25, 12,
                new LiteralText("Tot"), b -> dropTotem()));

        addDrawableChild(new ButtonWidget(x + backgroundWidth - 111, y + 4, 25, 12,
                new LiteralText("Sad"), b -> dropSaddle()));

        addDrawableChild(new ButtonWidget(x + backgroundWidth - 81, y + 4, 25, 12,
                new LiteralText("Oth"), b -> dropOther()));

    }


    private void dropOther() {
        AutoDrop.LOGGER.info("Shulker Drop Other!");
        runInThread(() -> choosedDrop(2));
    }

    private void dropSaddle() {
        AutoDrop.LOGGER.info("Shulker Drop Saddle!");
        runInThread(() -> choosedDrop(1));
    }

    private void dropTotem() {
        AutoDrop.LOGGER.info("Shulker Drop Totem!");
        runInThread(() -> choosedDrop(0));
    }

    private void dropAll()
    {
        AutoDrop.LOGGER.info("Shulker Drop All!");
        runInThread(() -> shiftClickSlots(0, rows * 9, 1));
    }

    private void runInThread(Runnable r)
    {
        new Thread(() -> {
            try
            {
                r.run();

            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }).start();
    }

    private void shiftClickSlots(int from, int to, int mode)

    {
        this.mode = mode;

        for(int i = from; i < to; i++)
        {
            Slot slot = handler.slots.get(i);
            if(slot.getStack().isEmpty())
                continue;

            try
            {
                Thread.sleep(5);

            }catch(InterruptedException e)
            {
                throw new RuntimeException(e);
            }
            if(this.mode != mode || client.currentScreen == null)//防止new或中断
                break;
            onMouseClick(slot, slot.id, 1, SlotActionType.THROW);
        }
    }

    private void choosedDrop(int j) {
        switch (j) {
            case 0:
                for(int i = 0; i < rows * 9; i++)
                {
                    Slot slot = handler.slots.get(i);
                    if(slot.getStack().isEmpty())
                        continue;

                    try
                    {
                        Thread.sleep(5);

                    }catch(InterruptedException e)
                    {
                        throw new RuntimeException(e);
                    }

                    if(this.mode != mode || client.currentScreen == null)//防止new或中断
                        break;
                    if(slot.getStack().getItem().toString().equals("totem_of_undying")){
                        onMouseClick(slot, slot.id, 1, SlotActionType.THROW);
                    }
                }
                break;
            case 1:
                for(int i = 0; i < rows * 9; i++)
                {
                    Slot slot = handler.slots.get(i);
                    if(slot.getStack().isEmpty())
                        continue;

                    try
                    {
                        Thread.sleep(5);

                    }catch(InterruptedException e)
                    {
                        throw new RuntimeException(e);
                    }

                    if(this.mode != mode || client.currentScreen == null)//防止new或中断
                        break;
                    if(slot.getStack().getItem().toString().equals("saddle")){
                        onMouseClick(slot, slot.id, 1, SlotActionType.THROW);
                    }
                }
                break;
            case 2:
                for(int i = 0; i < rows * 9; i++)
                {
                    Slot slot = handler.slots.get(i);
                    if(slot.getStack().isEmpty())
                        continue;

                    try
                    {
                        Thread.sleep(5);

                    }catch(InterruptedException e)
                    {
                        throw new RuntimeException(e);
                    }

                    if(this.mode != mode || client.currentScreen == null)//防止new或中断
                        break;
                    if(slot.getStack().getItem().toString().equals("totem_of_undying") || slot.getStack().getItem().toString().equals("saddle"))
                        continue;
                    onMouseClick(slot, slot.id, 1, SlotActionType.THROW);
                }
                break;
            default:
                break;
        }
    }
}