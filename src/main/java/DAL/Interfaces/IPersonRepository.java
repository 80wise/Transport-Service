package DAL.Interfaces;

import DAL.Models.Person;

public interface IPersonRepository extends IBaseRepositoryDA<Person>, IName<Person> {
    boolean changeBankBalance(int id, double amount) throws Exception;
}
