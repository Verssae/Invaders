package engine;

import java.awt.event.KeyEvent;
import effect.Effect;
import engine.InputManager;
public class ItemManager {
        private int bombCount;
        private int shieldCount;
        private Effect effect;
        private InputManager inputManager;
    public ItemManager(final int shield, final int bomb) {
        this.bombCount = 0;
        this.shieldCount = 0;

    }



    public void useItem() {
        if (inputManager.isKeyDown(KeyEvent.VK_1)) {
            if (shieldCount > 0) {
                effect.setShieldState(true);
                shieldCount--;
            }
        } else if (inputManager.isKeyDown(KeyEvent.VK_1)) {
            if (bombCount > 0) {
                // 특정한 작업을 수행 (폭탄 터뜨리기 등)
                bombCount--;
            }
        }
    }
    public int getShieldCount()
    {
        return this.shieldCount;
    }
    public int getBombCount()
    {
        return this.bombCount;
    }
    public int PlusShieldCount(final int count)
    {
        this.shieldCount += count;
        return this.shieldCount;
    }
        public int PlusBombCount(final int count)
    {
        this.bombCount += count;
        return this.bombCount;
    }
}
