import bagel.Image;

public class Rock extends Weapon {
    private final Image rock = new Image("res/level-1/rock.png");
    private final int range = 100;
    public Rock() {
        super();
    }

    @Override
    /**
     * draw rock and update its position and state
     */
    public void update() {
        rock.draw(weaponX, weaponY);
        super.update();

    }

    /**
     * check if the rock move out of the range
     * @return boolean if rock is out of range, set it to false and return it.
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
