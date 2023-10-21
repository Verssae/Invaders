package engine;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import entity.Coin;
import entity.Ship;

public class SkinBuyManager {


    /* map to store skin ownership*/
    private Map<Color, Boolean> ownedSkins;
    /* map to store skin wearing status */
    private Map<Color, Boolean> equippedSkins;
    private GameState gameState;
    private Coin coin;
    private Ship ship;


    public SkinBuyManager(GameState gameState) {
        ownedSkins = new HashMap<>();
        equippedSkins = new HashMap<>();
        this.gameState = gameState;
        this.coin = gameState.getCoin();
    }
    
    /**
     * Returns the boolean of skin price payment
     *
     * @return the boolean of skin price payment
     */
    /* public boolean isPossible(int skinPrice) {
    *    int coinCurrent = coin.getCoin();
    *    return coinCurrent >= skinPrice;
    *}
    */

    /**
     * Purchase a skin if it is possible based on the provided skin price.
     *
     * @param skinName   The name of the skin to purchase.
     * @param skinPrice  The price of the skin.
     */
    public void purchaseSkin(Color skinColor, int skinPrice) {
        if (coin.getCoin()>= skinPrice) {
            if (!(isSkinOwned(skinColor))){
                this.coin.minusCoin(skinPrice);
                gameState.setShipColor(skinColor);
            }
        }
        ownedSkins.put(skinColor, true);  
    }

    /**
     * check for the skin ownership
     * 
     * @return True if the player owns the skin, or false if the skin is not owned
     */
    public boolean isSkinOwned(Color skinColor) {
        return ownedSkins.getOrDefault(skinColor, false);
    }

    /**
     * check for the skin wearing
     * 
     * @return True if the player wear the skin, or false if the skin isn't worn
     */
    public boolean isSkinEquipped(Color skinColor) {
        return equippedSkins.getOrDefault(skinColor, false);
    }

    /**
     * Equips the skin with the given name. This method marks the skin as equipped.
     *
     * @param skinName The name of the skin to equip.
     */
    public void equipSkin(Color skinColor) {
        if (isSkinOwned(skinColor)){
            if (isSkinEquipped(skinColor)) {
                equippedSkins.put(skinColor, true);
                ship.setColor(skinColor);
            }
        }
    }

    /**
     * Unequips the skin with the given name. This method marks the skin as unequipped.
     *
     * @param skinName The name of the skin to unequip.
     */
    public void unequipSkin(Color skinColor, Ship ship) {
        equippedSkins.put(skinColor, false);
        ship.setColor(Color.WHITE);
    }

}
