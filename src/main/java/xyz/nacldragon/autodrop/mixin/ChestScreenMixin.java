package xyz.nacldragon.autodrop.mixin;


import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

@Mixin(GenericContainerScreen.class)
public abstract class ChestScreenMixin
	extends HandledScreen<GenericContainerScreenHandler>
	implements ScreenHandlerProvider<GenericContainerScreenHandler>
{
	@Shadow
	@Final
	private int rows;
	
	private int mode;
	
	public ChestScreenMixin(
		GenericContainerScreenHandler container,
		PlayerInventory playerInventory, Text name)
	{
		super(container, playerInventory, name);
	}
	
	@Override
	protected void init()
	{
		super.init();

			addButton(new ButtonWidget(x + backgroundWidth - 171, y + 4, 25, 12,
				new LiteralText("All"), b -> dropAll()));
			
			addButton(new ButtonWidget(x + backgroundWidth - 141, y + 4, 25, 12,
				new LiteralText("Tot"), b -> dropTotem()));

            addButton(new ButtonWidget(x + backgroundWidth - 111, y + 4, 25, 12,
				new LiteralText("Sad"), b -> dropSaddle()));

            addButton(new ButtonWidget(x + backgroundWidth - 81, y + 4, 25, 12,
				new LiteralText("Oth"), b -> dropOther()));
		

	}
	
	private void dropOther() {
        runInThread(() -> choosedDrop(2));
    }

    private void dropSaddle() {
        runInThread(() -> choosedDrop(1));
    }

    private void dropTotem() {
        runInThread(() -> choosedDrop(0));
    }

    private void dropAll()
	{
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