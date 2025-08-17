package arc.haldun.commandline;

import arc.haldun.logger.LogLevel;
import arc.haldun.logger.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineListener {

    private static boolean instantiated = false;

    private InputStream stream;
    private String inputPrefix;

    private List<Command> commands;

    private State state;

    private OnUnknownCommandListener onUnknownCommand;

    public CommandLineListener() {

        if (instantiated) {
            Logger.log("Cannot exist more than one Command Line Listener.", LogLevel.WARNING);
            return;
        }

        this.stream = System.in;
        this.inputPrefix = ">";

        this.commands = new ArrayList<>();

        state = State.RELEASED;

        instantiated = true;
    }

    public void blockListener() {
        state = State.BLOCKED;
    }

    public void releaseListener() {
        state = State.RELEASED;
    }

    private void cycle() {

        String input;
        Scanner scanner = new Scanner(stream);

        while (true) {

            if (state == State.BLOCKED) continue;

            System.out.print(inputPrefix + " ");
            input = scanner.nextLine();
            input = input.trim();

            List<String> commandArgs = new ArrayList<>(List.of(input.split(" ")));
            String action = commandArgs.getFirst();

            commandArgs.removeFirst();

            boolean performed = false;
            for (Command command : commands) {

                if (action.equals(command.name())) {

                    command.action(commandArgs.toArray(new String[0]));
                    performed = true;
                }
            }

            if (!performed) {
                if (onUnknownCommand != null) onUnknownCommand.onUnknownCommand(action);
            }
        }
    }

    public void setOnUnknownCommandListener(OnUnknownCommandListener onUnknownCommandListener) {
        onUnknownCommand = onUnknownCommandListener;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void start() {
        Thread listenerThread = new Thread(this::cycle);
        listenerThread.setName("Command-Line-Listener");
        listenerThread.start();
    }

    private enum State {
        BLOCKED,
        RELEASED
    }
}
