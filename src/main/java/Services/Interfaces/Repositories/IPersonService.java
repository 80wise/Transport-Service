package Services.Interfaces.Repositories;

import DAL.Models.Person;
import Helper.Implementation.BaseResponse;
import Services.IBaseService;

public interface IPersonService extends IBaseService<Person>, INameService<Person> {
    BaseResponse<Boolean> changeBankBalance(int idPerson, double amount);
}
