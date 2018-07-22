package com.platform.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体
 * 表名 cnn_learn_question
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-22 14:52:32
 */
public class CnnLearnQuestionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //学习内容ID
    private Integer id;
    //学习类型ID
    private Integer learnTypeId;
    //学习类型名称
    private String learnType;
    //学习天数
    private Integer genusDays;
    //重点内容
    private String oraleitem;
    //中文问题
    private String oralech;
    //其它问题
    private String oralejp;
    //关键词
    private String keyword;
    //解题分析
    private String analysis;
    //选项1
    private String opt1;
    //选项2
    private String opt2;
    //选项3
    private String opt3;
    //选项4
    private String opt4;
    //内容语音链接
    private String oralesound;
    //答案选项
    private Integer copt;
    //是否是问答
    private Integer typeof;
    //添加时间
    private Date addTime;
    //修改时间
    private Date updateTime;

    /**
     * 设置：学习内容ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：学习内容ID
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：学习类型ID
     */
    public void setLearnTypeId(Integer learnTypeId) {
        this.learnTypeId = learnTypeId;
    }

    /**
     * 获取：学习类型ID
     */
    public Integer getLearnTypeId() {
        return learnTypeId;
    }
    /**
     * 设置：学习类型名称
     */
    public void setLearnType(String learnType) {
        this.learnType = learnType;
    }

    /**
     * 获取：学习类型名称
     */
    public String getLearnType() {
        return learnType;
    }

    /**
     * 设置：学习天数
     */
    public void setGenusDays(Integer genusDays) {
        this.genusDays = genusDays;
    }

    /**
     * 获取：学习天数
     */
    public Integer getGenusDays() {
        return genusDays;
    }
    /**
     * 设置：重点内容
     */
    public void setOraleitem(String oraleitem) {
        this.oraleitem = oraleitem;
    }

    /**
     * 获取：重点内容
     */
    public String getOraleitem() {
        return oraleitem;
    }

    /**
     * 设置：中文问题
     */
    public void setOralech(String oralech) {
        this.oralech = oralech;
    }

    /**
     * 获取：中文问题
     */
    public String getOralech() {
        return oralech;
    }

    /**
     * 设置：其它问题
     */
    public void setOralejp(String oralejp) {
        this.oralejp = oralejp;
    }

    /**
     * 获取：其它问题
     */
    public String getOralejp() {
        return oralejp;
    }

    /**
     * 设置：关键词
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * 获取：关键词
     */
    public String getKeyword() {
        return keyword;
    }
    /**
     * 设置：解题分析
     */
    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    /**
     * 获取：解题分析
     */
    public String getAnalysis() {
        return analysis;
    }
    /**
     * 设置：选项1
     */
    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    /**
     * 获取：选项1
     */
    public String getOpt1() {
        return opt1;
    }
    /**
     * 设置：选项2
     */
    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    /**
     * 获取：选项2
     */
    public String getOpt2() {
        return opt2;
    }
    /**
     * 设置：选项3
     */
    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    /**
     * 获取：选项3
     */
    public String getOpt3() {
        return opt3;
    }
    /**
     * 设置：选项4
     */
    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }

    /**
     * 获取：选项4
     */
    public String getOpt4() {
        return opt4;
    }
    /**
     * 设置：内容语音链接
     */
    public void setOralesound(String oralesound) {
        this.oralesound = oralesound;
    }

    /**
     * 获取：内容语音链接
     */
    public String getOralesound() {
        return oralesound;
    }
    /**
     * 设置：答案选项
     */
    public void setCopt(Integer copt) {
        this.copt = copt;
    }

    /**
     * 获取：答案选项
     */
    public Integer getCopt() {
        return copt;
    }
    /**
     * 设置：是否是问答
     */
    public void setTypeof(Integer typeof) {
        this.typeof = typeof;
    }

    /**
     * 获取：是否是问答
     */
    public Integer getTypeof() {
        return typeof;
    }
    /**
     * 设置：添加时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取：添加时间
     */
    public Date getAddTime() {
        return addTime;
    }
    /**
     * 设置：修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }
}
