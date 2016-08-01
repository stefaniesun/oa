/*******************************************************************************
** File Name   ：PossessorSvc.java
** Create Date ：2016-7-18
** Author      ：xing xiaojie
** Version     ：1.0.0
** CopyRight   ：duanyi.com.cn
**
** Modified Date      Modified By      Why
** xxxx-xx-xx         xxx              xxxxx
** ... ...
********************************************************************************/

package xyz.svc.security;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @author xing xiaojie
 * @version 2016-7-18上午10:41:24
 */

@Service
public interface PossessorSvc{
    
    public Map<String ,Object> queryPossessorList(
            int offset,
            int pagesize,
            String nameCn);
    
    public Map<String ,Object> getPossessor(String numberCode);
    
    public Map<String ,Object> addPossessor(
            String nameCn,
            String remark);
    
    public Map<String ,Object> editPossessor(
            String numberCode,
            String nameCn,
            String remark);
    
    public Map<String ,Object> deletePossessor(String numberCodes);
    
    public Map<String ,Object> deletePossessorResource(String possessor, String possessorResources ,boolean isChannel);
    
    public Map<String ,Object> addPossessorResource(
            String possessor,
            String resources,
            String resourceType);
    
    public Map<String ,Object> queryPositionListByInOrNotInUserNumberCodes(
            int offset,
            int pagesize,
            String inPositionNumberCodes,
            String queryJson,
            boolean flag);
    
    public Map<String ,Object> getPossessorList(String q);
    
    public Map<String ,Object> queryPossessorResourceList(String possessor ,String type);
}
