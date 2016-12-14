package org.codingpedia.demo.rmqc.core.product.dao;

import org.codingpedia.demo.rmqc.core.product.bean.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}