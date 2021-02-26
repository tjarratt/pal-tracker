package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {
    private final String port;
    private final String memoryLimit;
    private final String instanceIndex;
    private final String instanceAddress;

    public EnvController(@Value("${port:NOT SET}") String port,
                         @Value("${memory.limit:NOT SET}") String memoryLimit,
                         @Value("${cf.instance.index:NOT SET}") String instanceIndex,
                         @Value("${cf.instance.addr:NOT SET}") String instanceAddress) {
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.instanceIndex = instanceIndex;
        this.instanceAddress = instanceAddress;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map<String, String> result = new HashMap<>();
        result.put("PORT", port);
        result.put("MEMORY_LIMIT", memoryLimit);
        result.put("CF_INSTANCE_INDEX", instanceIndex);
        result.put("CF_INSTANCE_ADDR", instanceAddress);

        return result;
    }
}
