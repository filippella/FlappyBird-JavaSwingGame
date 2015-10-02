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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Filippo-TheAppExpert
 */
public abstract class Canvas extends JPanel implements KeyListener {

    private Clip clip;

    public Canvas() {
        this.addKeyListener(Canvas.this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        draw(g2D);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        onKeyDown(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        onKeyUp(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    protected synchronized void playSound(String soundURL) {
        try {
            clip = AudioSystem.getClip();
            InputStream audioSrc = getClass().getResourceAsStream(soundURL);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);

            AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedIn);
            clip.open(inputStream);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.err.println("Error --> " + e.getMessage());
        }
    }

    public abstract void onKeyUp(KeyEvent e);

    public abstract void onKeyDown(KeyEvent e);

    public abstract void draw(Graphics2D g2D);
}
