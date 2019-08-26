package milktea.cim.bot.command.help;

import milktea.cim.bot.event.MessageCommandEvent;
import milktea.cim.framework.command.Command;
import milktea.cim.framework.command.CommandBus;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

public final class HelpCommand {

    public HelpCommand() {
    }

    @Command(
        name = "help",
        description = "コマンドの一覧と使い方を表示します。",
        permission = "milktea.cim.bot.command.base.help"
    )
    public void execute(MessageCommandEvent event) {
        final var message = event.message();
        final var channel = message.channel();
        channel.sendMessage("ヘルプ");
        CompletableFuture.supplyAsync(() -> commands(event.commandBus())).thenCompose(channel::sendMessage);
    }

    private static String commands(CommandBus commandBus) {
        try {
            var commands = (Collection<?>) access(CommandBus.class, "commands").get(commandBus);

            var joiner = new StringJoiner("\n");
            joiner.add("Installed Commands (" + commands.size() + "): ");
            if (!commands.isEmpty()) {
                var first = commands.iterator().next();
                Class<?> descriptorClass = first.getClass();

                final var commandHandle = access(descriptorClass, "command");
                final var descriptionHandle = access(descriptorClass, "description");
                final var permissionHandle = access(descriptorClass, "permission");

                for (var command : commands) {
                    var name = (String) commandHandle.get(command);
                    var description = (String) descriptionHandle.get(command);
                    var permission = (String) permissionHandle.get(command);
                    joiner.add("**" + name + "** - " + description + " [*" + permission + "*]");
                }
            }
            return joiner.toString();
        } catch (Throwable exception) {
            if (exception instanceof Error)
                throw (Error) exception;
            return "```" + ExceptionUtils.getStackTrace(exception) + "```";
        }
    }

    private static Field access(Class<?> container, String name) throws NoSuchFieldException {
        Field field = container.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

}
