package Main;

import GameState.GameStateManager;

import java.awt.*;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable, KeyListener{
    public static final int Width = 320;
    public static final int Height = 240;
    public static final int Scale = 2;
    private Thread thread;
    private boolean running;
    private int Fps = 60;
    private long targetTime = 1000 / Fps;

    private BufferedImage image;
    private Graphics2D g;

    //gst
    private GameStateManager gsm;

    public GamePanel(){
        super();
        setPreferredSize(new Dimension(Width * Scale, Height * Scale));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify(){
        super.addNotify();
        if (thread == null){
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    public void init(){
        image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        running = true;

        gsm = new GameStateManager();
    }

    public void run(){
        init();

        long start;
        long elapsed;
        long wait;

        while(running){
            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed / 1000000;
            if (wait < 0) wait = 1;

            try{
                Thread.sleep(wait);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private void update() {
        gsm.update();
    }
    private void draw() {
        gsm.draw(g);
    }
    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, Width * Scale, Height * Scale, null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) {}
    public void keyPressed(KeyEvent key) {
        gsm.keyPressed(key.getKeyCode());
    }
    public void keyReleased(KeyEvent key) {
        gsm.keyReleased(key.getKeyCode());
    }
}
