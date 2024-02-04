package football.board.entity;

import football.board.exception.InvalidArgumentException;

import java.util.UUID;

public class Team {
    private final UUID uuid;
    private final String name;

    public Team(String name) {
        validateName(name);
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Team{");
        sb.append("uuid=").append(uuid);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidArgumentException("Team name cannot be null or empty");
        }
    }
}
