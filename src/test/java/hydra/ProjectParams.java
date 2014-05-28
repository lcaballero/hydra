package hydra;

public class ProjectParams {
    private String name;
    private String namespace;


    public String getName() {
        return name;
    }

    public void setName(String projectName) {
        this.name = projectName;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}