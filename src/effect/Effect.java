package effect;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager;
import engine.DrawManager.SpriteType;

public class Effect {

    /**
     * dummy item 1 effectCooldown
     */
    protected Cooldown effect1Cooldown;


    /**
     * dummy item 2 effectCooldown
     */
    protected Cooldown effect2Cooldown;


    /**
     * Initialize effect cool time
     */
    public Effect() {

        effect1Cooldown = Core.getCooldown(5000);
        effect2Cooldown = Core.getCooldown(5000);
    }



    /**
     * Initialize effect cool time according to item Sprite
     *
     * @param s
     *          When an item collision event occurs,
     *          a sprite type of the item is received.
     */
    public void CooldownReset(SpriteType s) {
//        switch (s) {
//            case SpriteType.Bullet:
//                effect1Cooldown.reset();
//                break ;
//
//        }
    }
}
