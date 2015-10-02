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

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Filippo-TheAppExpert
 */
public class Bird extends Sprite implements Helper {

    private final Image image;
    private double degree = -80;

    public Bird(int x, int y, int speed) {
        super(x, y, speed);
        image = new ImageIcon(getClass().getResource("/flappybird/game/assets/images/flappy_bird.png")).getImage();
    }

    /**
     * This method is called in every iteration of the loop by the running
     * thread to change the value of speed
     */
    public void update() {
        if (getSpeed() < 15) {
            setSpeed(getSpeed() + 1);
        }
        setY(getY() + getSpeed());
    }

    /**
     *
     * @param g2D
     */
    @Override
    public void draw(Graphics2D g2D) {

        //AffineTransform transform = AffineTransform.getTranslateInstance(getX(), getY());
        //transform.rotate(degree, image.getWidth(null) / 2, image.getHeight(null) / 2);
        //g2D.drawImage(image, transform, null);
        g2D.drawImage(image, getX(), getY(), null);
    }

    /**
     * This method is called whenever there is a user input from the keyboard
     * and let flappy bird jump
     */
    public void jump() {
        if (getSpeed() > 0) {
            setSpeed(0);
        }
        setSpeed(getSpeed() - 10);
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    public Rectangle getBound() {
        return new Rectangle(getX(), getY(), image.getWidth(null), image.getHeight(null));
    }
}
