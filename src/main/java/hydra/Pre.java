package hydra;

import java.util.Map;
import java.util.Objects;


public class Pre {

    protected Map<String,Object> map = null;

    public Pre(Map<String,Object> map) {
        this.map = map;
    }

    public Object get(String key) {
        return map.get(key);
    }

    public Pre map(String key) {
        Map<String,Object> m = (Map<String,Object>)map.get(key);
        return new Pre(m);
    }
}
