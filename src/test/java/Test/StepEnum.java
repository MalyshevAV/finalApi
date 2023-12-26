package Test;

public enum StepEnum {
ONE(5), TWO(200);
private final int step;

    public int getStep() {
        return step;
    }

    StepEnum(int step) {
        this.step = step;
    }
}
