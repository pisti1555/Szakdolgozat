package hu.nye.szakdolgozat.data.repository;

import java.util.List;

public interface PlayerRepository<T, I>{
    T save(T item);

    T getById(I id);

    List<T> getAll();

    T update(T item);

    void deleteById(I id);
}
