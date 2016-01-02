package net.mengkang.api.service;

import net.mengkang.api.route.ApiProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseService {

    protected ApiProtocol apiProtocol;
    protected Logger logger;

    public BaseService(ApiProtocol apiProtocol) {
        this.apiProtocol = apiProtocol;
        logger = LoggerFactory.getLogger(this.getClass());
    }
}
