import bagel.Image;

public class Bomb extends Weapon {
    private final Image bomb = new Image("res/level-1/bomb.png");
    private final int range = 200;
    public Bomb() {
        super();
    }

    /**
     * draw bomb and update its position and state
     */
    @Override
    public void update() {
        bomb.draw(weaponX, weaponY);
        super.update();
    }

    /**
     * check if the bomb move out of range
     * @return boolean if bomb is out of range, set it to false and return it.
     */
    public boolean rangeCheck() {
        if (shoot) {
            if (count==range/5) {
                return outOfRange = true;
            }
        }
        return outOfRange = false;
    }
}
