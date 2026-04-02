package hu.mol.MeetingRoomManagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Meeting Room Booking API",
                version = "v1",
                description = "REST API for meeting rooms, reservations, and availability checks"
        )
)
public class OpenApiConfig {
}
