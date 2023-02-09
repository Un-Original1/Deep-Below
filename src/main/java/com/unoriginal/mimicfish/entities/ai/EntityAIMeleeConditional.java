package com.unoriginal.mimicfish.entities.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.EntitySelectors;

public class EntityAIMeleeConditional extends EntityAIAttackMelee {
    protected final double range;
    protected final Predicate<? super EntityLivingBase> conditional;

    public EntityAIMeleeConditional(EntityCreature creature, double speedIn, boolean useLongMemory, Predicate<? super EntityLivingBase > conditional, double range) {
        super(creature, speedIn, useLongMemory);
        //  this.conditional = conditional;
        this.range = range;
        this.conditional = (Predicate<EntityLivingBase>) p_apply_1_ -> {
            if (p_apply_1_ == null)
            {
                return false;
            }
            else if (conditional != null && !conditional.apply(p_apply_1_))
            {
                return false;
            }
            else
            {
                return EntitySelectors.NOT_SPECTATING.apply(p_apply_1_);
            }
        };

    }

    @Override
    public boolean shouldExecute() {
        return super.shouldExecute() && conditional.apply(this.attacker.getAttackTarget());
    }

    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting() && conditional.apply(this.attacker.getAttackTarget());
    }
    
    protected double getAttackReachSqr(EntityLivingBase attackTarget)
    {
        return (this.attacker.width * range * this.attacker.width * range + attackTarget.width);
    }
}
