package com.wells.speech.models;

import com.yugabyte.data.jdbc.repository.YsqlRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordingRepository extends YsqlRepository<Recording, Integer> {

    Recording save(Recording recording) throws DataAccessException;

    @Query("select * from recording where name = :name")
    List<Recording> findByName(@Param("name") String name);

    @Query("select * from recording where username = :userName")
    Recording findByUserName(@Param("userName") String userName);
}
