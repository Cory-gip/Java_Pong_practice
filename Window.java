import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Window extends JFrame implements Runnable {

    public Graphics2D g2;
    public KL keyListener = new KL();
    public Rect playerOne, ai, ballRect, ballRect_2;
    public Text leftScoreText, rightScoreText;

    public Ball ball, ball_2;
    public PlayerController playerController;
    public AIController aiController;

    public Window() {
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.STRING_TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        Constants.TOOLBAR_HEIGHT = this.getInsets().top;
        Constants.INSETS_BOTTOM = this.getInsets().bottom;
        g2 = (Graphics2D)this.getGraphics();

        playerOne = new Rect(Constants.HZ_PADDING, 40, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Color.WHITE);
        playerController = new PlayerController(playerOne, keyListener);
        ai = new Rect(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH - Constants.HZ_PADDING, 40, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Color.WHITE);

        leftScoreText = new Text(0, new Font("Times New Roman", Font.PLAIN, Constants.TEXT_SIZE), Constants.TEXT_X_POS, Constants.TEXT_Y_POS);
        rightScoreText = new Text(0, new Font("Times New Roman", Font.PLAIN, Constants.TEXT_SIZE), Constants.SCREEN_WIDTH - Constants.TEXT_X_POS - Constants.TEXT_SIZE,Constants.TEXT_Y_POS);

        ballRect = new Rect(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, Constants.BALL_WIDTH, Constants.BALL_HEIGHT, Color.WHITE);
        ballRect_2 = new Rect((Constants.SCREEN_WIDTH / 2) + (2 * Constants.BALL_WIDTH), Constants.SCREEN_HEIGHT / 2, Constants.BALL_WIDTH, Constants.BALL_HEIGHT, Color.BLUE);
        ball = new Ball(ballRect, playerOne, ai, leftScoreText, rightScoreText);
        //ball_2 = new Ball(ballRect_2, playerOne, ai, leftScoreText, rightScoreText);

        aiController = new AIController(new PlayerController(ai), ballRect);
    }

    public void update(double dt) {
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        playerController.update(dt);
        aiController.update(dt);
        ball.update(dt);
        //ball_2.update(dt);


       // Rect rect = new Rect(50, Constants.SCREEN_HEIGHT - 100, 40, 80, Color.BLUE);
       // rect.draw(g2);

        if (keyListener.isKeyPressed(KeyEvent.VK_UP)) {
            System.out.println("The user is pressing the up arrow");
        } else if (keyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
            System.out.println("The user is pressing the down arrow");
        }
        //System.out.println("" + dt + " seconds have passed since the last frame");
        // System.out.println(1 / dt + " fps");
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        Font font = new Font("Times New Roman", Font.PLAIN, 14);
        leftScoreText.draw(g2);
        rightScoreText.draw(g2);
        playerOne.draw(g2);
        ai.draw(g2);
        ballRect.draw(g2);
        //ballRect_2.draw(g2);


    }

    public void run() {
        double lastFrameTime = 0.0;
        while (true) {
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;

            update(deltaTime);

             /* try {
                Thread.sleep(30);
            } catch (Exception e) {

            } */
        }
    }




}

