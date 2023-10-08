package entity;

public class Skin {
    private String name;
    private int price;
    private Color skinColor;
    private Boolean skinState=false;

    public Skin(String name, int price, Color skinColor) {
        this.name = name;
        this.price = price;
        this.skinColor = skinColor;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Color getSkinColor() {
        return skinColor;
    }

    public void applySkin(Entity entity) {
        entity.setColor(this.skinColor);
    }
    public boolean isPurchased() {
        return skinState;
    }

    
}
