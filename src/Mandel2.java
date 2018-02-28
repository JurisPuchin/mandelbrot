import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

/**
 * Created by jpuchin on 5/15/2017.
 */
public class Mandel2 extends JFrame {
    public static final int MAX_ITER = 800;
    private final double ZOOM = 150;
    private final double ZOOM_SPEED = 1.01;
    public double cX, cY, zx, zy, xtmp, ytmp;
    double thisZoom = 0;
    double mouseX = 0;
    double mouseY = 0;
    private BufferedImage I;

    static int intIntoGRB(int val){
        double v = val / (double) MAX_ITER;
        int r = (int) floor(min(max(0, 1.5 - abs(1 - 4* (v - 0.5))),1)* 255);
        int g = (int) floor(min(max(0, 1.5 - abs(1 - 4* (v - 0.25))),1)* 255);
        int b = (int) floor(min(max(0, 1.5 - abs(1 - 4* v)),1)* 255);
        int rgb = r;
        rgb = (rgb << 8) | g;
        rgb = (rgb << 8) | b;
        return rgb;
    }

    Mandel2() {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_RGB);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = (e.getX() - 400) / thisZoom + mouseX;
                mouseY = (e.getY() - 300) / thisZoom + mouseY;
            }
        });
        setVisible(true);
        int itr = 0;
        while(true) {
            thisZoom = ZOOM * Math.pow(ZOOM_SPEED, itr);
            for (int y = 0; y < getHeight(); y++) {
                for (int x = 0; x < getWidth(); x++) {
                    zx = 0;
                    zy = 0;
                    cX = (x - 400) / thisZoom + mouseX;
                    cY = (y - 300) / thisZoom + mouseY;
                    int iter = MAX_ITER;
                    while (zx * zx + zy * zy < 4 && iter > 0) {
                        xtmp = zx * zx - zy * zy + cX;
                        ytmp = 2.0 * zx * zy + cY;
                        zx = xtmp;
                        zy = ytmp;
                        iter--;
                    }
                    I.setRGB(x, y, intIntoGRB(iter));
                }
            }
            repaint();
            itr++;
        }
    }
    @Override
    public void paint(Graphics g){
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        new Mandel2();
    }
}
