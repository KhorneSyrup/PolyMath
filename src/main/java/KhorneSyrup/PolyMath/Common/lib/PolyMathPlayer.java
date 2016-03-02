package KhorneSyrup.PolyMath.Common.lib;

import net.minecraft.entity.Entity;
import java.io.File;

import KhorneSyrup.PolyMath.Common.network.PacketDispatcher;
import KhorneSyrup.PolyMath.Common.network.client.SyncPlayerPropsMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;


public final class PolyMathPlayer implements IExtendedEntityProperties {

	public final static String EXT_PROP_NAME = "PolyMathPlayer";


	private final EntityPlayer player;

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
	private int currentHealth, maxHealth, healthRegenTimer;
	public static final int HEALTH_WATHCER = 20;
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
		this.currentHealth = this.maxHealth;
		this.healthRegenTimer = 0;
		this.player.getDataWatcher().addObject(HEALTH_WATHCER, this.maxHealth);
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

	public int getMaxHealth()
	{
		return this.maxHealth;
	}

	public int getCurrentHealth()
	{
		return this.currentHealth;
	}

	public int getMaxStamina()
	{
		return this.maxStamina;
	}

	public int getCurrentStamina()
	{
		return this.currentStamina;
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

		player.getDataWatcher().updateObject(HEALTH_WATHCER, props.getCurrentHealth());
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
		if (manaRegenTimer == 0) {
			manaRegenTimer = getCurrentMana() < getMaxMana() ? 100 : 0;
			return true;
		}

		return false;
	}
	/*========================================================================================*/
	/*========================================================================================*/
	/*========================================================================================*/
	/*Begin Mana related functions!!!!!!!!!!!!!!!!! */
	/*========================================================================================*/
	/*========================================================================================*/
	/*========================================================================================*/
	public int getCurrentMana()
	{
		return player.getDataWatcher().getWatchableObjectInt(MANA_WATCHER);
	}

	public final void setCurrentMana(int amount) {
		player.getDataWatcher().updateObject(MANA_WATCHER, amount > 0 ? (amount < maxMana ? amount : maxMana) : 0);
		player.getDataWatcher().updateObject(MANA_WATCHER, getCurrentMana() > maxMana ? maxMana : getCurrentMana());
		}

	public final int getMaxMana() {
		return maxMana;
	}
	public final void setMaxMana(int amount) {
		maxMana = (amount > 0 ? amount : 0);

		PacketDispatcher.sendTo(new SyncPlayerPropsMessage(player), (EntityPlayerMP) player);
	}

	public final boolean regenMana(int amount)
	{
		boolean full = (amount + getCurrentMana()) >= maxMana;
		setCurrentMana(getCurrentMana() + amount);
		return full;
	}

	public boolean consumeMana(int amount)
	{
		//Make sure we have enough mana.
		boolean sufficient = amount <= getCurrentMana();
		//Consume the manas!
		setCurrentMana(getCurrentMana() - amount);
		return sufficient;

	}
	// Completely restores Mana
	public final void replenishMana()
	{
		this.player.getDataWatcher().updateObject(MANA_WATCHER, this.maxMana);
	}

}
