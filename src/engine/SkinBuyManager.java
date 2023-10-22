package engine;

import java.awt.Color;

import entity.Coin;

public class SkinBuyManager {


    
    //private Map<Color, Boolean> ownedSkins;
    //private Map<Color, Boolean> equippedSkins;
    private GameState gameState;
    private Coin coin;

    public SkinBuyManager(GameState gameState) {
        this.gameState = gameState;
        this.coin = gameState.getCoin();
        //this.ownedSkins = gameState.getOwnedSkins();
        //this.equippedSkins = gameState.getEquippedSkins();
    }

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
            }
        }
        gameState.setOwnedSkins(skinColor, true) ; 
    }

    /**
     * check for the skin ownership
     * 
     * @return True if the player owns the skin, or false if the skin is not owned
     */
    public boolean isSkinOwned(Color skinColor) {
        return gameState.getOwnedSkins().getOrDefault(skinColor, false);
    }

    /**
     * check for the skin wearing
     * 
     * @return True if the player wear the skin, or false if the skin isn't worn
     */
    public boolean isSkinEquipped(Color skinColor) {
        return gameState.getEquippedSkins().getOrDefault(skinColor, false);
    }

    /**
     * Equips the skin with the given name. This method marks the skin as equipped.
     *
     * @param Color of the skin to equip.
     */
    public void equipSkin(Color skinColor) {
        if (isSkinOwned(skinColor)){
            if (!(isSkinEquipped(skinColor)) && !(gameState.getEquippedSkins().containsValue(true))) {
                gameState.setEquipped(skinColor, true);
                gameState.setShipColor(skinColor);
            }
        }
    }

    /**
     * Unequips the skin with the given name. This method marks the skin as unequipped.
     *
     * @param skinName The name of the skin to unequip.
     */
    public void unequipSkin(Color skinColor) {
        gameState.setEquipped(skinColor, false);
        gameState.setShipColor(Color.WHITE);
    }

}
