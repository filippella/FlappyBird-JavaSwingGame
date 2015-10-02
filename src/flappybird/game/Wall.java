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
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author Filippo-TheAppExpert
 */
public class Wall extends Sprite implements Helper {

    private final int randomHeight;
    private boolean isScored;

    public Wall(int x, int y, int speed) {
        super(x, y, speed);
        randomHeight = new Random().nextInt(300) + 50;
    }

    public void update() {
        setX(getX() - getSpeed());
    }

    @Override
    public void draw(Graphics2D g2D) {
        g2D.setColor(Color.GREEN.darker());
        g2D.fill3DRect(getX(), getY(), 50, PANEL_HEIGHT - randomHeight - 150, true);
        g2D.fill3DRect(getX(), getY() + PANEL_HEIGHT - randomHeight, 50, randomHeight, true);
    }

    public Rectangle getTopBound() {
        return new Rectangle(getX(), getY(), 50, PANEL_HEIGHT - randomHeight - 150);
    }

    public Rectangle getBottomBound() {
        return new Rectangle(getX(), getY() + PANEL_HEIGHT - randomHeight, 50, randomHeight);
    }

    public void setIsScored(boolean isScored) {
        this.isScored = isScored;
    }

    public boolean isScored() {
        return isScored;
    }
}
