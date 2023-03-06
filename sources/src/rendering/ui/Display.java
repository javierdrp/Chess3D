package rendering.ui;

import data.Data;
import rendering.renderdata.Global;
import rendering.lighting.DirectionalLight;
import rendering.model.Board3D;
import rendering.renderer.MeshBuffer;
import rendering.renderer.Renderer;
import rendering.renderer.camera.Camera;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;
import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Display extends Canvas implements Runnable {

    private static final int FPS = 60;
    private int width;
    private int height;
    private boolean running;

    // rotation
    int startX, startY;
    boolean rotating = false;

    HashMap<Character, Boolean> activeKeys;

    private Thread thread;
    private final JFrame frame;
    public JPanel pnlCanvas;

    public Display(String title, int width, int height) {
        this.frame = new JFrame(title);
        this.width = width;
        this.height = height;
        this.running = false;
        Dimension size = new Dimension(width, height);
        this.setPreferredSize(size);
        pnlCanvas = new JPanel();

        Global.display = this;
        Global.camera = new Camera();
        Global.zoom = 1;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        pnlCanvas.setLayout(new OverlayLayout(pnlCanvas));

        Global.light = new DirectionalLight(5, 4, 3);

        activeKeys = new HashMap<>();
        KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(activeKeys.get(e.getKeyChar()) != null) activeKeys.replace(e.getKeyChar(), true);
                else activeKeys.put(e.getKeyChar(), true);
                //System.out.println("pressed " + e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(activeKeys.get(e.getKeyChar()) != null) activeKeys.replace(e.getKeyChar(), false);
                else activeKeys.put(e.getKeyChar(), false);
                //System.out.println("released " + e.getKeyChar());
            }
        };
        this.addKeyListener(keyListener);

        // zoom
        this.addMouseWheelListener(e -> Global.camera.zoomIn(e.getPreciseWheelRotation() * 5));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(SwingUtilities.isRightMouseButton(e)) {
                    startX = frame.getMousePosition().x;
                    startY = frame.getMousePosition().y;
                    rotating = true;
                    frame.setCursor(frame.getToolkit().createCustomCursor(
                            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
                            "null"));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(SwingUtilities.isRightMouseButton(e))
                    rotating = false;
                    frame.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(SwingUtilities.isLeftMouseButton(e) && !rotating)
                    Renderer.click(e.getX(), e.getY());
            }
        });

        pnlCanvas.add(this);
        frame.add(pnlCanvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public synchronized void start() {

        // load meshes
        Global.meshBuffer = new MeshBuffer();
        Board3D.prepare();
        Board3D.loadGame(Board3D.newGame);

        running = true;
        thread = new Thread(this, "renderer.Display");
        this.thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        // refresh rate control vars
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double ns = 1000000000.0 / FPS;
        double delta = 0;
        int ups = 0;
        int k = 0;

        Global.camera.rotate(35, 30, 0);

        while (running) {
            // refresh rate control
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            // don't skip refreshes if it lags
            while (delta >= 1) {
                update();
                delta--;
                render();
                ups++;
            }

            k++;

//            // show updates per second
//            if(System.currentTimeMillis() - timer > 1000) {
//                timer += 1000;
//                this.frame.setTitle("3DRenderer" + " | " + ups);
//                ups = 0;
//            }


        }

        stop();
    }

    private void update() {

        Board3D.loadGame(Data.game);
        this.width = frame.getWidth();
        this.height = frame.getHeight();
        setSize(width, height);
        pnlCanvas.setSize(width, height);

        Point mousePos = frame.getMousePosition();
        // rotate camera
        if(rotating) {
            if(mousePos != null) Global.camera.rotate((double)(mousePos.y - startY) / 3, (double)(mousePos.x - startX) / 3, 0);
            try {
                Robot robot = new Robot();
                robot.mouseMove(frame.getX() + startX, frame.getY() + startY);
            } catch (AWTException ex) {
            }
        }
    }



    private void render() {

        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Renderer.graphics = bs.getDrawGraphics();

        Renderer.clear();
        Renderer.render(Renderer.FILL);
//        Renderer.render(Renderer.WIREFRAME);

        Renderer.graphics.dispose();
        bs.show();
    }

}
