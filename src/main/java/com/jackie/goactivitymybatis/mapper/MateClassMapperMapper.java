package com.jackie.goactivitymybatis.mapper;

import com.jackie.goactivitymybatis.entity.MateClassMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.mybatis.spring.annotation.MapperScan;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-23
 */
@Mapper
public interface MateClassMapperMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into mate_class_mapper (school_id, class_id, mate_id, mate_type, valid_flag) " +
            "values (#{schoolId},#{classId},#{mateId},#{mateType},#{validFlag})")
    int insert(MateClassMapper mateClassMapper);
}
