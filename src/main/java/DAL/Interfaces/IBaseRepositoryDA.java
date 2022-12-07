package DAL.Interfaces;

import java.util.List;
import java.util.Optional;

public interface IBaseRepositoryDA<T> {
    Optional<T> getById(int id) throws Exception;
    boolean create(T entity) throws Exception;
    Optional<List<T>> getAll() throws Exception;
    boolean deleteById(int id) throws Exception;
    boolean update(T entity) throws Exception;
}
