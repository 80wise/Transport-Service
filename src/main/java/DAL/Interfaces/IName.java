package DAL.Interfaces;

import java.util.List;
import java.util.Optional;

public interface IName <T>{
    Optional<List<T>> getByName(String name) throws Exception;
}
