package java.entities;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    private final List<String> name;

    public Artist(List<String> name) {
        this.name = name;
    }
    public List<String> getName() {
        return name;
    }
}
