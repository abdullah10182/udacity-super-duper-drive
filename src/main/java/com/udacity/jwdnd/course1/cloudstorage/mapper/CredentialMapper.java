package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId}")
    List<Credential> getCredentials(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url , username, key, password, userid ) VALUES ( #{url}, #{userName},#{key}, #{password},  #{userId} ) ")
    @Options(useGeneratedKeys = true, keyColumn = "credentialid", keyProperty = "credentialId")
    Integer createCredential(Credential credential);


    @Insert("UPDATE CREDENTIALS SET username= #{userName}, password=#{password}, key= #{key}, url=#{url} WHERE credentialid=#{credentialId}")
    void editCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialId}")
    void deleteCredential(Integer credentialId);
}
