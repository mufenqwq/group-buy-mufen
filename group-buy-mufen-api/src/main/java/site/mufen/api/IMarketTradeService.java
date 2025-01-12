package site.mufen.api;

import site.mufen.api.dto.LockMarketPayOrderRequestDTO;
import site.mufen.api.dto.LockMarketPayOrderResponseDTO;
import site.mufen.api.response.Response;

/**
 * @author mufen
 * @Description
 * @create 2025/1/12 21:40
 */
public interface IMarketTradeService {

    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO requestDTO);
}
