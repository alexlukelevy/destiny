package commands.character;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CharacterOptions {

    public Options getOptions() {
        return new Options()
                .addOption(getUsernameOption())
                .addOption(getClassTypeOption())
                .addOption(getInstructionOption());
    }

    public Option getUsernameOption() {
        return Option.builder(CharacterOptionNames.Username.getShortOpt())
                .longOpt(CharacterOptionNames.Username.getLongOpt())
                .desc("the username of your character")
                .required()
                .argName("username")
                .hasArg()
                .numberOfArgs(1)
                .build();
    }

    public Option getClassTypeOption() {
        return Option.builder(CharacterOptionNames.ClassType.getShortOpt())
                .longOpt(CharacterOptionNames.ClassType.getLongOpt())
                .desc("the class of your character (hunter, titan or warlock)")
                .required()
                .argName("classtype")
                .hasArg()
                .numberOfArgs(1)
                .build();
    }
    // Why do you get the short option as the first param?
    // How does the command like ensure its got a valid instruction, should this be done in the part of code that is called?
    public Option getInstructionOption() {
        return Option.builder(CharacterOptionNames.Instruction.getShortOpt())
                .longOpt(CharacterOptionNames.Instruction.getLongOpt())
                .desc("the instruction for the program (getWeapons, getSummary)")
                .required()
                .argName("instruction")
                .hasArg()
                .numberOfArgs(1)
                .build();
    }
}