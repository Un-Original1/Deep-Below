package com.unoriginal.mimicfish.entities.entity;

import com.unoriginal.mimicfish.Mimicfish;
import com.unoriginal.mimicfish.entities.ai.EntityAIMeleeConditional;
import com.unoriginal.mimicfish.init.ModItems;
import com.unoriginal.mimicfish.init.ModSounds;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EntityLunkertooth extends EntityMob implements IEntityMultiPart {
    public List<ItemStack> chestItems = new ArrayList<>();

    public MultiPartEntityPart[] partArray;
    public MultiPartEntityPart esca = new MultiPartEntityPart(this, "esca", 0.8F, 0.8F);
    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(EntityLunkertooth.class, DataSerializers.BOOLEAN);
    public static final ResourceLocation LOOT = new ResourceLocation(Mimicfish.MODID, "entities/Lunkertooth");
    protected EntityAIWander wander;
    private int attackTick;
    private int avoidTicks;
    private int chestOpenTicks;
    private boolean touchedGround;
    public boolean weak;

    public EntityLunkertooth(World worldIn) {
        super(worldIn);
        this.setSize(2.5F, 2F);
        this.partArray = new MultiPartEntityPart[] {this.esca};
        this.moveHelper = new LunkerMoveHelper(this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
    }

    protected void initEntityAI() {
        EntityAIMoveTowardsRestriction entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction(this, 1.0D);
        this.tasks.addTask(2, new EntityAIMeleeConditional(this, 1.0D, false, Predicate -> this.avoidTicks <= 0, 1.45D));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityLivingBase.class, Predicate -> this.avoidTicks > 0 ,9F, 1.0D, 1.2D));
        this.tasks.addTask(3, new EntityAILunkertoothLeap(this, 0.2F));
        this.wander = new EntityAIWander(this, 1.0D, 80);
        this.tasks.addTask(5, entityaimovetowardsrestriction);
        this.tasks.addTask(7, this.wander);
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityLunkertooth.class, 12.0F, 0.01F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.wander.setMutexBits(3);
        entityaimovetowardsrestriction.setMutexBits(3);
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, Boolean.FALSE);

    }

    @SideOnly(Side.CLIENT)
    public boolean isAttackingClient() {
        return this.dataManager.get(ATTACKING);
    }

    private void setAttacking(boolean moving) {
        this.dataManager.set(ATTACKING, moving);
    }


    protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateSwimmer(this, worldIn);
    }

    public float getBlockPathWeight(BlockPos pos) {
        return this.world.getBlockState(pos).getMaterial() == Material.WATER ? 10.0F + this.world.getLightBrightness(pos) - 0.5F : super.getBlockPathWeight(pos);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public boolean getCanSpawnHere() {
        return true;
    }

    public boolean isNotColliding() {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.esca.onUpdate();
        float f17 = this.rotationYaw * 0.017453292F;
        float f3 = MathHelper.sin(f17 + (float)Math.PI);
        float f18 = MathHelper.cos(f17);
        this.esca.setLocationAndAngles(this.posX + (f3 * 1.5F), this.posY + this.height / 3F * 2F, this.posZ + (f18 * 1.5F), 0.0F, 0.0F);

        if (!this.isInWater()) {
            if (this.motionY > 0.0D && this.touchedGround && !this.isSilent()) {
                this.world.playSound(this.posX, this.posY, this.posZ, ModSounds.LUNKERTOOTH_FLOP, this.getSoundCategory(), 1.0F, 1.0F, false);
            }
            this.touchedGround= this.motionY < 0.0D && this.world.isBlockNormalCube((new BlockPos(this)).down(), false);
        }
        if (this.onGround)
        {
            this.motionY += 0.5D;
            this.motionX += ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
            this.motionZ += ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
            this.rotationYaw = this.rand.nextFloat() * 360.0F;
            this.onGround = false;
            this.isAirBorne = true;
        }
        if(this.chestOpenTicks == 100 && !this.world.isRemote){
            this.playSound(SoundEvents.BLOCK_CHEST_OPEN, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        }

        if(this.chestOpenTicks == 5 && !this.world.isRemote){
            this.playSound(SoundEvents.BLOCK_CHEST_CLOSE, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        }

        if(this.chestOpenTicks > 0){
            this.weak = true;
            --this.chestOpenTicks;
        } else {
            this.weak = false;
        }

        if(this.avoidTicks > 0){
            --this.avoidTicks;
        }

        if(this.attackTick > 0){
            --this.attackTick;
        } else {
            this.world.setEntityState(this, (byte) 8);
            this.setAttacking(false);
        }
    }

    public void onEntityUpdate()
    {
        int i = this.getAir();
        super.onEntityUpdate();



        if (this.isEntityAlive() && !this.isInWater())
        {
            --i;
            this.setAir(i);

            if (this.getAir() == -20)
            {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
        }
        else
        {
            this.setAir(800);
        }
    }

    public boolean isPushedByWater()
    {
        return false;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {

        this.attackTick = 20;
        this.setAttacking(true);
        this.playSound(ModSounds.LUNKERTOOTH_BITE, this.getSoundVolume(), this.world.rand.nextFloat() * 0.1F + 0.9F);
        this.world.setEntityState(this, (byte) 7);
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if((rand.nextInt(2)==0 || this.weak) && this.avoidTicks <= 0 ){
            this.avoidTicks = 20 + rand.nextInt(100);
        }
        if(!this.weak && this.chestOpenTicks <= 0 && this.attackTick > 0 && this.avoidTicks <= 0){
            this.chestOpenTicks = 100;
            this.avoidTicks = 20 + rand.nextInt(100);
            this.weak = true;
            this.world.setEntityState(this, (byte)9);
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    public Entity[] getParts()
    {
        return this.partArray;
    }

    @Override
    public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage) {
        if (!part.isEntityInvulnerable(source) || this.weak) {
            this.attackEntityFrom(source, damage * 2F);
        }
        return false;
    }

    static class EntityAILunkertoothLeap extends EntityAILeapAtTarget{
        EntityLunkertooth leaper;
        EntityLivingBase leapTarget;

        public EntityAILunkertoothLeap(EntityLiving leapingEntity, float leapMotionYIn) {
            super(leapingEntity, leapMotionYIn);
            this.leaper = (EntityLunkertooth) leapingEntity;
        }


        public boolean shouldExecute()
        {
            this.leapTarget = this.leaper.getAttackTarget();

            if (this.leapTarget == null)
            {
                return false;
            }
            else
            {
                double d0 = this.leaper.getDistanceSq(this.leapTarget);

                if (d0 >= 4.0D && d0 <= 16.0D)
                {
                    if (!this.leaper.onGround || !this.leaper.isInWater())
                    {
                        return false;
                    }
                    else
                    {
                        return this.leaper.getRNG().nextInt(5) == 0;
                    }
                }
                else
                {
                    return false;
                }
            }
        }

        public boolean shouldContinueExecuting()
        {
            return !this.leaper.onGround || !this.leaper.isInWater();
        }

        public void startExecuting()
        {

            super.startExecuting();
        }
    }

    public void travel(float strafe, float vertical, float forward)
    {
        if (this.isServerWorld() && this.isInWater())
        {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.8999999761581421D;
            this.motionY *= 0.8999999761581421D;
            this.motionZ *= 0.8999999761581421D;

            if (this.getAttackTarget() == null)
            {
                this.motionY -= 0.005D;
            }
        }
        else
        {
            super.travel(strafe, vertical, forward);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("attackTick", this.attackTick);
        compound.setInteger("avoidTick", this.avoidTicks);
        compound.setInteger("chestTick", this.chestOpenTicks);
        compound.setBoolean("Weak", this.weak);
        compound.setBoolean("touchedGround", this.touchedGround);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.attackTick = compound.getInteger("attackTick");
        this.avoidTicks = compound.getInteger("avoidTick");
        this.chestOpenTicks = compound.getInteger("chestTick");
        this.weak = compound.getBoolean("Weak");
        this.touchedGround = compound.getBoolean("touchedGround");
    }


    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 7) {
            this.attackTick = 20;
            this.setAttacking(true);
        }
        if (id == 8) {
            this.attackTick = 0;
            this.setAttacking(false);
        }
        if(id==9){
            this.weak = true;
            this.chestOpenTicks = 100;
        }
        else {
            super.handleStatusUpdate(id);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTick(){
        return this.attackTick;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return LOOT; }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)
    {
        if(!chestItems.isEmpty()){
            for(ItemStack stack : chestItems ){
                this.entityDropItem(stack, 0.0F);
            }
        }
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
    }

    public void addItemsToTable(NonNullList<ItemStack> chestContents){
        chestItems.addAll(chestContents);
        this.removeEggs(chestItems);
    }
    public void removeEggs(List<ItemStack> chestItems){
        if(!chestItems.isEmpty()){
            chestItems.removeIf(stack -> stack.getItem() == ModItems.ANGLERFISH_EGG);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getChestOpenTicks(){
        return this.chestOpenTicks;
    }

    public void spawnExplosionCloud(){
        EntityAreaEffectCloud areaeffectcloudentity = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
        areaeffectcloudentity.setParticle(EnumParticleTypes.EXPLOSION_HUGE);
        areaeffectcloudentity.setRadius(1.25F);
        areaeffectcloudentity.setDuration(1);
        areaeffectcloudentity.setWaitTime(0);

        this.playSound(ModSounds.LUNKERTOOTH_SCREAM, 1.0F, rand.nextFloat() * 0.2F + 0.9F);

        this.world.spawnEntity(areaeffectcloudentity);
    }

    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.LUNKERTOOTH_IDLE; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.LUNKERTOOTH_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.LUNKERTOOTH_DEATH; }

    static class LunkerMoveHelper extends EntityMoveHelper{
        private final EntityLunkertooth lunkertooth;

        public LunkerMoveHelper(EntityLunkertooth p_i45831_1_) {
            super(p_i45831_1_);
            this.lunkertooth = p_i45831_1_;
        }

        public void onUpdateMoveHelper() {

            if (action == Action.MOVE_TO && !lunkertooth.getNavigator().noPath()) {
                double d0 = this.posX - this.lunkertooth.posX;
                double d1 = this.posY - this.lunkertooth.posY;
                double d2 = this.posZ - this.lunkertooth.posZ;
                double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 /= d3;
                float f = (float) (MathHelper.atan2(d2, d0) * (180F / Math.PI)) - 90.0F;
                this.lunkertooth.rotationYaw = limitAngle(lunkertooth.rotationYaw, f, 90.0F);
                this.lunkertooth.renderYawOffset = lunkertooth.rotationYaw;
                float f1 = (float) (speed * lunkertooth.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
                this.lunkertooth.setAIMoveSpeed(lunkertooth.getAIMoveSpeed() + 0.125F * (f1 - lunkertooth.getAIMoveSpeed()));
                this.lunkertooth.motionY += lunkertooth.getAIMoveSpeed() * d1 * 0.1D;
            } else {
                this.lunkertooth.setAIMoveSpeed(0.0F);
            }
        }
    }

}
