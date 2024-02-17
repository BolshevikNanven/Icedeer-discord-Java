package org.discord.common.config;

import com.hy.corecode.idgen.WFGIdGenerator;
import com.hy.properties.IdGeneratorOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfig {
    @Bean
    public WFGIdGenerator wfgIdGenerator(){
        IdGeneratorOptions idGeneratorOptions=new IdGeneratorOptions();
        idGeneratorOptions.setWorkerId((short) 1);
        return new WFGIdGenerator(idGeneratorOptions);
    }
}
