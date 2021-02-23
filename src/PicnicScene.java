/**********************
 * Picnic Scene
 * Author: Kara achmar
 * CSC345 Spring 2021
 *
 * Generates animated picnic scene using Java Graphics2D.
 **********************/
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class PicnicScene extends JPanel{

    public static void main(String[] args){
        JFrame window;
        window = new JFrame("Picnic Time!");
        PicnicScene panel = new PicnicScene(); //creates panel
        window.setContentPane(panel); //add panel to main window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exits on close
        window.pack(); //set size based on preferred sz of contents
        window.setResizable(true); //window cannot be resized by user
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        //Add animation timer
        Timer animationTimer; //declare timer
        final long startTime = System.currentTimeMillis();
        animationTimer = new Timer(16, new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                panel.frameNumber++; //advance to next frame
                panel.repaint();
            }
        });

        //center window on screen by taking average x and y
        window.setLocation((screen.width - window.getWidth())/2, (screen.height - window.getHeight())/2);
        window.setVisible(true); //open and display window

        // start timer
        animationTimer.start();
    }

    private float pixelSize; //measure of pizel in coordinate system
    private int frameNumber = 0; //sets animation frame to start (0)

    //constructor
    public PicnicScene(){
        setPreferredSize(new Dimension(900, 800));
    }

    //paint component method -- draws content of JPanel
    protected void paintComponent(Graphics g){
        //drawing context is graphics2D
        Graphics2D g2 = (Graphics2D) g.create();

        //turn on antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //background-sky
        g2.setPaint(new Color(10,200,250)); //hex #66ccff
        g2.fillRect(0,0,getWidth(), 500);

        //background-grass
        g2.setPaint(new Color(0, 187, 99));//hex #5DBB63
        g2.fillRect(0,500,getWidth(), 500);

        //create new coordinate system
        applyWindowToViewportTransformation(g2, -5, 10, -1, 14,true);

        drawScene(g2);
    }

    //draw scene
    private void drawScene(Graphics2D g2){
        drawMainScene(g2);
    }

    private  void drawMainScene(Graphics2D g2){
        drawLake(g2);
        drawSunWithRays(g2);
        drawBird(g2);
        drawTree(g2, -10, 0);
        drawBlanket(g2);
        drawPerson(g2);

        drawTree(g2, 1025, 0);

    }

    private void drawLake(Graphics2D g2) {
        g2.setPaint(Color.BLUE);
        AffineTransform cs = g2.getTransform();
        Path2D lake = new Path2D.Double();
        lake.moveTo(100, 100);
        lake.curveTo(300, -80, 400, -80, 600, 100);
        lake.closePath();
        g2.scale(0.02, 0.02);
        g2.translate(-200, 131);
        g2.fill(lake);
        g2.setTransform(cs);
    }

    private void drawSun(Graphics2D g2, int layerNumber){
        AffineTransform cs = g2.getTransform();
        double sunHeight = 10;
        double startX = 2.5;
        double startSize = 1.5;
        int layer = layerNumber;
        double layerFactor = layerNumber*.3;
        int maxAlpha = 255;
        double frameAdjust = 0;
        if (frameNumber%60 <= 30){
            frameAdjust = layerFactor*.15;
        }
        g2.setPaint(new Color(245, 227, 66, maxAlpha-(layerNumber*45)));
        g2.translate(0, 2.7);
        Ellipse2D.Double sun = new Ellipse2D.Double(startX-layerFactor, sunHeight-layerFactor, 2.2+(layerFactor*2), 2.2+frameAdjust+(layerFactor*2));
        g2.fill(sun);
        g2.setTransform(cs);
    }

    private void drawSunWithRays(Graphics2D g2){
        AffineTransform cs = g2.getTransform();
        drawSun(g2, 4);
        drawSun(g2, 3);
        drawSun(g2, 2);
        drawSun(g2, 1);
        drawSun(g2, 0);
        g2.setTransform(cs);
    }

    private void drawBird(Graphics2D g2){
        double moveFactor = 0;
        AffineTransform cs = g2.getTransform();
        g2.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setPaint(Color.BLACK);

        AffineTransform cs2 = g2.getTransform();  // Save C.S. state
        Path2D leftWing = new Path2D.Double();
        Path2D rightWing = new Path2D.Double();

        moveFactor = frameNumber;

        double newX = 0+moveFactor;

        double flapFactor = 0;
        double flapRange = frameNumber%40;

        if (flapRange<20){
            flapFactor = flapRange;
        } else {
            flapFactor = -flapRange;
        }

        double bodyMovement = 0;

        if (frameNumber%40 <20){
            bodyMovement--;
        } else {
            bodyMovement++;
        }

        leftWing.moveTo(newX, 100+bodyMovement);
        leftWing.curveTo(10+moveFactor, 120, 50+moveFactor, 150, 70+newX, 130+flapFactor);
        rightWing.moveTo(newX,100+bodyMovement);
        rightWing.curveTo(-10+moveFactor, 120, -50+moveFactor, 150, -70+newX, 130+flapFactor);
        g2.scale(0.007, 0.007);
        g2.translate(0, 1600);
        g2.scale(.85, .85);
        g2.draw(leftWing);
        g2.draw(rightWing);
        g2.setTransform(cs2);

    }

    private void drawTree(Graphics2D g2, int xLoc, int yLoc){
        g2.setPaint(new Color(122, 90, 0));
        AffineTransform cs = g2.getTransform();
        Path2D tree = new Path2D.Double();
        tree.moveTo(50, 500);
        tree.curveTo(100, 400, 100, 200, 0, 0);
        tree.lineTo(100, 75);
        tree.lineTo(150, 0);
        tree.lineTo(200, 75);
        tree.lineTo(300,0);
        tree.curveTo(200, 200, 200, 400, 250, 500);
        tree.closePath();
        g2.scale(0.022, 0.022);
        g2.translate(-155, 205);

        Ellipse2D.Double leaves = new Ellipse2D.Double(-50, 350,400, 400);

        g2.scale(.55, .55);
        g2.translate(xLoc-100, yLoc-250);

        g2.fill(tree);

        g2.setPaint(new Color(13, 115, 8));
        g2.fill(leaves);

        g2.setTransform(cs);
    }

    private void drawBlanket(Graphics2D g2){
        AffineTransform cs = g2.getTransform();
        g2.scale(.3,.3);
        g2.translate(17.5, 6);
        AffineTransform cs2 = g2.getTransform();
        g2.shear(1,0);
        Rectangle2D.Double blanket = new Rectangle2D.Double(0, 0, 7, 8);
        g2.setPaint(Color.WHITE);
        g2.fill(blanket);
        g2.setTransform(cs2);
        drawApple(g2);
        drawPicnicBasket(g2);
        g2.setTransform(cs);
    }

    private void drawApple(Graphics2D g2){
        Ellipse2D.Double apple = new Ellipse2D.Double(0, 0, 2,2);
        g2.translate(6.5, 6);
        g2.scale(.4,.4);
        g2.setPaint(Color.RED);
        g2.fill(apple);
    }

    private void drawPicnicBasket(Graphics2D g2){
        Rectangle2D.Double basket = new Rectangle2D.Double(0, 0, 5,3.5);
        g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setPaint(new Color(122, 78, 23));
        Path2D handle = new Path2D.Double();
        handle.moveTo(1, 3.4);
        handle.curveTo(2, 4.4, 3, 4.4, 4, 3.4);

        g2.translate(6.5, 4);
        //g2.scale(.5,.5);
        g2.setPaint(new Color(122, 78, 23));
        g2.draw(handle);
        g2.fill(basket);
    }

    private void drawPerson(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();
        g2.scale(.1, .1);
        g2.translate(0, 10);
        Ellipse2D.Double head = new Ellipse2D.Double(0, 0, 10, 11.5);
        Path2D.Double body = new Path2D.Double();
        body.moveTo(5, 0);
        body.lineTo(5, -10);
        body.moveTo(5, -10);
        body.lineTo(7, -20);
        body.moveTo(5, -10);
        body.lineTo(3, -20);
        g2.setPaint(Color.BLACK);
        g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(head);
        g2.draw(body);

        g2.setPaint(Color.WHITE);
        g2.fill(head);



        g2.setTransform(cs);
    }



    //window to viewport transformation
    private void applyWindowToViewportTransformation(Graphics2D g2, double left, double right, double bottom, double top, boolean preserveAspect){
        int width = getWidth(); //width drawing area
        int height = getHeight(); //height drawing area
        if (preserveAspect){
            //adjust limits to match aspect ratio of drawing area
            double displayAspect = Math.abs((double)height/width);
            double requestedAspect = Math.abs((bottom-top)/(right-left));
            if(displayAspect > requestedAspect){
                //expand viewport vertically
                double excess = (bottom-top)*(displayAspect/requestedAspect-1);
                bottom += excess/2;
                top -= excess/2;
            } else if (displayAspect < requestedAspect){
                //expand viewport horizontally
                double excess = (right-left) * (requestedAspect/displayAspect - 1);
                right += excess/2;
                left += excess/2;
            }
        }

        g2.scale(width/(right-left), height/(bottom-top));
        g2.translate(-left, -top);
        double pixelWidth = Math.abs((right-left)/width);
        double pixelHeight = Math.abs((bottom - top)/height);
        pixelSize = (float)Math.max(pixelWidth, pixelHeight);
    }

}
