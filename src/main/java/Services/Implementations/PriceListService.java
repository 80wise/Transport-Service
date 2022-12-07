package Services.Implementations;

import DAL.Enums.StatusCode;
import DAL.Interfaces.IPriceListRepository;
import DAL.Models.Price;
import Helper.Implementation.BaseResponse;
import Services.Interfaces.Repositories.IPriceListService;

import java.util.List;
import java.util.Optional;

public class PriceListService implements IPriceListService {

    private final IPriceListRepository _priceListRepository;

    public PriceListService(IPriceListRepository _priceListRepository){
        this._priceListRepository = _priceListRepository;
    }

    @Override
    public BaseResponse<Optional<Price>> getById(int id) {
        try{
            Optional<Price> price = _priceListRepository.getById(id);
            if(price.isPresent()){
                return new BaseResponse<>(price,
                        "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(Optional.empty(),
                    "There is no price with this id",
                    StatusCode.errorClient);
        }catch (Exception ex){
            return new BaseResponse<>(null,"Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> create(Price entity) {
        try{
            boolean created = _priceListRepository.create(entity);
            if(created){
                return new BaseResponse<>(true,
                        "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(false,
                    "The new price has not been added",
                    StatusCode.errorClient);
        }catch (Exception ex){
            return new BaseResponse<>(null,"Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Optional<List<Price>>> getAll() {
        try{
            Optional<List<Price>> priceList = _priceListRepository.getAll();
            if(priceList.isPresent()){
                return new BaseResponse<>(priceList,
                        "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(Optional.empty(),
                    "There is no price",
                    StatusCode.errorClient);
        }catch (Exception ex){
            return new BaseResponse<>(null,"Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> deleteById(int id) {
        try{
            boolean deleted = _priceListRepository.deleteById(id);
            if(deleted){
                return new BaseResponse<>(true,
                        "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(false,
                    "The price has not been deleted",
                    StatusCode.errorClient);
        }catch (Exception ex){
            return new BaseResponse<>(null,"Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> update(Price entity) {
        try{
            boolean updated = _priceListRepository.update(entity);
            if(updated){
                return new BaseResponse<>(true,
                        "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(false,
                    "The price has been updated",
                    StatusCode.errorClient);
        }catch (Exception ex){
            return new BaseResponse<>(null,"Server error", StatusCode.errorServer);
        }
    }
}
