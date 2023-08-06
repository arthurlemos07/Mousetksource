package yalter.mousetweaks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;

import java.util.List;

public class DeobfuscationLayer {

	protected static Minecraft mc;

	protected static GuiScreen getCurrentScreen() {
		return mc.field_71462_r;
	}

	protected static boolean isGuiContainer(GuiScreen guiScreen) {
		return (guiScreen != null) && (guiScreen instanceof GuiContainer);
	}

	protected static boolean isGuiContainerCreative(GuiScreen guiScreen) {
		return (guiScreen instanceof GuiContainerCreative);
	}

	protected static boolean isVanillaCraftingOutputSlot(Container container, Slot slot) {
		return ((container instanceof ContainerWorkbench)   && (getSlotNumber(slot) == 0))
				|| ((container instanceof ContainerPlayer)  && (getSlotNumber(slot) == 0))
				|| ((container instanceof ContainerFurnace) && (getSlotNumber(slot) == 2))
				|| ((container instanceof ContainerRepair)  && (getSlotNumber(slot) == 2));
	}

	protected static GuiContainer asGuiContainer(GuiScreen guiScreen) {
		return (GuiContainer) guiScreen;
	}

	protected static Container asContainer(Object obj) {
		return (Container) obj;
	}

	protected static Slot asSlot(Object obj) {
		return (Slot) obj;
	}

	protected static Container getContainer(GuiContainer guiContainer) {
		return guiContainer.field_147002_h;
	}

	protected static List<?> getSlots(Container container) {
		return container.field_75151_b;
	}

	protected static Slot getSlot(Container container, int index) {
		return (Slot) (getSlots(container).get(index));
	}

	protected static ItemStack getSlotStack(Slot slot) {
		return (slot == null) ? null : slot.func_75211_c();
	}

	protected static int getWindowId(Container container) {
		return container.field_75152_c;
	}

	protected static void windowClick(int windowId, int slotNumber, int mouseButton, int shiftPressed) {
		// if (slotNumber != -1) {
		getPlayerController().func_78753_a(windowId, slotNumber, mouseButton, shiftPressed, getThePlayer());
		// }
	}

	protected static EntityPlayerSP getThePlayer() {
		return mc.field_71439_g;
	}

	protected static InventoryPlayer getInventoryPlayer() {
		return getThePlayer().field_71071_by;
	}

	protected static int getDisplayWidth() {
		return mc.field_71443_c;
	}

	protected static int getDisplayHeight() {
		return mc.field_71440_d;
	}

	protected static ItemStack getStackOnMouse() {
		return getInventoryPlayer().func_70445_o();
	}

	protected static PlayerControllerMP getPlayerController() {
		return mc.field_71442_b;
	}

	protected static int getSlotNumber(Slot slot) {
		return slot.field_75222_d;
	}

	protected static int getItemStackSize(ItemStack itemStack) {
		return itemStack.field_77994_a;
	}

	protected static int getMaxItemStackSize(ItemStack itemStack) {
		return itemStack.func_77976_d();
	}

	protected static ItemStack copyItemStack(ItemStack itemStack) {
		return (itemStack == null) ? null : itemStack.func_77946_l();
	}

	protected static boolean areStacksCompatible(ItemStack itemStack1, ItemStack itemStack2) {
		return (itemStack1 == null || itemStack2 == null) || itemStack1.func_77969_a(itemStack2);
	}

	protected static boolean isMouseOverSlot(GuiContainer guiContainer, Slot slot) {
		return (Boolean) Reflection.guiContainerClass.invokeMethod(
			guiContainer,
			Constants.ISMOUSEOVERSLOT_FORGE_NAME,
			slot,
			getRequiredMouseX(),
			getRequiredMouseY());
	}

	protected static Slot getSelectedSlot(GuiContainer guiContainer, Container container, int slotCount) {
		for (int i = 0; i < slotCount; i++) {
			Slot slot = getSlot(container, i);
			if (isMouseOverSlot(guiContainer, slot))
				return slot;
		}

		return null;
	}

	/**
	 * Disables the vanilla RMB drag mechanic in the given GuiContainer.
	 * If your guiContainer is based on the vanilla GuiContainer, you can use this method to disable the RMB drag.
	 *
	 * @param guiContainer The guiContainer to disable RMB drag in.
	 */
	public static void disableVanillaRMBDrag(GuiContainer guiContainer) {
		Reflection.guiContainerClass.setFieldValue(guiContainer, Constants.FIELDE_FORGE_NAME, true);
		Reflection.guiContainerClass.setFieldValue(guiContainer, Constants.FIELDq_FORGE_NAME, false);
	}

	protected static int getRequiredMouseX() {
		ScaledResolution var8 = new ScaledResolution(mc);
		int var9 = var8.func_78326_a();
		return (Mouse.getX() * var9) / getDisplayWidth();
	}

	protected static int getRequiredMouseY() {
		ScaledResolution var8 = new ScaledResolution(mc);
		int var10 = var8.func_78328_b();
		return var10 - ((Mouse.getY() * var10) / getDisplayHeight()) - 1;
	}
}
