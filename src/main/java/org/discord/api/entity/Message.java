package org.discord.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(value = {"id", "username", "avatar", "from", "time"}, allowGetters = true)
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long channel;

    private Integer type;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String avatar;

    @TableField(value = "`from`")
    private Long from;

    @TableField(value = "`to`")
    private Long to;

    private String content;

    private String file;

    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date time;

}
