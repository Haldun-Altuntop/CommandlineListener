# Simply Usage

```
CommandLineListener commandLineListener = new CommandLineListener();

        // Add command
        commandLineListener.addCommand(new Command() {
            @Override
            public void action(String[] args) {

                // Command actions here
            }

            @Override
            public String name() {
                return "command-name"; // Command name returned here
            }
        });

        commandLineListener.start(); // Start cycle for listening

```
