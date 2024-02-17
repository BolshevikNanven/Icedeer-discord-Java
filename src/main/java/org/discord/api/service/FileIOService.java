package org.discord.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.discord.api.entity.File;
import org.discord.api.mapper.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;

@Service
public class FileIOService {
    @Resource
    private FileMapper fileMapper;
    public String getFileAsBase64(String id){
        QueryWrapper<File> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);

        byte[] buffer= fileMapper.selectOne(queryWrapper).getData();

        return Base64Utils.encodeToString(buffer);
    }
    public byte[] getFile(String id){
        QueryWrapper<File> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);

        return fileMapper.selectOne(queryWrapper).getData();
    }
}
