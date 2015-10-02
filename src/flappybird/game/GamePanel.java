/*
 * The MIT License
 *
 * Copyright (c) 2015 Filippo Engidashet <filippo.eng@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package flappybird.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Filippo-TheAppExpert
 */
public class GamePanel extends Canvas implements Runnable, Helper {

    private Thread thread;
    private boolean isRunning;
    private final ArrayList<Wall> walls = new ArrayList<>();
    private Bird flappyBird = new Bird(PANEL_WIDTH / 4 + 25, 100, 0);
    private boolean gameOver;
    private int score;

    private boolean gameStarted;

    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setDoubleBuffered(true);
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(GamePanel.this);
        }
    }

    @Override
    public void onKeyUp(KeyEvent e) {
        flappyBird.setDegree(-80);
    }

    @Override
    public void onKeyDown(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            restartGame();
        } else if (e.getKeyCode() == KeyEvent.VK_G) {
            startGame();
        } else if(gameStarted && !gameOver) {
            flappyBird.jump();
            playSound("/flappybird/game/assets/sounds/jump.wav");
        }
        flappyBird.setDegree(0);
    }

    @Override
    public void draw(Graphics2D g2D) {
        g2D.setColor(Color.CYAN.brighter());
        g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        walls.stream().forEach((wall) -> {
            wall.draw(g2D);
        });
        if (flappyBird != null) {
            flappyBird.draw(g2D);
        }

        g2D.setColor(Color.white);
        Font scoreFont = new Font("Helvetica", 1, 100);
        FontMetrics metrics = getFontMetrics(scoreFont);
        g2D.setFont(scoreFont);

        String scoreText = String.valueOf(getScore());

        g2D.drawString(scoreText, (PANEL_WIDTH - metrics.stringWidth(scoreText)) / 2, 100);

        if (!gameStarted) {
            Font gameOverFont = new Font("Verdana", 1, 18);
            FontMetrics gameOverMetrics = getFontMetrics(gameOverFont);
            g2D.setFont(gameOverFont);
            g2D.drawString(GAME__START, (PANEL_WIDTH - gameOverMetrics.stringWidth(GAME__START)) / 2, getHeight() / 2);
            g2D.drawString(GAME__JUMP, (PANEL_WIDTH - gameOverMetrics.stringWidth(GAME__JUMP)) / 2, getHeight() / 2 + 30);
        }

        if (gameOver) {
            Font gameOverFont = new Font("Arial", 1, 20);
            FontMetrics gameOverMetrics = getFontMetrics(gameOverFont);
            g2D.setFont(gameOverFont);
            g2D.setColor(Color.red);
            g2D.drawString(GAME__OVER, (PANEL_WIDTH - gameOverMetrics.stringWidth(GAME__OVER)) / 2, getHeight() / 2);
        }
    }

    @Override
    public void run() {
        init();
        while (isRunning) {
            long startTime = System.currentTimeMillis();

            updateGame();
            renderGame();

            long difference = System.currentTimeMillis() - startTime;
            long waitTime = (1000L / 40) - difference / 1000L;
            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {
                System.err.println("Error --> " + e.getMessage());
            }
        }
    }

    private void init() {
        isRunning = true;
        gameOver = false;
        score = 0;
        walls.clear();
        walls.add(new Wall(PANEL_WIDTH, 0, 3));
    }

    private void renderGame() {
        repaint();
    }

    private void updateGame() {
        flappyBird.update();
        if (flappyBird.getY() > PANEL_HEIGHT - 50) {
            isRunning = false;
        }

        if (!gameOver) {
            for (int i = 0; i < walls.size(); i++) {
                Wall wall = walls.get(i);
                wall.update();

                if (wall.getTopBound().intersects(flappyBird.getBound()) || wall.getBottomBound().intersects(flappyBird.getBound())) {
                    gameOver = true;
                    playSound("/flappybird/game/assets/sounds/hit.wav");
                    playSound("/flappybird/game/assets/sounds/fall.wav");
                    flappyBird.setDegree(-80);
                    break;
                }

                if (flappyBird.getX() > wall.getX() && flappyBird.getX() < wall.getX() + 50 && !wall.isScored()) {
                    wall.setIsScored(true);
                    score++;
                    playSound("/flappybird/game/assets/sounds/point.wav");
                }

                if (wall.getX() < -50) {
                    walls.remove(wall);
                } else if (wall.getX() % 80 == 0) {
                    walls.add(new Wall(PANEL_WIDTH, 0, 3));
                }
            }
        }
    }

    private void startGame() {
        thread.start();
        gameStarted = true;
    }

    private void restartGame() {
        thread.stop();
        thread = null;
        gameStarted = true;
        flappyBird = new Bird(PANEL_WIDTH / 4 + 25, 100, 0);
        addNotify();
        thread.start();
    }

    private Object getScore() {
        return score;
    }
}
