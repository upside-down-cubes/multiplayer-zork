package io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WhoamiDTO {

    private boolean loggedIn;

    private String username;

}
