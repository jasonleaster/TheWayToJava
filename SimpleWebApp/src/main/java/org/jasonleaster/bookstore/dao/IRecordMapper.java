package org.jasonleaster.bookstore.dao;

import org.jasonleaster.bookstore.model.Record;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("recordMapper")
public interface IRecordMapper {

    int insert(Record record);

    int deleteByPrimaryKey(Integer id);

    /**
     * Upper Layyer will never use these method.
     *
     * int updateByPrimaryKeySelective(Record record);
     *
     *  Unimplemented method
     *
     *  int deleteFuzzy(Map map);
     */

    Record selectByPrimaryKey(Integer id);

    List<Record> selectFuzzy(Map map);

    int selectItemCount(Map map);

    int countAll();
}