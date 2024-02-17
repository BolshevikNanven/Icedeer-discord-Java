package org.discord.common.impl;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.hy.corecode.idgen.WFGIdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CustomIdGenerator implements IdentifierGenerator {
    @Resource
    private WFGIdGenerator wfgIdGenerator;
    @Override
    public Number nextId(Object entity) {
        return wfgIdGenerator.next();
    }
}
