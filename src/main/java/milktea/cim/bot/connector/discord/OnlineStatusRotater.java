package milktea.cim.bot.connector.discord;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;

public class OnlineStatusRotater extends TimerTask {

  private static final List<OnlineStatus> STATUSES = List.of(
    OnlineStatus.ONLINE,
    OnlineStatus.IDLE,
    OnlineStatus.DO_NOT_DISTURB);

  private final JDA discord;
  private final Duration delay;
  private final Duration period;

  private final Timer timer = new Timer();
  private final Iterator<OnlineStatus> statusIterator = IntStream
    .iterate(0, i -> i + 1 % STATUSES.size())
    .mapToObj(STATUSES::get)
    .iterator();

  public OnlineStatusRotater(JDA discord, Duration delay, Duration period) {
    this.discord = Objects.requireNonNull(discord);
    this.delay = Objects.requireNonNull(delay);
    this.period = Objects.requireNonNull(period);
  }

  public JDA getDiscord() {
    return discord;
  }

  @Override
  public void run() {
    discord.getPresence().setStatus(statusIterator.next());
  }

  public void start() {
    timer.schedule(this, delay.toMillis(), period.toMillis());
  }

}
