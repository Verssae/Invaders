package effect;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager;
import engine.DrawManager.SpriteType;
import entity.Item;

public class Effect {

    /**
     * dummy item 1 effectCooldown
     */
    protected Cooldown tripleshotEffectCooldown;
    /**
     * dummy item 2 effectCooldown
     * 만드는 버프에 따라 이름을 달리 할 것.
     */
    protected Cooldown Buff2EffectCooldown;
    protected Cooldown DebuffEffectCooldown;
    /** 스턴 아이템 */
    protected Cooldown debuffSturnEffect;


    /**
     * Initialize effect cool time
     */
    public Effect() {
        tripleshotEffectCooldown = Core.getCooldown(5000);
        Buff2EffectCooldown = Core.getCooldown(5000);
        DebuffEffectCooldown = Core.getCooldown(5000);
        debuffSturnEffect = Core.getCooldown(2000);
    }
    /**
     * Initialize effect cool time according to item Sprite
     *
     * @param s
     *          When an item collision event occurs,
     *          a sprite type of the item is received.
     */
    public void CooldownReset(SpriteType s) {
        switch (s) {
            case Buff_Item:
                tripleshotEffectCooldown.reset();
                break;
            case Buff_Item2:
                Buff2EffectCooldown.reset();
                break;
            case Debuff_Item:
                debuffSturnEffect.reset();
                break;
            default:
                break;
        }
    }

    public Cooldown getCooldown(SpriteType s) {
        switch (s) {
            case Buff_Item:
                return (tripleshotEffectCooldown);
            case Buff_Item2:
                return (Buff2EffectCooldown);
            case Debuff_Item:
                return (DebuffEffectCooldown);
            case Debuff_Item2:
                return (debuffSturnEffect);
            default:
                return (null);
        }
    }
}
