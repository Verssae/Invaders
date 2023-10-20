package engine;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import entity.Coin;
import entity.Entity;


public class SkinBuyManager {

    private static SkinBuyManager instance;

    /* map to store skin ownership*/
    private Map<Color, Boolean> ownedSkins;
    /* map to store skin wearing status */
    private Map<Color, Boolean> equippedSkins;

    private int skinPrice = 100;

    private SkinBuyManager() {
        ownedSkins = new HashMap<>();
        equippedSkins = new HashMap<>();
    }
    
    /**
     * Returns the singleton instance of the SkinBuyManager class
     * access the unique instance of the SkinBuyManager class
     *
     * @return The singleton instance of the SkinBuyManager class
     */
    public static SkinBuyManager getInstance() {
        if (instance == null) {
            instance = new SkinBuyManager();
        }
        return instance;
    }
    /**
     * Returns the boolean of skin price payment
     *
     * @return the boolean of skin price payment
     */
    public boolean isPossible(int skinPrice, Coin coin) {
        int coinCurrent = coin.getCoin();
        return coinCurrent >= skinPrice;
    }

    /**
     * Purchase a skin if it is possible based on the provided skin price.
     *
     * @param skinName   The name of the skin to purchase.
     * @param skinPrice  The price of the skin.
     */
    public void purchaseSkin(Color skinColor, int skinPrice, Coin coin, Entity entity) {
        if (isPossible(skinPrice, coin)) {
            if (!(isSkinOwned(skinColor))){
                coin.minusCoin(skinPrice);
                entity.setColor(skinColor);
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
    public void equipSkin(Color skinColor, Entity entity) {
        if (isSkinOwned(skinColor)){
            if (isSkinEquipped(skinColor)) {
                equippedSkins.put(skinColor, true);
                entity.setColor(skinColor);
            }
        }
    }

    /**
     * Unequips the skin with the given name. This method marks the skin as unequipped.
     *
     * @param skinName The name of the skin to unequip.
     */
    public void unequipSkin(Color skinColor, Entity entity) {
        equippedSkins.put(skinColor, false);
        entity.setColor(Color.GREEN);
    }

}
