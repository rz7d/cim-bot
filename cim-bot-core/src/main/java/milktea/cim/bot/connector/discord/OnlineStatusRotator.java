package milktea.cim.bot.connector.discord;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;

import com.mewna.catnip.Catnip;
import com.mewna.catnip.entity.user.Presence;
import com.mewna.catnip.entity.user.Presence.OnlineStatus;

public class OnlineStatusRotator extends TimerTask {

    private static final List<OnlineStatus> STATUSES = List.of(
            OnlineStatus.ONLINE,
            OnlineStatus.IDLE,
            OnlineStatus.DND);

    private final Catnip discord;
    private final Duration delay;
    private final Duration period;

    private final Timer timer = new Timer();
    private final Iterator<OnlineStatus> statusIterator = IntStream
            .iterate(0, i -> i + 1 % STATUSES.size())
            .mapToObj(STATUSES::get).iterator();

    public OnlineStatusRotator(Catnip discord, Duration delay, Duration period) {
        this.discord = Objects.requireNonNull(discord);
        this.delay = Objects.requireNonNull(delay);
        this.period = Objects.requireNonNull(period);
    }

    public Catnip getDiscord() {
        return discord;
    }

    @Override
    public void run() {
        discord.presence(Presence.of(statusIterator.next()));
    }

    public void start() {
        timer.schedule(this, delay.toMillis(), period.toMillis());
    }

}
