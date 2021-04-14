package com.gls.demo.consumer.web.service.impl;

import com.gls.demo.consumer.config.FeignConfig;
import com.gls.demo.consumer.web.service.RibbonDemoService;
import com.gls.demo.provider.api.model.DemoModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author george
 */
@Service(value = "ribbonDemoService")
public class RibbonDemoServiceImpl implements RibbonDemoService {

    private static final String URL = "http://" + FeignConfig.PROVIDER_APPLICATION_NAME + "/provider/demo";

    @Resource
    private RestTemplate restTemplate;

    @Override
    public List<DemoModel> query(DemoModel model) {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(URL, DemoModel[].class, model).getBody()));
    }

    @Override
    public DemoModel getInfo(Integer id) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(URL + "/{id:\\d+}").build().expand(id).encode();
        URI uri = uriComponents.toUri();
        return restTemplate.getForEntity(uri, DemoModel.class).getBody();
    }

    @Override
    public DemoModel create(DemoModel model) {
        return restTemplate.postForEntity(URL, model, DemoModel.class).getBody();
    }

    @Override
    public DemoModel update(DemoModel model) {
        restTemplate.put(URL, model);
        return model;
    }

    @Override
    public void delete(Integer id) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(URL + "/{id:\\d+}").build().expand(id).encode();
        URI uri = uriComponents.toUri();
        restTemplate.delete(uri);
    }
}
