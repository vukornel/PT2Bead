package Entity;

import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Letter extends MapObject {
    private int id;
    private boolean collected;
    private boolean remove;
    private boolean pickedUp;
    private BufferedImage[] sprites;

    public Letter(TileMap tm, int id){
        super(tm);
        this.id = id;
        width = 20;
        height = 20;
        cwidth = 20;
        cheight = 20;
        collected = false;

        try{
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Letter/letter.png"));

            sprites = new BufferedImage[2];

            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }

            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(-1);


        }catch (Exception e){e.printStackTrace();}
    }

    public void update(){
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        if(!collected) animation.update();
        else {
            animation.setFrame(1);
            animation.hasPlayedOnce();
        }
    }

    public void draw(Graphics2D g){
        setMapPosition();
        g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
    public boolean isCollected() {return collected;}
}
