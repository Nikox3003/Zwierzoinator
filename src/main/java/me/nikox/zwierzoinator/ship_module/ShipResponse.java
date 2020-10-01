package me.nikox.zwierzoinator.ship_module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipResponse {

    private ShipResponseType responseType;
    private String response;
    private ShipRequirement shipRequirement;

}
