package site.mufen.trigger.http;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.springframework.web.bind.annotation.*;
import site.mufen.api.IDCCService;
import site.mufen.api.response.Response;
import site.mufen.types.enums.ResponseCode;

import javax.annotation.Resource;

/**
 * @author mufen
 * @Description
 * @create 2025/1/10 0:46
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/gbm/dcc/")
public class DCCController implements IDCCService {

    @Resource
    private RTopic dccTopic;


    /**
     * 动态值变更
     * <p>
     * curl http://127.0.0.1:8091/api/v1/gbm/dcc/update_config?key=downgradeSwitch&value=1
     * curl http://127.0.0.1:8091/api/v1/gbm/dcc/update_config?key=cutRange&value=0
     */
    @GetMapping("update_config")
    @Override
    public Response<Boolean> updateConfig(@RequestParam String key, @RequestParam String value) {
        try {
            dccTopic.publish(key + "," + value);

            return Response.<Boolean>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
        } catch (Exception e) {
            return Response.<Boolean>builder()
                .code(ResponseCode.UN_ERROR.getCode())
                .info(ResponseCode.UN_ERROR.getInfo())
                .build();
        }
    }
}
