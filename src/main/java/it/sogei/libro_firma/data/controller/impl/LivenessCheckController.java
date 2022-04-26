package it.sogei.libro_firma.data.controller.impl;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "live")
public class LivenessCheckController {
	
	@ReadOperation
	public Health health() {
		return Health.up().build();
	}

}
