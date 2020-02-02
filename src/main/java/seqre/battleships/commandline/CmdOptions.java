package seqre.battleships.commandline;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;


public class CmdOptions extends OptionsBase {

    @Option(
            name = "mode",
            abbrev = 'm',
            help = "The instance type: server|client",
            defaultValue = "client"
    )
    public String mode;

    @Option(
            name = "port",
            abbrev = 'p',
            help = "The game port",
            defaultValue = "2137"
    )
    public int port;

    @Option(
            name = "map",
            abbrev = 'm',
            help = "The game map",
            defaultValue = ""
    )
    public String map;
}