package com.naraci.app.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naraci.app.media.domain.DouyinCount;
import org.apache.ibatis.annotations.Select;

/**
* @author Zhaoyu
* @description 针对表【douyin_count】的数据库操作Mapper
* @createDate 2024-03-02 23:32:36
* @Entity com.naraci.app.domain.DouyinCount
*/
public interface DouyinCountMapper extends BaseMapper<DouyinCount> {

    @Select("select * from douyin_count where user_id = #{id} and is_deleted = 0")
    DouyinCount selectByUserId(String id);
}




