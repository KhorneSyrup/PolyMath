package KhorneSyrup.PolyMath.Common.lib;

import java.util.UUID;

import KhorneSyrup.PolyMath.PolyMath;
import KhorneSyrup.PolyMath.Common.network.PacketDispatcher;
import KhorneSyrup.PolyMath.Common.network.client.SyncPlayerPropsMessage;
import akka.actor.Props;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;


public final class PolyMathPlayer implements IExtendedEntityProperties  {

	public final static String EXT_PROP_NAME = "PolyMathPlayer";


	public final EntityPlayer player;

	//public final InventoryCustomPlayer inventory = new InventoryCustomPLayer();

	private enum playerClass {
		Paladin,
		Berzerker,
		Archer,
		Shadow,
		Wizard,
		Sorceror,
		Necromancer,
		Cleric
	}
	//Declare Player Attributes
	private int strength;
	private int vitality;
	private int agility;
	private int dexterity;
	private int intelligence;
	private int willpower;
	private int perception;
	private int luck;
	//Declare Health pool
	public static final UUID HealthID= UUID.fromString("e3723b50-7cc6-11e3-baa7-0800200c9a66");
	private int currentHealth = 100;// (int) this.player.getHealth();
	//IAttributeInstance PMHealth = new AttributeModifier(this.player.getPersistentID())

	private static int maxHealth;


	private int healthRegenTimer;
	public static final int HEALTH_WATCHER = 20;
	//Declare Mana pool
	private int currentMana, maxMana, manaRegenTimer;
	public static final int MANA_WATCHER = 21;
	//Declare Stamina pool
	private int currentStamina, maxStamina, staminaRegenTimer;
	public static final int STAMINA_WATCHER = 22;
	//Declare attributepoints
	private int currentAttributePoints;
	//Declare skillpoints
	private int currentSkillPoints;
	//Declare player entity and set health/mana and attributes

	public PolyMathPlayer(EntityPlayer player)
	{
		this.player = player;
		//Set Health
		this.currentHealth = 100;
		this.maxHealth = 100;
		this.healthRegenTimer = 0;
		this.player.getDataWatcher().addObject(HEALTH_WATCHER, this.maxHealth);
		//Set Mana
		this.currentMana = 0;
		this.maxMana = 100;
		this.manaRegenTimer = 0;
		this.player.getDataWatcher().addObject(MANA_WATCHER, this.maxMana);
		//Set Stamina
		this.currentStamina = this.maxStamina = 100;
		this.staminaRegenTimer = 0;
		this.player.getDataWatcher().addObject(STAMINA_WATCHER, this.maxStamina);
		//Set starting attributes
		this.strength = 0;
		this.vitality = 0;
		this.agility = 0;
		this.dexterity = 0;
		this.intelligence = 0;
		this.willpower = 0;
		this.perception = 0;
		this.luck = 0;
		//Set starting skill points
		this.currentSkillPoints = 0;
		//Set starting attributePoints.
		this.currentAttributePoints = 0;
	}

	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(PolyMathPlayer.EXT_PROP_NAME, new PolyMathPlayer(player));

	}

	public static final PolyMathPlayer get(EntityPlayer player)
	{
		return (PolyMathPlayer) player.getExtendedProperties(EXT_PROP_NAME);
	}

	public void copy(PolyMathPlayer props) {

		//inventory.copy(props.inventory);

		player.getDataWatcher().updateObject(HEALTH_WATCHER, props.getCurrentHealth());
		maxHealth = props.maxHealth;
		healthRegenTimer = props.healthRegenTimer;

		player.getDataWatcher().updateObject(MANA_WATCHER, props.getCurrentMana());
		maxMana = props.maxMana;
		manaRegenTimer = props.manaRegenTimer;

		player.getDataWatcher().updateObject(STAMINA_WATCHER, props.getCurrentStamina());
		maxStamina = props.maxStamina;
		staminaRegenTimer = props.staminaRegenTimer;
	}


	@Override
	public void saveNBTData(NBTTagCompound compound) {

		//Make a new tag compound that will save everything for our player.
		NBTTagCompound properties = new NBTTagCompound();


		//Save all the new properties
		//Save Mana
		properties.setInteger("CurrentMana", this.currentMana);
		properties.setInteger("MaxMana", this.maxMana);
		//Save Health
		properties.setInteger("CurrentHealth", this.currentHealth);
		properties.setInteger("MaxHealth", this.currentHealth);
		//Save Stamina
		properties.setInteger("CurrentStamina", this.currentStamina);
		properties.setInteger("MaxStamina", this.maxStamina);
		//Save Attributes
		properties.setInteger("Strength", this.strength);
		properties.setInteger("Vitality", this.vitality);
		properties.setInteger("agility", this.agility);
		properties.setInteger("Dexterity", this.dexterity);
		properties.setInteger("Intelligence", this.intelligence);
		properties.setInteger("Willpower", this.willpower);
		properties.setInteger("Perception", this.perception);
		properties.setInteger("Luck", this.luck);
		//Save skillpoints
		properties.setInteger("SkillPoints", this.currentSkillPoints);
		properties.setInteger("AttributePoints", this.currentAttributePoints);



		//Add custom tags to player
		compound.setTag(EXT_PROP_NAME, properties);
	}


	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		//Gather Data from custom tag compounds
		this.currentMana = properties.getInteger("CurrentMana");
		this.maxMana = properties.getInteger("MaxMana");
		//Debug TEST
		System.out.println("CURRENTMANA: " + this.currentMana + "/" + this.maxMana);

	}
	@Override
	public void init(Entity entity, World world) {
		// TODO Auto-generated method stub

	}

	public void onUpdate() {
		if (!player.worldObj.isRemote) {
			if (updateManaTimer()) {
				regenMana(1);
			}
		}
	}

	private boolean updateManaTimer() {
		if (manaRegenTimer > 0) {
			--manaRegenTimer;
		}
		if (manaRegenTimer == 0 && getCurrentMana() < maxMana) {
			manaRegenTimer = 100;
			return true;
		}
		return false;
	}
	/*========================================================================================*/
	/*========================================================================================*/
	/*========================================================================================*/
	/*Begin Health related functions!!!!!!!!!!!!!!!!! */
	/*========================================================================================*/
	/*========================================================================================*/
	/*========================================================================================*/
	public static void addHealthModifier(EntityPlayer player, double healthModifier) {
		AttributeModifier healthMod=new AttributeModifier(HealthID, "More Health Heart Modifier", healthModifier,0); //note modifier can be negative, as a +0=20 health (10 hearts) start. The player may choose to start at less hearts, so less than 20 health.
		IAttributeInstance attributeinstance = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
		attributeinstance.removeModifier(healthMod);
		attributeinstance.applyModifier(healthMod);
	}
	public int getCurrentHealth() {
		return player.getDataWatcher().getWatchableObjectInt(HEALTH_WATCHER);
	}

	public final int getMaxHealth() {
		return maxMana;
	}

	public final void setCurrentHealth(int amount) {
		player.getDataWatcher().updateObject(HEALTH_WATCHER, amount); // set mana
		//player.getDataWatcher().updateObject(MANA_WATCHER, amount > 0 ? (amount < maxMana ? amount : maxMana) : 0);
		if (getCurrentHealth() > maxHealth) {
			player.getDataWatcher().updateObject(HEALTH_WATCHER, maxHealth);
			player.addChatMessage(new ChatComponentText("Health Full."));
		}
		else if (getCurrentHealth() < 0) {
			player.getDataWatcher().updateObject(HEALTH_WATCHER, 0);
			player.addChatMessage(new ChatComponentText("Health Depleted. You're dead."));
		}
	}

	public final void setMaxHealth(int amount) {
		player.setHealth(maxHealth);
		player.getMaxHealth();
		maxHealth = (amount > 0 ? amount : 0); // kind of silly. Is this number over 0? Good, make it the number. Is it 0? make it 0.
		PacketDispatcher.sendTo(new SyncPlayerPropsMessage(player), (EntityPlayerMP) player);
	}

	public final boolean regenHealth(int amount) {
		//boolean full = (amount + getCurrentMana()) >= maxMana;
		setCurrentHealth(getCurrentHealth() + amount);
		return (getCurrentHealth() >= maxHealth);
	}

	public boolean consumeHealth(int amount) {
		//Make sure we have enough mana.
		//boolean sufficient = amount <= getCurrentMana();
		//Consume the manas!
		setCurrentHealth(getCurrentHealth() - amount);
		return (getCurrentHealth() > 0);

	}
	// Completely restores Mana
	public final void replenishHealth() {
		this.player.getDataWatcher().updateObject(HEALTH_WATCHER, this.maxHealth);
	}
	/*========================================================================================*/
	/*========================================================================================*/
	/*========================================================================================*/
	/*Begin Stamina related functions!!!!!!!!!!!!!!!!! */
	/*========================================================================================*/
	/*========================================================================================*/
	/*========================================================================================*/
	public int getCurrentStamina() {
		return player.getDataWatcher().getWatchableObjectInt(STAMINA_WATCHER);
	}

	public final int getMaxStamina() {
		return maxStamina;
	}

	public final void setCurrentStamina(int amount) {
		player.getDataWatcher().updateObject(STAMINA_WATCHER, amount); // set mana
		//player.getDataWatcher().updateObject(MANA_WATCHER, amount > 0 ? (amount < maxMana ? amount : maxMana) : 0);
		if (getCurrentMana() > maxStamina) {
			player.getDataWatcher().updateObject(STAMINA_WATCHER, maxStamina);
			player.addChatMessage(new ChatComponentText("Stamina Full."));
		}
		else if (getCurrentMana() < 0) {
			player.getDataWatcher().updateObject(STAMINA_WATCHER, 0);
			player.addChatMessage(new ChatComponentText("Stamina Depleted."));
		}
	}

	public final void setMaxStamina(int amount) {
		maxStamina = (amount > 0 ? amount : 0); // kind of silly. Is this number over 0? Good, make it the number. Is it 0? make it 0.
		PacketDispatcher.sendTo(new SyncPlayerPropsMessage(player), (EntityPlayerMP) player);
	}

	public final boolean regenStamina(int amount) {
		//boolean full = (amount + getCurrentMana()) >= maxMana;
		setCurrentStamina(getCurrentStamina() + amount);
		return (getCurrentStamina() >= maxStamina);
	}

	public boolean consumeStamina(int amount) {
		//Make sure we have enough mana.
		//boolean sufficient = amount <= getCurrentMana();
		//Consume the manas!
		setCurrentStamina(getCurrentStamina() - amount);
		return (getCurrentStamina() > 0);

	}
	// Completely restores Mana
	public final void replenishStamina() {
		this.player.getDataWatcher().updateObject(STAMINA_WATCHER, this.maxStamina);
	}
	/*========================================================================================*/
	/*========================================================================================*/
	/*========================================================================================*/
	/*Begin Mana related functions!!!!!!!!!!!!!!!!! */
	/*========================================================================================*/
	/*========================================================================================*/
	/*========================================================================================*/
	public int getCurrentMana() {
		return player.getDataWatcher().getWatchableObjectInt(MANA_WATCHER);
	}

	public final int getMaxMana() {
		return maxMana;
	}

	public final void setCurrentMana(int amount) {
		player.getDataWatcher().updateObject(MANA_WATCHER, amount); // set mana
		//player.getDataWatcher().updateObject(MANA_WATCHER, amount > 0 ? (amount < maxMana ? amount : maxMana) : 0);
		if (getCurrentMana() > maxMana) {
			player.getDataWatcher().updateObject(MANA_WATCHER, maxMana);
			player.addChatMessage(new ChatComponentText("Mana Full."));
		}
		else if (getCurrentMana() < 0) {
			player.getDataWatcher().updateObject(MANA_WATCHER, 0);
			player.addChatMessage(new ChatComponentText("Mana Depleted."));
		}
	}

	public final void setMaxMana(int amount) {
		maxMana = (amount > 0 ? amount : 0); // kind of silly. Is this number over 0? Good, make it the number. Is it 0? make it 0.
		PacketDispatcher.sendTo(new SyncPlayerPropsMessage(player), (EntityPlayerMP) player);
	}

	public final boolean regenMana(int amount) {
		//boolean full = (amount + getCurrentMana()) >= maxMana;
		setCurrentMana(getCurrentMana() + amount);
		return (getCurrentMana() >= maxMana);
	}

	public boolean consumeMana(int amount) {
		//Make sure we have enough mana.
		//boolean sufficient = amount <= getCurrentMana();
		//Consume the manas!
		setCurrentMana(getCurrentMana() - amount);
		return (getCurrentMana() > 0);

	}
	// Completely restores Mana
	public final void replenishMana() {
		this.player.getDataWatcher().updateObject(MANA_WATCHER, this.maxMana);
	}

}