package com.cpf.xunwu.base;

import lombok.*;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceMultiResult<T> implements Serializable {
    private List<T> result;
    private Integer total;

    public int getResultSize(){
        if (CollectionUtils.isEmpty(result)){
            return 0;
        }
        return result.size();
    }

}
