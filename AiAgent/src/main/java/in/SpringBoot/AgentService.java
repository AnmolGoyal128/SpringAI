package in.SpringBoot;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    public static ResponseEntity<String> agent(String message) {
        return ResponseEntity.ok(message);
    }
}
