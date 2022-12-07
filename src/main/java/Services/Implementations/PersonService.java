package Services.Implementations;

import DAL.Enums.StatusCode;
import DAL.Interfaces.IPersonRepository;
import DAL.Models.Person;
import Helper.Implementation.BaseResponse;
import Services.Interfaces.Repositories.IPersonService;

import java.util.List;
import java.util.Optional;

public abstract class PersonService implements IPersonService {
    protected IPersonRepository _personRepository;

    public PersonService(IPersonRepository _personRepository) {
        this._personRepository = _personRepository;
    }

    @Override
    public BaseResponse<Optional<Person>> getById(int id) {
        try {
            Optional<Person> person = _personRepository.getById(id);
            if (person.isPresent()) {
                return new BaseResponse<>(person,"Successfully processed",StatusCode.success);
            }

            return new BaseResponse<>(Optional.empty(),"There is none with this Id",
                    StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> create(Person entity) {
        try {
            boolean created = _personRepository.create(entity);
            if (created) {
                return new BaseResponse<>(true,"Successfully processed", StatusCode.success);
            }

            return new BaseResponse<>(false,
                    "The person has not been created",
                    StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Optional<List<Person>>> getAll() {
        try {
            Optional<List<Person>> people = _personRepository.getAll();
            if (people.isPresent()) {
                return new BaseResponse<>(people,
                        "Successfully processed",
                        StatusCode.success);
            }

            return new BaseResponse<>(Optional.empty(),
                    "There is none",
                    StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> deleteById(int id) {
        try {
            boolean deleted = _personRepository.deleteById(id);
            if (deleted) {
                return new BaseResponse<>(true,
                        "Successfully processed",
                        StatusCode.success);
            }

            return new BaseResponse<>(false,
                    "The person has not been deleted",
                    StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> update(Person entity) {
        try {
            boolean updated = _personRepository.update(entity);
            if (updated) {
                return new BaseResponse<>(true,
                        "Successfully processed",
                        StatusCode.success);
            }

            return new BaseResponse<>(false,
                    "The person has not been updated",
                    StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Optional<List<Person>>> getByName(String name) {
        try {
            Optional<List<Person>> people = _personRepository.getByName(name);
            if (people.isPresent()) {
                return new BaseResponse<>(people,
                        "Successfully processed",
                        StatusCode.success);
            }

            return new BaseResponse<>(Optional.empty(),
                    "There is none with this name",
                    StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> changeBankBalance(int idPerson, double amount) {
        try {
            boolean changed = _personRepository.changeBankBalance(idPerson, amount);
            if (changed) {
                return new BaseResponse<>(true, "Successfully processed", StatusCode.success);
            }

            return new BaseResponse<>(false, "There is none with this Id", StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }
}
