package site.mufen.api;

import site.mufen.api.response.Response;

/**
 * @author mufen
 * @Description
 * @create 2025/1/10 0:45
 */
public interface IDCCService {

    Response<Boolean> updateConfig(String key, String value);
}
