package hu.nye.szakdolgozat.data.repository;

import hu.nye.szakdolgozat.data.model.Player;

import java.util.*;

@org.springframework.stereotype.Repository
public class InMemoryRepository implements Repository<Player, UUID>{

    private static final Map<UUID, Player> STORAGE = new HashMap<>();
    @Override
    public Player save(Player item) {
        UUID id = UUID.randomUUID();
        item.setId(id);
        STORAGE.put(id, item);
        return STORAGE.get(id);
    }

    @Override
    public Player getById(UUID id) {
        return STORAGE.get(id);
    }

    @Override
    public List<Player> getAll() {
        return STORAGE.values().stream().toList();
    }

    @Override
    public Player update(Player item) {
        UUID id = item.getId();
        STORAGE.put(id, item);
        return STORAGE.get(id);
    }

    @Override
    public void deleteById(UUID id) {
        STORAGE.remove(id);
    }
}
