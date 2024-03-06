package com.naraci.app.media.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.naraci.app.BaseServer.domain.ImageAcg;
import com.naraci.app.BaseServer.mapper.ImageAcgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ShenZhaoYu
 * @date 2024/3/6
 */
@Service
public class RandomService {

    @Autowired
    private ImageAcgMapper imageAcgMapper;

    public List<String> acgImage(Integer number) {

        if (ObjectUtils.isEmpty(number) || number <= 0) {
            number = 1;
        } else if (number > 10) {
            number = 10;
        }
        List<String> urlList = imageAcgMapper.listRandomUrl(number)
                .stream().map(ImageAcg::getUrl)
                .toList();
        return urlList;
    }
}
