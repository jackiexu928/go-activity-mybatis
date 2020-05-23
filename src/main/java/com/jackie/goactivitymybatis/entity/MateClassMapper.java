package com.jackie.goactivitymybatis.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-23
 */
@Data
public class MateClassMapper {
    private Long id;
    private Long schoolId;
    private Long classId;
    private Long mateId;
    private Integer mateType;
    private Integer validFlag;
}
