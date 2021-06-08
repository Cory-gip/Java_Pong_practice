import java.awt.event.KeyEvent;

//This class will use the key listener to allow the user to control the movement of the player paddle.
public class PlayerController {
    public Rect rect;
    public KL keyListener;

    public PlayerController(Rect rect, KL keyListener) {
        this.rect = rect;
        this.keyListener = keyListener;
    }

    public PlayerController(Rect rect) {
        this.rect = rect;
        this.keyListener = null;
    }

    //First checks to see if the user is pressing anything, then checks whether they are pressing
    //up or down, and moves the paddle up or down accordingly.
    public void update(double dt) {
        if (keyListener != null) {
            if (keyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
                moveDown(dt);
            } else if (keyListener.isKeyPressed(KeyEvent.VK_UP)) {
                moveUp(dt);
            }
        }
    }

    //Checks to see if the user paddle is already at the top of the screen;
    //if not, then the paddle will be moved up.
    public void moveUp(double dt) {
        if (rect.y - Constants.PADDLE_SPEED * dt > Constants.TOOLBAR_HEIGHT) {
            this.rect.y -= (Constants.PADDLE_SPEED * dt);
        }
    }

    //Checks to see if the user paddle is already at the bottom of the screen;
    //if not, then the paddle will be moved down.
    public void moveDown(double dt) {
        if ((rect.y + Constants.PADDLE_SPEED * dt) + rect.height < Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM) {
            this.rect.y += (Constants.PADDLE_SPEED * dt);
        }
    }
}