package com.slx.zsxt.dao;

import com.slx.zsxt.model.accountType;

public interface accountTypeMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(accountType record);

    int insertSelective(accountType record);

    accountType selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(accountType record);

    int updateByPrimaryKey(accountType record);
}