import bagel.Image;

public class SteelPipe extends Pipe {
    private final Image pipe1 = new Image("res/level-1/steelPipe.png");
    private final Image pipe2 = new Image("res/level-1/steelPipe.png");
    private final Image flame1 = new Image("res/level-1/flame.png");
    private final Image flame2 = new Image("res/level-1/flame.png");
    private int frame = 0;


    /**
     * this the constructor of steel pipe
     */
    public SteelPipe() {
        super();
    }

    /**
     * update the position of steel pipe and shoot fire
     */
    @Override
    public void update() {
        flame = 0;
        xPipe -= this.PIPE_V ;   //pipe move from right to left at constant speed.
        pipe2.draw(xPipe, yPipe2, this.Rotations.setRotation(Math.PI));
        pipe1.draw(xPipe, yPipe1);

        //shoot flame every 50 frame and stay 5 frame
        if (frame >= 50) {
            flame = 1;
            flame1.draw(xPipe, yPipe1 + 39 / 2 + PIPELENGTH / 2);
            flame2.draw(xPipe, yPipe2 - 39 / 2 - PIPELENGTH / 2, this.Rotations.setRotation(Math.PI));
            if (frame == 60) {
                frame = 0;
                flame = 0;
            }
        }
        frame++;
    }

    /**
     * check if the steel pipe can be destroyed by weapon
     * since it is steel pipe, it can only be destroyed by bomb
     * @param w this is the weapon that use to destroy pipe
     */
    public void destroyCheck(Weapon w) {
        //check if the weapon hit the pipe. since it is a steel pipe, it only works for bomb
        if ((w.weaponX >= getxPipe() && w.weaponX <= getxPipe() + PIPEWIDTH / 2) &&
                (w.weaponY <= getyPipe1() + getPIPELENGTH() / 2 || w.weaponY >= getyPipe2() - getPIPELENGTH() / 2)) {
            //if the bomb, destroyed
            if (w.getClass() == Bomb.class) {
                destroied = true;
                w.outOfRange = true;
            //if the rock, not destroyed, weapon disappear
            } else if (w.getClass() == Rock.class) {
                w.outOfRange = true;
            }
        }
    }
}
