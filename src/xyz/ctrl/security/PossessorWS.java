/*******************************************************************************
** File Name   ：PossessorWS.java
** Create Date ：2016-7-18
** Author      ：xing xiaojie
** Version     ：1.0.0
** CopyRight   ：duanyi.com.cn
**
** Modified Date      Modified By      Why
** xxxx-xx-xx         xxx              xxxxx
** ... ...
********************************************************************************/

package xyz.ctrl.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.security.PossessorSvc;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author xing xiaojie
 * @version 2016-7-18上午11:32:16
 */

@Controller
@RequestMapping(value="/PossessorWS")
public class PossessorWS {

    @Autowired
    PossessorSvc possessorSvc;
    
    /**
     * 管理员--机构--查询机构列表
     */
    @RequestMapping(value="queryPossessorList")
    @ResponseBody
    public Map<String, Object> queryPossessorList(
            int page,
            int rows,
            String nameCn){
        
        int pagesize = rows;
        int offset = (page-1)*pagesize;
        
        return possessorSvc.queryPossessorList(offset, pagesize, nameCn);
    }
    
    /**
     *  管理员--机构--查询机构有的资源
     */
    @RequestMapping(value="getPossessor")
    @ResponseBody
    public Map<String ,Object> getPossessor(String possessor){
        
        return possessorSvc.getPossessor(possessor);
    }
    
    /**
     *  管理员--机构--添加机构
     */
    @RequestMapping(value="addPossessor")
    @ResponseBody
    public Map<String ,Object> addPossessor(
            String nameCn,
            String remark){
        
        return possessorSvc.addPossessor(nameCn ,remark);
    }
    
    /**
     *  管理员--机构--编辑机构
     */
    @RequestMapping(value="editPossessor")
    @ResponseBody
    public Map<String ,Object> editPossessor(
            String numberCode,
            String nameCn,
            String remark){
        
        return possessorSvc.editPossessor(numberCode ,nameCn ,remark);
    }
    
    /**
     *  管理员--机构--删除机构
     */
    @RequestMapping(value="deletePossessor")
    @ResponseBody
    public Map<String ,Object> deletePossessor(String numberCodes){
        return possessorSvc.deletePossessor(numberCodes);
    }
    
    /**
     *  管理员--机构--添加单品给机构
     */
    @RequestMapping(value="addPossessorTkview")
    @ResponseBody
    public Map<String ,Object> addPossessorTkview(
            String possessor,
            String possessorResources){
        return possessorSvc.addPossessorResource(possessor, possessorResources ,"tkview");
    }
    
    /**
     *  管理员--机构--从机构删除单品
     */
    @RequestMapping(value="deletePossessorTkview")
    @ResponseBody
    public Map<String ,Object> deletePossessorTkview(String possessor ,String possessorResources){
        return possessorSvc.deletePossessorResource(possessor, possessorResources ,false);
    }
    
    /**
     *  管理员--机构--添加产品给机构
     */
    @RequestMapping(value="addPossessorPtview")
    @ResponseBody
    public Map<String ,Object> addPossessorPtview(
            String possessor,
            String possessorResources){
        return possessorSvc.addPossessorResource(possessor, possessorResources, "ptview");
    }
    
    /**
     *  管理员--机构--从机构删除产品
     */
    @RequestMapping(value="deletePossessorPtview")
    @ResponseBody
    public Map<String ,Object> deletePossessorPtview(String possessor ,String possessorResources){
        return possessorSvc.deletePossessorResource(possessor, possessorResources ,false);
    }
    
    /**
     *  管理员--机构--添加渠道给机构
     */
    @RequestMapping(value="addPossessorChannel")
    @ResponseBody
    public Map<String ,Object> addPossessorChannel(
            String possessor,
            String possessorResources){
        return possessorSvc.addPossessorResource(possessor, possessorResources ,"channel");
    }
    
    /**
     *  管理员--机构--从机构删除渠道
     */
    @RequestMapping(value="deletePossessorChannel")
    @ResponseBody
    public Map<String ,Object> deletePossessorChannel(String possessor ,String possessorResources){
        return possessorSvc.deletePossessorResource(possessor, possessorResources ,true);
    }
    
    /**
     *  管理员--机构--添加供应商给机构
     */
    @RequestMapping(value="addPossessorProvider")
    @ResponseBody
    public Map<String ,Object> addPossessorProvider(
            String possessor,
            String possessorResources){
        return possessorSvc.addPossessorResource(possessor, possessorResources, "provider");
    }
    
    /**
     *  管理员--机构--从机构删除单品
     */
    @RequestMapping(value="deletePossessorProvider")
    @ResponseBody
    public Map<String ,Object> deletePossessorProvider(String possessor, String possessorResources){
        return possessorSvc.deletePossessorResource(possessor, possessorResources ,false);
    }
    
    /**
     *  管理员--机构--添加岗位给机构
     */
    @RequestMapping(value="addPossessorPosition")
    @ResponseBody
    public Map<String ,Object> addPossessorPosition(
            String possessor,
            String possessorResources){
        return possessorSvc.addPossessorResource(possessor, possessorResources, "position");
    }
    
    /**
     *  管理员--机构--从机构删除岗位
     */
    @RequestMapping(value="deletePossessorPosition")
    @ResponseBody
    public Map<String ,Object> deletePossessorPosition(String possessor ,String possessorResources){
        return possessorSvc.deletePossessorResource(possessor, possessorResources ,false);
    }
    
    /**
     * 管理员--机构--查询机构的用户
     */
    @RequestMapping(value="queryPossessorPositionTrueList")
    @ResponseBody
    public Map<String, Object> queryPossessorPositionTrueList(
            int page,
            int rows,
            String inPossessorResourceNumberCodes,
            String queryJson){
        
        int pagesize = rows;
        int offset = (page-1)*pagesize;
        
        return possessorSvc.queryPositionListByInOrNotInUserNumberCodes(offset, pagesize, inPossessorResourceNumberCodes, queryJson, true);
    }
    
    /**
     * 管理员--机构--查询机构 没有的用户
     */
    @RequestMapping(value="queryPossessorPositionFalseList")
    @ResponseBody
    public Map<String, Object> queryPossessorPositionFalseList(
            int page,
            int rows,
            String inPossessorResourceNumberCodes,
            String queryJson){
        
        int pagesize = rows;
        int offset = (page-1)*pagesize;
        
        return possessorSvc.queryPositionListByInOrNotInUserNumberCodes(offset, pagesize, inPossessorResourceNumberCodes ,queryJson, false);
    }
    
    /**
     * 下拉框--获取机构列表
     */
    @RequestMapping(value="getPossessorList")
    @ResponseBody
    public Map<String,Object> getPossessorList(String q){
        Map<String,Object> results = possessorSvc.getPossessorList(q);
        return results;
    }
    
    /**
     * 管理员 - 机构管理 - 初始化 机构资源列表
     */
    @RequestMapping(value="queryPossessorResourceList")
    @ResponseBody
    public Map<String,Object> queryPossessorResourceList(String possessor ,String type){
        Map<String,Object> results = possessorSvc.queryPossessorResourceList(possessor ,type);
        return results;
    }
}
