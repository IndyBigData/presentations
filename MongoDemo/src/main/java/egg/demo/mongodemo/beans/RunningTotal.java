package egg.demo.mongodemo.beans;

public class RunningTotal {
  private String id;
  private long value;

  public String getId() {
    return id;
  }

  public float getValue() {
    return value;
  }

  public void setValue(long count) {
    this.value = count;
  }

  @Override
  public String toString() {
    return "RunningTotal [id=" + id + ", value=" + value + "]";
  }    
}
