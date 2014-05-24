package hydra;

import com.google.common.base.Joiner;
import com.google.inject.Singleton;


@Singleton
public class App {
    public void start(String[] args) {
        System.out.println(Joiner.on(", ").join("Hello", "World"));
    }
}
