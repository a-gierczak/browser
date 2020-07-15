package agierczak.browser.core.layout;

public class Box {

  public float width;
  public float height;
  public float x;
  public float y;

  public Box() {
    width = 0;
    height = 0;
    x = 0;
    y = 0;
  }

  public Box(Box box) {
    x = box.x;
    y = box.y;
    width = box.width;
    height = box.height;
  }

  public Box shrink(float amount) {
    Box ret = new Box(this);
    ret.x += amount;
    ret.y += amount;
    ret.width = Math.max(0, ret.width - (amount * 2));
    ret.height = Math.max(0, ret.height - (amount * 2));
    return ret;
  }

  public Box expand(float amount) {
    return shrink(-amount);
  }

  public boolean contains(float targetX, float targetY) {
    return targetX >= x && targetX <= x + width && targetY >= y && targetY <= y + height;
  }

  @Override
  public String toString() {
    return "Box{" +
        "width=" + width +
        ", height=" + height +
        ", x=" + x +
        ", y=" + y +
        '}';
  }
}
