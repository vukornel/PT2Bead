package GameState;

import Entity.*;
import Entity.Enemies.*;
import Main.GamePanel;
import TileMap.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level1State extends GameState {

    private TileMap tileMap;
    private Background bg;
    private Player player;
    private Letter letter;
    private Letter letter2;
    private Letter letter3;
    private Letter letter4;
    private int lettersNum;
    private HUD hud;

    private ArrayList<Enemy> enemies;

    public Level1State(GameStateManager gsm){
        this.gsm = gsm;
        init();
        lettersNum = 0;
    }

    @Override
    public void init() {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0,0);
        tileMap.setTween(1);

        bg = new Background("/Backgrounds/grassbg1.gif", 0.1);

        player = new Player(tileMap);
        player.setPosition(100,100);

        letter = new Letter(tileMap, 0);
        letter.setPosition(50, 200);
        letter2 = new Letter(tileMap, 1);
        letter2.setPosition(150, 200);
        letter3 = new Letter(tileMap, 2);
        letter3.setPosition(75, 200);
        letter4 = new Letter(tileMap, 3);
        letter4.setPosition(100, 200);

        hud = new HUD(player);

        enemies = new ArrayList<Enemy>();
        Slugger s;
        s = new Slugger(tileMap);
        s.setPosition(100,100);
        enemies.add(s);
    }

    @Override
    public void update() {
        player.update();
        if(letter.intersects(player)){
            if(!letter.isCollected()) lettersNum++;
            letter.setCollected(true);
        }
        if(letter2.intersects(player)){
            if(!letter2.isCollected()) lettersNum++;
            letter2.setCollected(true);
        }
        if(letter3.intersects(player)){
            if(!letter3.isCollected()) lettersNum++;
            letter3.setCollected(true);
        }
        if(letter4.intersects(player)){
            if(!letter4.isCollected()) lettersNum++;
            letter4.setCollected(true);
        }
        letter.update();
        letter2.update();
        letter3.update();
        letter4.update();
        tileMap.setPosition(GamePanel.Width / 2 - player.getX(),GamePanel.Height / 2 - player.getY());

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }

        if(lettersNum == 4) gsm.setState(GameStateManager.WinState);
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);
        tileMap.draw(g);
        player.draw(g);
        letter.draw(g);
        letter2.draw(g);
        letter3.draw(g);
        letter4.draw(g);

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        hud.draw(g);
    }

    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_SPACE) player.setJumping(true);
        if(k == KeyEvent.VK_SHIFT) player.setGliding(true);
        //if(k == KeyEvent.VK_CONTROL) player.setScratching();
        if(k == KeyEvent.VK_Q) player.setFiring();
    }

    @Override
    public void keyReleased(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_SPACE) player.setJumping(false);
        if(k == KeyEvent.VK_SHIFT) player.setGliding(false);
    }
}
