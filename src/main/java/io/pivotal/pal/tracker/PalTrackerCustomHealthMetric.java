package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class PalTrackerCustomHealthMetric implements HealthIndicator {
    @Override
    public Health getHealth(boolean includeDetails) {
        return canned_health_response();
    }

    @Override
    public Health health() {
        return canned_health_response();
    }

    private Health canned_health_response() {
        if (false) {
            return Health
                    .down()
                    .withDetail("root cause", "just bad luck I guess")
                    .build();
        } else {
            return Health
                    .up()
                    .build();
        }
    }
}
