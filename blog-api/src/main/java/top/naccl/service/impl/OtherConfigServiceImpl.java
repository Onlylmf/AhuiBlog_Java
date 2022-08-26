package top.naccl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.naccl.mapper.OtherConfigMapper;
import top.naccl.model.dto.OtherConfig;
import top.naccl.service.OtherConfigService;

@Service
public class OtherConfigServiceImpl implements OtherConfigService {

    @Autowired
    private OtherConfigMapper otherConfigMapper;

    @Override
    public OtherConfig selectWeChatConfig(String configCode) {
        return otherConfigMapper.selectWeChatConfig(configCode);
    }

    @Override
    public int updateWeChatConfig(String configCode, String configJson) {
        return otherConfigMapper.updateWeChatConfig(configCode,configJson);
    }
}
