package KhorneSyrup.PolyMath.Common.lib;

import net.minecraft.entity.Entity;
import java.io.File;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;


public final class PolyMathPlayer implements IExtendedEntityProperties {
	
	public final static String EXT_PROP_NAME = "ExtendedPlayer";
	
	
	private final EntityPlayer player;
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
	private int currentHealth, maxHealth;
	//Declare Mana pool
	private int currentMana, maxMana;
	//Declare Stamina pool
	private int currentStamina, maxStamina;
	//Declare attributepoints
	private int currentAttributePoints;
	//Declare skillpoints
	private int currentSkillPoints;
	//Declare player entity and set health/mana and attributes
	
	public PolyMathPlayer(EntityPlayer player)
	{
		this.player = player;
		//Set Health
		this.currentHealth = this.maxHealth = 100;
		//Set Mana
		this.currentMana = this.maxMana = 100;
		//Set Stamina
		this.currentStamina = this.maxStamina = 100;
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
	
	public int getMaxMana()
	{
		return this.maxMana;
	}
	public int getCurrentMana() 
	{
		return this.currentMana;
	}
	
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(PolyMathPlayer.EXT_PROP_NAME, new PolyMathPlayer(player));
		
	}
	
	public static final PolyMathPlayer get(EntityPlayer player)
	{
		return (PolyMathPlayer) player.getExtendedProperties(EXT_PROP_NAME);
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
	
	public boolean consumeMana(int amount)
	{
		//Make sure we have enough mana.
		boolean sufficient = amount <=this.currentMana;
		//Consume the manas!
		this.currentMana -=(amount < this.currentMana ? amount : this.currentMana);
		//If we had enough mana, tell us.
		return sufficient;
	}
	
	public void replenishMana(int amount)
	{
		this.currentMana = this.currentMana + amount;
		
	}

}
