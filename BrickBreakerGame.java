

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

interface Drawable {
    // Interface definition for objects that can be drawn
    void draw(Graphics g);
    // Method signature for the draw method which takes a Graphics object as an argument
}

enum Direction {
    // Enumeration for the direction the ball is moving
    LEFT, RIGHT
    // Two values representing the possible directions
}

class Ball implements Drawable {
    // Class definition for the Ball object which implements the Drawable interface
    private int x, y;
    // Private instance variables for the x and y coordinates of the ball
    private int size;
    // Private instance variable for the size of the ball
    private Direction direction;
    // Private instance variable for the direction the ball is moving

    Ball(int x, int y, int size) {
        // Constructor for the Ball object
        this.x = x;
        // Initialize the x instance variable with the argument passed in
        this.y = y;
        // Initialize the y instance variable with the argument passed in
        this.size = size;
        // Initialize the size instance variable with the argument passed in
        direction = Direction.LEFT;
        // Initialize the direction instance variable to LEFT by default
    }

    public void move() {
        // Method for moving the ball
        if (direction == Direction.LEFT) {
            // If the ball is moving LEFT
            x--;
            // Decrement the x coordinate by 1
        } else {
            // If the ball is moving RIGHT
            x++;
            // Increment the x coordinate by 1
        }
    }

    @Override
    public void draw(Graphics g) {
        // Overrides the draw method from the Drawable interface
        g.fillOval(x, y, size, size);
        // Fill an oval shape with the current x, y, and size variables using the Graphics object passed in as an argument
    }

    public Rectangle getBounds() {
        // Method to get the bounds of the ball as a rectangle
        return new Rectangle(x, y, size, size);
        // Return a new rectangle object with the current x, y, and size values
    }

    public void changeDirection() {
        // Method to change the direction of the ball
        if (direction == Direction.LEFT) {
            // If the ball is currently moving LEFT
            direction = Direction.RIGHT;
            // Change the direction to RIGHT
        } else {
            // If the ball is currently moving RIGHT
            direction = Direction.LEFT;
            // Change the direction to LEFT
        }
    }
}
//This is the declaration of the abstract class GameObject, which implements the Drawable interface.
abstract class GameObject implements Drawable {
 // These are the instance variables for the class.
 int x, y;
 Color color;

 // This is the constructor for the class, which initializes the instance variables.
 GameObject(int x, int y, Color color) {
     this.x = x;
     this.y = y;
     this.color = color;
 }
}

//This is the declaration of the Brick class, which extends the GameObject class.
class Brick extends GameObject {
 // These are the instance variables for the class.
 private int width, height;

 // This is the constructor for the class, which initializes the instance variables and calls the super constructor.
 Brick(int x, int y, int width, int height, Color color) {
     super(x, y, color);
     this.width = width;
     this.height = height;
 }

 // This is the implementation of the draw method from the Drawable interface.
 @Override
 public void draw(Graphics g) {
     // This sets the color for the graphics object.
     g.setColor(color);
     // This fills a rectangle using the specified color.
     g.fillRect(x, y, width, height);
 }

 // This is a method to get the bounds of the rectangle.
 public Rectangle getBounds() {
     // This returns a new Rectangle object with the specified x, y, width, and height.
     return new Rectangle(x, y, width, height);
 }
}

//This is the declaration of the Paddle class, which extends the GameObject class.
class Paddle extends GameObject {
 // These are the instance variables for the class.
 private int width, height;

 // This is the constructor for the class, which initializes the instance variables and calls the super constructor.
 Paddle(int x, int y, int width, int height, Color color) {
     super(x, y, color);
     this.width = width;
     this.height = height;
 }

 // This is the implementation of the draw method from the Drawable interface.
 @Override
 public void draw(Graphics g) {
     // This sets the color for the graphics object.
     g.setColor(color);
     // This fills a rectangle using the specified color.
     g.fillRect(x, y, width, height);
 }

 // This is a method to get the bounds of the rectangle.
 public Rectangle getBounds() {
     // This returns a new Rectangle object with the specified x, y, width, and height.
     return new Rectangle(x, y, width, height);
 }
}
//Class definition for Board which extends JPanel and implements the Drawable interface
class Board extends JPanel implements Drawable {
 // Declaring an ArrayList of type Brick to store the bricks in the game
 private ArrayList<Brick> bricks;
 // Declaring a Ball object to represent the ball in the game
 private Ball ball;
 // Declaring a Paddle object to represent the paddle in the game
 private Paddle paddle;

 // Constructor to initialize the bricks, ball and paddle
 Board() {
     // Initializing the ArrayList of bricks
     bricks = new ArrayList<>();
     // Adding bricks to the ArrayList
     for (int i = 0; i < 5; i++) {
         for (int j = 0;j < 10; j++) {
             // Adding a new brick to the ArrayList at (j * 60 + 30, i * 25 + 50) with width 50, height 20, and color RED
             bricks.add(new Brick(j * 60 + 30, i * 25 + 50, 50, 20, Color.RED));
         }
     }
     // Initializing the ball object at position (200, 300) with a diameter of 20
     ball = new Ball(200, 300, 20);
     // Initializing the paddle object at position (175, 330) with width 50, height 10, and color BLUE
     paddle = new Paddle(175, 330, 50, 10, Color.BLUE);
 }

 // Overriding the paintComponent method from JPanel
 @Override
 public void paintComponent(Graphics g) {
     // Calling the super class's paintComponent method
     super.paintComponent(g);
     // Iterating through the bricks and calling their draw method
     for (Brick brick : bricks) {
         brick.draw(g);
     }
     // Calling the draw method of the ball
     ball.draw(g);
     // Calling the draw method of the paddle
     paddle.draw(g);
 }

 // Overriding the draw method from the Drawable interface
 @Override
 public void draw(Graphics g) {
     // Calling the paintComponent method
     paintComponent(g);
 }

 // Method to move the ball and check for collisions
 public void move() {
     // Calling the move method of the ball
     ball.move();
     // Calling the checkCollisions method to check for collisions
     checkCollisions();
     // Calling the repaint method to repaint the Board
     repaint();
 }

 // Method to check for collisions between the ball and the paddle, or between the ball and the bricks
 private void checkCollisions() {
     // Getting the bounds of the ball as a Rectangle
     Rectangle ballBounds = ball.getBounds();
     // Getting the bounds of the paddle as a Rectangle
     Rectangle paddleBounds = paddle.getBounds();
     // Checking if the ball intersects with the paddle
     if (ballBounds.intersects(paddleBounds)) {
         // If it does, change the direction of the ball
         ball.changeDirection();
     }
     // Iterating through the bricks
     for (int i = 0; i < bricks.size(); i++) {
         // Getting the current brick
         Brick brick = bricks.get(i);
         // Getting the bounds of
      // Create a rectangle object for the current brick
         Rectangle brickBounds = brick.getBounds();

         // Check if the ball intersects with the current brick
         if (ballBounds.intersects(brickBounds)) {
         // Remove the current brick from the list of bricks
         bricks.remove(brick);
         // Change the direction of the ball
         ball.changeDirection();
         }
     }
     }
 }

//Main class of the Brick Breaker game
         public class BrickBreakerGame {
         // Main method to run the game
         public static void main(String[] args) {
         // Create JFrame for the game window
         JFrame frame = new JFrame("Brick Breaker");
         // Set the close operation to exit the game when the window is closed
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         // Set the size of the game window
         frame.setSize(400, 400);
         // Disable resizing of the game window
         frame.setResizable(false);
         // Create a Board object to manage the game logic and rendering
         Board board = new Board();
         // Add the Board to the JFrame
         frame.add(board);
         // Make the JFrame visible to the user
         frame.setVisible(true);
         // Game loop to continuously update the game state and render it
         while (true) {
         // Call the move method of the Board to update the game state
         board.move();
         // Sleep the game loop for 5 milliseconds to control the game speed
         try {
         Thread.sleep(5);
         } catch (InterruptedException e) {
         // Print the error message in case of an InterruptedException
         e.printStackTrace();
         }
         }
         }
         }




