package arc.haldun.commandline;

public abstract class Command {

    private String name;

    @SuppressWarnings("all")
    private String[] args;

    public abstract void action(String[] args);

    public abstract String name();

    public final void setArgs(String[] args) {
        this.args = args;
    }
}
