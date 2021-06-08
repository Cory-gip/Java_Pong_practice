//This class will govern the AI paddle for the game. It will only react to the ball.
public class AIController {
     public PlayerController playerController;
     public Rect ball;

     public AIController(PlayerController playerController, Rect ball) {
         this.playerController = playerController;
         this.ball = ball;
     }
    //This method will check for the location of the ball; if the ball is above the paddle,
    //the paddle will move up, and if the ball is below, then the paddle will move down.
     public void update(double dt) {
         playerController.update(dt);

         if (ball.y < playerController.rect.y) {
             playerController.moveUp(dt);
         } else if (ball.y + ball.height > playerController.rect.y + playerController.rect.height) {
             playerController.moveDown(dt);
         }
     }
}
