package me.nikox.zwierzoinator.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Message;

import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Entry {

    private int entryId;
    private long punishedId;
    private long executorId;
    private long punishedAt;
    private long punishedTo;
    private boolean isActive;
    private boolean isAccepted;
    private String reason;
    private Message entryMessage;

}
