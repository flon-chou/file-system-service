package com.cj.qc.model;

import com.cj.qc.base.WebContext;
import com.cj.qc.tool.DateTool;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * Pojo基类：表公有字段，如createman、createdate、updateman、updatedate
 * @author chenzhaowen
 * @date 2018/9/11
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BasicPojo {

    @ApiModelProperty(value = "创建人ID")
    @Column(name = "creator_id",columnDefinition="bigint(20) COMMENT '创建人ID'")
    private Long creatorId;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "creation_time",columnDefinition="datetime COMMENT '创建时间'")
    private Timestamp creationTime;

    @ApiModelProperty(value = "创建人名称")
    @Column(name = "creator_name",columnDefinition="varchar(255) COMMENT '创建人名称'")
    private String creatorName;

    @ApiModelProperty(value = "最后修改人ID")
    @Column(name = "last_modifier_id",columnDefinition="bigint(20) COMMENT '最后修改人ID'")
    private Long lastModifierId;

    @ApiModelProperty(value = "最后修改时间")
    @Column(name = "last_modified",columnDefinition="datetime COMMENT '最后修改时间'")
    private Timestamp lastModified;

    @ApiModelProperty(value = "最后修改人名称")
    @Column(name = "last_modifier_name",columnDefinition="varchar(255) COMMENT '最后修改人名称'")
    private String lastModifierName;

    //todo：获取登陆人信息
    public void createOperation(){
        this.creatorId= WebContext.getLocalUserId();
        this.lastModifierId=this.creatorId;
        this.creatorName= WebContext.getLocalUserName();
        this.lastModifierName=this.creatorName;
        this.creationTime= DateTool.nowTime();
        this.lastModified= this.creationTime;
    }
    public void updateOperation(){
        this.lastModifierId= WebContext.getLocalUserId();
        this.lastModifierName= WebContext.getLocalUserName();
        this.lastModified= DateTool.nowTime();
    }

//    public void createOperationByUserId(Long creatorId){
//        this.creatorId=creatorId;
//        this.lastModifierId=this.creatorId;
//        this.creationTime=DateTool.nowTime();
//        this.lastModified= this.creationTime;
//    }
//    public void updateOperationByUserId(Long lastModifierId){
//        this.lastModifierId=lastModifierId;
//        this.lastModified= DateTool.nowTime();
//    }
//
//    public void createOperationByTimestamp(Timestamp timestamp){
//        this.creatorId=WebContext.getLocalUserId();
//        this.lastModifierId=this.creatorId;
//        this.creationTime=timestamp;
//        this.lastModified= this.creationTime;
//    }
//
//    public void updateOperationByTimestamp(Timestamp timestamp){
//        this.lastModifierId=WebContext.getLocalUserId();
//        this.lastModified= timestamp;
//    }
}
