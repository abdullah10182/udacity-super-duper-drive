package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT fileId, fileName FROM FILES WHERE userid=#{userId}")
    List<File> getFiles(Integer userId);

    @Insert("INSERT INTO FILES (filename , filesize , filedata , contenttype , userid ) VALUES ( #{fileName}, #{fileSize}, #{fileData}, #{contentType}, #{userId} ) ")
    @Options(useGeneratedKeys = true, keyColumn = "fileid", keyProperty = "fileId")
    Integer uploadFile(File file);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    File getFile(Integer fileId);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    void deleteFile(Integer fileId);

}
