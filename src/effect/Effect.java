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
    protected Cooldown item1EffectCooldown;


    /**
     * dummy item 2 effectCooldown
     */
    protected Cooldown item2EffectCooldown;


    /**
     * Initialize effect cool time
     */
    public Effect() {

        item1EffectCooldown = Core.getCooldown(5000);
        item2EffectCooldown = Core.getCooldown(5000);
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
                item1EffectCooldown.reset();
                break ;

        }
    }

    public Cooldown getCooldown(SpriteType s) {
        switch (s) {
            case Buff_Item:
                return (item1EffectCooldown);
            default:
                return (null);
        }
    }
}
