import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameFiles extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    
    @Override
    public void actionPerformed(ActionEvent e) {

        move();
        repaint();
        if(gameEnd == true)
        {
            time.stop();
        }

    }

int boardWidth;
int boardHeight;
int tileSize = 15;


// snake food
Tile food;
Random rand;


Timer time;
int speed = 100;
int velocityX;
int velocityY;
boolean gameEnd = false;

GameFiles(int boardWidth, int boardHeight) {
    this.boardWidth = boardWidth;
    this.boardHeight = boardHeight;
    setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));

    setBackground(Color.black);
    addKeyListener(this);
    setFocusable(true);

//snake
    snakeHead = new Tile(5, 5);
    snakeBody = new ArrayList<Tile>();

    // apples
        food = new Tile(25, 25);
        rand = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        Timer time = new Timer(100, this);
        time.start();

    }


    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        draw(graphics);
    }

        public void draw(Graphics graphics)
        {

                // apple color and location
                graphics.setColor(Color.red);
                graphics.fillRect(food.x * tileSize, food.y *tileSize, tileSize, tileSize);
                graphics.fill3DRect(food.x * tileSize, food.y *tileSize, tileSize, tileSize, true);

            // snake's head
           graphics.setColor(Color.green);
           //g.fillRect(snakeHead.x * tileSize, snakeHead.y *tileSize, tileSize, tileSize);
           graphics.fill3DRect(snakeHead.x * tileSize, snakeHead.y *tileSize, tileSize, tileSize, true);


        // snake's body
            for(int i = 0; i < snakeBody.size(); i++)
            {
                Tile snakePart = snakeBody.get(i);
                // g.fillRect(snakePart.x *tileSize, snakePart.y * tileSize, tileSize, tileSize);
                graphics.fill3DRect(snakePart.x *tileSize, snakePart.y * tileSize, tileSize, tileSize, true);

            }
           
            // win and lose conditions
            graphics.setFont(new Font("Arial", Font.PLAIN, (int) 16.7));
            if (gameEnd)
            {
                graphics.setColor(Color.red);
                graphics.drawString("Game Over, uninstall. Score was: " + String.valueOf(snakeBody.size()), tileSize - (int) 16.7, tileSize);
            }
           
            else
            {
                graphics.drawString("Score is: " + String.valueOf(snakeBody.size()), tileSize - (int) 16.7, tileSize);
            }
        }

        public void placeFood()
        {
            food.x = rand.nextInt(boardWidth/tileSize);
            food.y = rand.nextInt(boardHeight/tileSize);
        }

        public boolean collision(Tile tile1, Tile tile2)
        {
            return tile1.x == tile2.x && tile1.y == tile2.y;
        }

        public void move()
        {
   
            // eating apple johnson packs
                if (collision(snakeHead, food))
                {
                    snakeBody.add(new Tile(food.x, food.y));
                    snakeBody.add(new Tile(food.x, food.y));
                    snakeBody.add(new Tile(food.x, food.y));

                    placeFood();
                    
                }

                //snake body moving
                for (int i = snakeBody.size()-1; i >= 0; i--)
                {
                    Tile snakePart = snakeBody.get(i);
                    if (i ==0)
                    {
                    snakePart.x = snakeHead.x;
                    snakePart.y = snakeHead.y;
                    }
                    else
                    {
                        Tile previousPart = snakeBody.get(i-1);
                        snakePart.x = previousPart.x;
                        snakePart.y = previousPart.y;
                    }
                }
   
            snakeHead.x += velocityX;
            snakeHead.y += velocityY;

            // game over conditions
                for (int i = 0; i < snakeBody.size(); i++) {

                    Tile snakePart = snakeBody.get(i);
                    if (collision(snakeHead, snakePart)) {
                        gameEnd = true;
                    }
                }


                if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight) {
                        gameEnd = true;
                    }

        }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_UP && velocityY == 0)
                {
                    velocityX = 0;
                    velocityY = -1;
                }

                else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1)
                {
                    velocityX = 0;
                    velocityY = 1;
                }

                else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1)
                {
                    velocityX = -1;
                    velocityY = 0;
                }

                else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1)
                {
                    velocityX = 1;
                    velocityY = 0;
                }

            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
            
}
