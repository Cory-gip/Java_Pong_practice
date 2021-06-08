//This class will handle all of the ball's functionality, keeping track of its velocity and location relative to
//the paddles, and updating the score accordingly.
public class Ball {
    public Rect rect;
    public Rect leftPaddle, rightPaddle;
    public Text leftScoreText, rightScoreText;

    private double vy = 10.0;
    private double vx = -90.0;

    public Ball(Rect rect, Rect leftPaddle, Rect rightPaddle, Text leftScoreText, Text rightScoreText) {
        this.rect = rect;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
        this.leftScoreText = leftScoreText;
        this.rightScoreText = rightScoreText;
    }

    //This method calculates the angle at which the ball will bounce off of the paddle.
    public double calculateNewVelocityAngle(Rect paddle) {
        double relativeIntersectY = (paddle.y + (paddle.height / 2.0) - (this.rect.y + (this.rect.height / 2.0)));
        double normalIntersectY = relativeIntersectY / (paddle.height / 2.0);
        double theta = normalIntersectY * Constants.MAX_ANGLE;

        return Math.toRadians(theta);
    }

    //First, this method will be checking whether the ball is moving left or right.
    //Then, it will check whether the ball is in contact with the paddle corresponding
    //to its direction of travel. If it is hitting a paddle, the new velocity and direction
    //will be assigned. If the ball is not hitting a paddle, this method will check whether the ball
    //has passed the corresponding paddle. If so, the opposing side's score will be incremented.
    public void update(double dt) {
        if (vx < 0) {
            if (this.rect.x <= this.leftPaddle.x + this.leftPaddle.width
                    && this.rect.x + this.rect.width >= this.leftPaddle.x
                    && this.rect.y >= this.leftPaddle.y
                    && this.rect.y <= this.leftPaddle.y + this.leftPaddle.height
                    && this.rect.x >= this.leftPaddle.x) {
                double theta = calculateNewVelocityAngle(leftPaddle);
                double newVx = Math.abs(Math.cos(theta)) * Constants.BALL_SPEED;
                double newVy = (-Math.sin(theta)) * Constants.BALL_SPEED;

                double oldSign = Math.signum(vx);
                this.vx = newVx * (-1.0 * oldSign);
                this.vy = newVy;
            } else if (this.rect.x + this.rect.width < this.leftPaddle.x) {
                System.out.println("You lost");
                this.rect.x = Constants.SCREEN_WIDTH / 2;
                this.rect.y = Constants.SCREEN_HEIGHT / 2;
                this.vx *= -0.9;
                int rightScore = Integer.parseInt(rightScoreText.text);
                rightScore++;
                rightScoreText.text = "" + rightScore;
            }
        } else if (vx > 0) {
            if (this.rect.x + this.rect.width >= this.rightPaddle.x &&
                    this.rect.x <= this.rightPaddle.x + this.rightPaddle.width &&
                    this.rect.y >= this.rightPaddle.y &&
                    this.rect.y <= this.rightPaddle.y + this.rightPaddle.height) {
                double theta = calculateNewVelocityAngle(rightPaddle);
                double newVx = Math.abs(Math.cos(theta)) * Constants.BALL_SPEED;
                double newVy = (-Math.sin(theta)) * Constants.BALL_SPEED;
                double oldSign = Math.signum(vx);
                this.vx = newVx * (-1.0 * oldSign);
                this.vy = newVy;
            } else if (this.rect.x + this.rect.width > this.rightPaddle.x + this.rightPaddle.width) {
                System.out.println("The AI has lost a point");
                this.rect.x = Constants.SCREEN_WIDTH / 2;
                this.rect.y = Constants.SCREEN_HEIGHT / 2;
                double oldSign = Math.signum(vx);
                this.vx *= (-0.9 * oldSign);
                int leftScore = Integer.parseInt(leftScoreText.text);
                leftScore++;
                leftScoreText.text = "" + leftScore;
            }
        }

        this.rect.x += vx * dt;
        this.rect.y += vy * dt;

        if (vy >= 0) {
            if (this.rect.y + this.rect.height > Constants.SCREEN_HEIGHT) {
                this.vy *= -1;
            }
        } else if (vy < 0) {
            if (this.rect.y < Constants.TOOLBAR_HEIGHT) {
                this.vy *= -1;
            }
        }
    }
}
