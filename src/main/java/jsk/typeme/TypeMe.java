package jsk.typeme;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

@SpringBootApplication
public class TypeMe extends JPanel
        implements KeyListener, ActionListener {

    private String letter = "A";
    private final Color[]  colors = {   Color.BLUE,
                                        Color.CYAN,
                                        //Color.DARK_GRAY,
                                        //Color.GRAY,
                                        Color.GREEN,
                                        //Color.LIGHT_GRAY,
                                        Color.MAGENTA,
                                        Color.ORANGE,
                                        Color.PINK,
                                        Color.RED,
                                        Color.WHITE,
                                        Color.YELLOW };
    private final Random rand = new Random();

    public TypeMe() {
        addKeyListener(this);
        setFocusable(true);

    }


    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(TypeMe.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {
            TypeMe ex = ctx.getBean(TypeMe.class);
            JFrame frame = new JFrame("Type Me");
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            //frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.add(ex);
            frame.setMinimumSize(new Dimension(800, 600));
            frame.setVisible(true);
        });
    }

    private Color getColor() {
        return this.colors[this.rand.nextInt(this.colors.length)];

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        g2d.setBackground(Color.BLACK);
        g2d.setColor(Color.BLACK);
        Rectangle bounds = g2d.getClipBounds();
        g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g2d.setColor(this.getColor());
        g2d.setFont(new Font("Cooper Black", Font.PLAIN, 600));
        FontMetrics fm   = g2d.getFontMetrics(g2d.getFont());
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(this.letter, g2d);
        int textHeight = (int)(rect.getHeight());
        int textWidth  = (int)(rect.getWidth());

        // Center text horizontally and vertically within provided rectangular bounds
        int textX = (bounds.width - textWidth)/2;
        int textY = (bounds.height - textHeight)/2 + fm.getAscent();


        g2d.drawString(this.letter, textX, textY);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.printf("key typed %c %6d   shift %6b alt %6b  ctrl %6b\n", e.getKeyChar(),
        //        e.getExtendedKeyCode(), e.isShiftDown(), e.isAltDown(), e.isControlDown());


//        System.out.printf("%c %42d isAlphabetic %b  isDigit %b\n",
//                e.getKeyChar(), e.getKeyCode(), Character.isAlphabetic(e.getKeyChar()), Character.isDigit(e.getKeyChar()));

        Character c = e.getKeyChar();

        if(Character.isAlphabetic(c) || Character.isDigit(c)) {
            this.letter = Character.toString(c).toUpperCase();
            this.repaint();
        }
    }




    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q && e.isControlDown() && e.isAltDown()) {
            System.out.println("ctrl-alt-Q pressed - exiting");
            System.exit(0);
        }

        //System.out.printf("key typed %6d %6d   alt %b  ctrl %b\n",e.getKeyCode(), e.getExtendedKeyCode(), e.isAltDown(), e.isControlDown());
        //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));

    }
}