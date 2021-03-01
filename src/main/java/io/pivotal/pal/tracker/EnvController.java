package io.pivotal.pal.tracker;

import java.util.HashMap;
import java.util.Map;

public class EnvController {
    private final String port;
    private final String memory;
    private final String instanceIndex;
    private final String instanceAddress;

    public EnvController(String port, String memory, String instanceIndex, String instanceAddress) {
        this.port = port;
        this.memory = memory;
        this.instanceIndex = instanceIndex;
        this.instanceAddress = instanceAddress;
    }

    public Map<String, String> getEnv() {
        Map<String, String> result = new HashMap<>();

        result.put("PORT", port);
        result.put("MEMORY_LIMIT", memory);
        result.put("CF_INSTANCE_INDEX", instanceIndex);
        result.put("CF_INSTANCE_ADDR", instanceAddress);

        return result;
    }
}
