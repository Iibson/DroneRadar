package pl.edu.agh.DroneRadar.component;

public enum Direction {
    N("NORTH"),
    S("SOUTH"),
    W("WEST"),
    E("EAST");

    final String fullName;

    Direction(String fullName) {
        this.fullName = fullName;
    }

    public boolean isLatitudinal(){
        return this.equals(N) || this.equals(S);
    }

    public boolean isLongitudinal(){
        return this.equals(W) || this.equals(E);
    }
}
