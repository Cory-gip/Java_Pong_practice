import javax.swing.JFrame;
import java.awt.*;

//This class sets up the window and runs the game of Pong, rendering all of the objects to the window
//and updating them according to the time.
public class Window extends JFrame implements Runnable {

    public Graphics2D g2;
    public KL keyListener = new KL();
    public Rect playerOne, ai, ballRect;
    public Text leftScoreText, rightScoreText;

    public Ball ball;
    public PlayerController playerController;
    public AIController aiController;

    //This will set up the window and preload all of the objects.
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
        ai = new Rect(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH - Constants.HZ_PADDING, 40,
                Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Color.WHITE);

        leftScoreText = new Text(0, new Font("Times New Roman", Font.PLAIN, Constants.TEXT_SIZE),
                Constants.TEXT_X_POS, Constants.TEXT_Y_POS);
        rightScoreText = new Text(0, new Font("Times New Roman", Font.PLAIN, Constants.TEXT_SIZE),
                Constants.SCREEN_WIDTH - Constants.TEXT_X_POS - Constants.TEXT_SIZE,Constants.TEXT_Y_POS);

        ballRect = new Rect(Constants.SCREEN_WIDTH / 2,
                Constants.SCREEN_HEIGHT / 2, Constants.BALL_WIDTH, Constants.BALL_HEIGHT, Color.WHITE);
        ball = new Ball(ballRect, playerOne, ai, leftScoreText, rightScoreText);


        aiController = new AIController(new PlayerController(ai), ballRect);
    }

    //This method calls all of the objects' update methods in the window.
    public void update(double dt) {
        //The four lines of code below implement what is known as double buffering to reduce flickering
        //and allow the game to run smoothly.
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        playerController.update(dt);
        aiController.update(dt);
        ball.update(dt);
    }

    //This method draws all of the objects to the screen.
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
    }
    //This method runs the game by updating the lastFrameTime and using that in turn to
    //calculate deltaTime, which will be passed into all of the update functions as dt.
    public void run() {
        double lastFrameTime = 0.0;
        while (true) {
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;
            update(deltaTime);
        }
    }
}

