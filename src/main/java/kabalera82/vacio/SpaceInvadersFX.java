package kabalera82.vacio;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SpaceInvadersFX extends Application {

    // === Config ===
    private static final int TILE = 32, ROWS = 16, COLS = 16;
    private static final int W = TILE * COLS, H = TILE * ROWS;

    // === Estado dibujo ===
    private Canvas canvas;
    private GraphicsContext g;

    // === Sprites (opcionales) ===
    private Image shipImg, alienGreen, alienCyan, alienMagenta, alienYellow;
    private final ArrayList<Image> alienImgs = new ArrayList<>();

    // === Entidad base ===
    private static class Block {
        int x, y, w, h;
        Image img;
        boolean alive = true; // aliens
        boolean used  = false; // balas
        Block(int x, int y, int w, int h, Image img) {
            this.x = x; this.y = y; this.w = w; this.h = h; this.img = img;
        }
    }

    // === Nave ===
    private final int shipW = TILE * 2, shipH = TILE;
    private final int shipStartX = TILE * COLS / 2 - TILE, shipStartY = TILE * ROWS - TILE * 2;
    private final int shipVX = TILE; // paso de casilla (como en Swing)
    private Block ship;

    // === Aliens ===
    private final ArrayList<Block> aliens = new ArrayList<>();
    private final int alienW = TILE * 2, alienH = TILE;
    private final int alienStartX = TILE, alienStartY = TILE;
    private int alienRows = 2, alienCols = 3, alienCount = 0, alienVX = 1;

    // === Balas ===
    private final ArrayList<Block> bullets = new ArrayList<>();
    private final int bulletW = TILE / 8, bulletH = TILE / 2, bulletVY = -10;

    // === Juego ===
    private boolean gameOver = false;
    private int score = 0;
    private AnimationTimer loop;

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(W, H);
        g = canvas.getGraphicsContext2D();

        // Carga opcional de imágenes (si faltan, se dibujan rectángulos y listo)
        shipImg     = load("ship.png");
        alienGreen  = load("alien.png");
        alienCyan   = load("alien-cyan.png");
        alienMagenta= load("alien-magenta.png");
        alienYellow = load("alien-yellow.png");

        if (alienGreen != null)  alienImgs.add(alienGreen);
        if (alienCyan != null)   alienImgs.add(alienCyan);
        if (alienMagenta != null)alienImgs.add(alienMagenta);
        if (alienYellow != null) alienImgs.add(alienYellow);
        if (alienImgs.isEmpty()) alienImgs.add(null); // fallback

        ship = new Block(shipStartX, shipStartY, shipW, shipH, shipImg);
        createAliens();

        Scene scene = new Scene(new StackPane(canvas), W, H, Color.BLACK);

        scene.setOnKeyPressed(e -> {
            if (gameOver) { restart(); return; }
            if (e.getCode() == KeyCode.LEFT  && ship.x - shipVX >= 0)             ship.x -= shipVX;
            else if (e.getCode() == KeyCode.RIGHT && ship.x + shipVX + ship.w <= W) ship.x += shipVX;
            else if (e.getCode() == KeyCode.SPACE) {
                Block b = new Block(ship.x + shipW * 15 / 32, ship.y, bulletW, bulletH, null);
                bullets.add(b);
            }
        });

        loop = new AnimationTimer() {
            @Override public void handle(long now) {
                update();
                draw();
                if (gameOver) stop();
            }
        };
        loop.start();

        stage.setTitle("Space Invaders (JavaFX)");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void update() {
        // Aliens
        for (Block a : aliens) {
            if (!a.alive) continue;
            a.x += alienVX;

            if (a.x + a.w >= W || a.x <= 0) {
                alienVX *= -1;
                a.x += alienVX * 2;
                for (Block all : aliens) all.y += alienH; // bajan todos
            }
            if (a.y >= ship.y) gameOver = true;
        }

        // Balas + colisiones
        for (Block b : bullets) {
            b.y += bulletVY;
            for (Block a : aliens) {
                if (!b.used && a.alive && hit(b, a)) {
                    b.used = true; a.alive = false; alienCount--; score += 100;
                }
            }
        }
        bullets.removeIf(b -> b.used || b.y < 0);

        // Siguiente oleada
        if (alienCount == 0) {
            score += alienCols * alienRows * 100;
            alienCols = Math.min(alienCols + 1, COLS / 2 - 2);
            alienRows = Math.min(alienRows + 1, ROWS - 6);
            aliens.clear(); bullets.clear();
            createAliens();
        }
    }

    private void draw() {
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, W, H);

        // Nave
        if (ship.img != null) g.drawImage(ship.img, ship.x, ship.y, ship.w, ship.h);
        else { g.setFill(Color.CORNFLOWERBLUE); g.fillRect(ship.x, ship.y, ship.w, ship.h); }

        // Aliens
        for (Block a : aliens) if (a.alive) {
            if (a.img != null) g.drawImage(a.img, a.x, a.y, a.w, a.h);
            else { g.setFill(Color.LIMEGREEN); g.fillRect(a.x, a.y, a.w, a.h); }
        }

        // Balas (contorno, como tu drawRect)
        g.setStroke(Color.WHITE);
        for (Block b : bullets) if (!b.used) g.strokeRect(b.x, b.y, b.w, b.h);

        // Marcador
        g.setFill(Color.WHITE);
        g.setFont(Font.font("Arial", 32));
        g.fillText(gameOver ? "Game Over: " + score : String.valueOf(score), 10, 35);
    }

    private void createAliens() {
        Random r = new Random();
        for (int c = 0; c < alienCols; c++) {
            for (int rr = 0; rr < alienRows; rr++) {
                Image img = alienImgs.get(r.nextInt(alienImgs.size()));
                aliens.add(new Block(
                        alienStartX + c * alienW,
                        alienStartY + rr * alienH,
                        alienW, alienH, img
                ));
            }
        }
        alienCount = aliens.size();
    }

    private static boolean hit(Block a, Block b) {
        return a.x < b.x + b.w && a.x + a.w > b.x && a.y < b.y + b.h && a.y + a.h > b.y;
    }

    private Image load(String name) {
        try { return new Image(getClass().getResourceAsStream("/" + name)); }
        catch (Exception e) { return null; }
    }

    private void restart() {
        ship.x = shipStartX;
        bullets.clear();
        aliens.clear();
        gameOver = false;
        score = 0;
        alienCols = 3;
        alienRows = 2;
        alienVX = 1;
        createAliens();
        loop.start();
    }

    public static void main(String[] args) { launch(args); }
}
