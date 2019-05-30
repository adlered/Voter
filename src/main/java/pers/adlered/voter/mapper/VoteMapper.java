package pers.adlered.voter.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.adlered.voter.dao.Vote;

@Mapper
public interface VoteMapper {
    @Select("SELECT * FROM Voter_Vote where VID = #{VID}")
    Vote getVote(@Param("VID") Integer VID);
}
