package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class PalTrackerCustomInfoMetric implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("hello", "world").build();
    }
}
