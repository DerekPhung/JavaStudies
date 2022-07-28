import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Lifter {

    private String name;
    private int squat;
    private int bench;
    private int deadlift;

    public Lifter(String name){
        this.name = name;
        this.squat = 0;
        this.bench = 0;
        this.deadlift = 0;
    }

    public Lifter(String name, int squat, int bench, int deadlift) {
        this.name = name;
        this.squat = squat;
        this.bench = bench;
        this.deadlift = deadlift;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSquat() {
        return squat;
    }

    public void setSquat(int squat) {
        this.squat = squat;
    }

    public int getBench() {
        return bench;
    }

    public void setBench(int bench) {
        this.bench = bench;
    }

    public int getDeadlift() {
        return deadlift;
    }

    public void setDeadlift(int deadlift) {
        this.deadlift = deadlift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lifter lifter = (Lifter) o;
        return name.equals(lifter.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%s=%s,%s,%s",name,squat,bench,deadlift);
    }

}
