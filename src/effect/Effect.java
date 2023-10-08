package effect;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager;
import engine.DrawManager.SpriteType;

public class Effect {

    protected Cooldown effect1Cooldown;

    protected Cooldown effect2Cooldown;

    public Effect() {

        effect1Cooldown = Core.getCooldown(5000);
        effect2Cooldown = Core.getCooldown(5000);
    }


    public void CooldownReset(SpriteType s) {
//        switch (s) {
//            case SpriteType.Bullet:
//                effect1Cooldown.reset();
//                break ;
//
//        }
    }
}
