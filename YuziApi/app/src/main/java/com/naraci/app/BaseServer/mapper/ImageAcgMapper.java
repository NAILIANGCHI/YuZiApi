package com.naraci.app.BaseServer.mapper;

import com.naraci.app.BaseServer.domain.ImageAcg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Zhaoyu
* @description 针对表【image_acg】的数据库操作Mapper
* @createDate 2024-03-05 01:31:57
* @Entity com.naraci.app.BaseServer.domain.ImageAcg
*/
public interface ImageAcgMapper extends BaseMapper<ImageAcg> {

    void deletedByUrl(String url);

    @Select("SELECT url FROM image_acg ORDER BY RAND() LIMIT #{number};")
    List<ImageAcg> listRandomUrl(Integer number);
}




