package dev.aleesk.arsenal.models.kit;

import dev.aleesk.arsenal.utilities.TimeUtil;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
public class KitCooldown {

    private final String name;
    private LocalDateTime kitNextClaimDate;

    public KitCooldown(String name) {
        this.name = name;
    }

    public boolean isExpired() {
        return kitNextClaimDate != null && kitNextClaimDate.isBefore(LocalDateTime.now());
    }

    public void setKitNextClaimDate(long time) {
        this.kitNextClaimDate = LocalDateTime.now().plusSeconds(time);
    }

    public void setKitNextClaimDate(String time) {
        this.kitNextClaimDate = LocalDateTime.parse(time, TimeUtil.DATE_TIME_FORMATTER);
    }

    public String getKitNextClaimDateFormatted() {
        return kitNextClaimDate.format(TimeUtil.DATE_TIME_FORMATTER);
    }

    public String getCooldownRemaining() {
        return TimeUtil.getTimeFormatted(Duration.between(LocalDateTime.now(), kitNextClaimDate));
    }
}
