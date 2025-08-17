import arc.haldun.commandline.Command;
import arc.haldun.commandline.CommandLineListener;
import arc.haldun.logger.LogLevel;
import arc.haldun.logger.Logger;

public class Main {

    public static void main(String[] args) {

        CommandLineListener commandLineListener = new CommandLineListener();

        commandLineListener.addCommand(new Command() {
            @Override
            public void action(String[] args) {

                StringBuilder str = new StringBuilder();

                for (String s : args) {
                    str.append(s).append(", ");
                }


                Logger.log("Arguments: " + str, LogLevel.INFO);
            }

            @Override
            public String name() {
                return "args";
            }
        });



        commandLineListener.start();
    }
}