package pers.adlered.voter.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pers.adlered.voter.dao.Vote;

@Mapper
public interface VoteMapper {
    @Select("SELECT * FROM Voter_Vote where VID = #{VID}")
    Vote getVote(@Param("VID") Integer VID);

    @Select("SELECT VID FROM Voter_Vote where VID = #{VID}")
    Vote checkVote(@Param("VID") Integer VID);

    @Update("UPDATE Voter_Vote SET Selection = #{Selection} WHERE VID = #{VID}")
    int vote(@Param("Selection") String selection, @Param("VID") Integer VID);
}
