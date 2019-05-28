package GameState;

import TileMap.Background;
import java.awt.*;
import java.awt.event.KeyEvent;

public class WinState extends GameState{


    private Background bg;

    private int currentChoice = 0;
    private String[] options = {
            "Start",
            "Quit"
    };

    private Color titleColor;
    private Font titleFont;

    private Font font;

    public WinState(GameStateManager gsm){
        this.gsm = gsm;

        try{
            bg = new Background("/Backgrounds/menubg.gif", 1);
            bg.setVector(-0.1, 0);

            titleColor = new Color(128, 128, 0);
            titleFont = new Font("Arial", Font.PLAIN, 28);

            font = new Font("Arial", Font.PLAIN, 12);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void init(){}
    public void update(){
        bg.update();
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("NYERTÉL!", 80, 70);

        //menu
        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if(i != currentChoice){
                g.setColor(Color.BLACK);
            }else{
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], 145, 140 + i * 15);
        }
    }

    private void select(){
        switch (currentChoice){
            case 0:
                gsm.setState(GameStateManager.Level1State);
                break;
            case 1:
                System.exit(0);
                break;
        }
    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER){
            select();
        }
        if (k == KeyEvent.VK_UP){
            currentChoice--;
            if(currentChoice == -1){
                currentChoice = options.length-1;
            }
        }

        if (k == KeyEvent.VK_DOWN){
            currentChoice++;
            if(currentChoice == options.length){
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {

    }
}
