/*******************************************************************************
** File Name   ：PossessorSvcImp.java
** Create Date ：2016-7-18
** Author      ：xing xiaojie
** Version     ：1.0.0
** CopyRight   ：duanyi.com.cn
**
** Modified Date      Modified By      Why
** xxxx-xx-xx         xxx              xxxxx
** ... ...
********************************************************************************/

package xyz.svc.security.imp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.exception.MyExceptionForRole;
import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.model.security.Possessor;
import xyz.model.security.Possessor_Relate;
import xyz.model.security.SecurityPosition;
import xyz.model.security.SecurityUser;
import xyz.svc.security.PossessorSvc;
import xyz.util.ListNumberCode;
import xyz.util.StringTool;
import xyz.util.StringUtil;
import xyz.util.UUIDUtil;

/**
 * @author xing xiaojie
 * @version 2016-7-18上午10:42:12
 */

@Service
public class PossessorSvcImp  implements PossessorSvc{
    
    @Autowired
    CommonDao commonDao;

    @Override
    public Map<String, Object> queryPossessorList(
            int offset, 
            int pagesize,
            String nameCn) {
        StringBuffer sql = new StringBuffer();
        sql.append("select p.number_code as numberCode, p.name_cn as nameCn, p.remark as remark ,p.add_date as addDate, p.alter_date as alterDate from possessor p where 1 = 1");
        if(nameCn != null && !"".equals(nameCn)){
            sql.append(" and p.name_cn like '%"+nameCn+"%'");
        }
      /*  {
            Map<String, List<String>> decideMap = MyRequestUtil.getDecideMap();
            if(decideMap!=null){
                List<String> channels = decideMap.get("channels");
                Collections.sort(channels);
                String sqlT = "SELECT t.possessor FROM possessor_relate t WHERE t.type = 'channel' GROUP BY t.possessor HAVING '"+StringTool.listToString(channels, "")+"' LIKE CONCAT('%', group_concat(t.relate ORDER BY t.relate ASC SEPARATOR '%' ),'%')";
                @SuppressWarnings("unchecked")
                List<String> numberCodeList = commonDao.getSqlQuery(sqlT).list();
                sql.append(" and p.number_code in ("+StringTool.listToSqlString(numberCodeList)+")");
            }
        }*/
        
        String countSql = "select count(tt.numberCode) from ("+sql.toString()+")tt";
        Query countQuery = commonDao.getSqlQuery(countSql);
        Number countTemp = (Number)countQuery.uniqueResult();
        int count = countTemp==null?0:countTemp.intValue();
        
        SQLQuery query = commonDao.getSqlQuery(sql.toString());
        query.addScalar("numberCode")
        .addScalar("nameCn")
        .addScalar("remark")
        .addScalar("addDate")
        .addScalar("alterDate")
        .setResultTransformer(Transformers.aliasToBean(Possessor.class));
        
        query.setMaxResults(pagesize);
        query.setFirstResult(offset);
        @SuppressWarnings("unchecked")
        List<Possessor> possessorList = query.list();
        
        Map<String,Object> mapContent = new HashMap<String, Object>();
        mapContent.put("total",count);
        mapContent.put("rows",possessorList);
        return ReturnUtil.returnMap(1,mapContent);
    }

    @Override
    public Map<String, Object> getPossessor(String numberCode) {
        
        Possessor possessor = (Possessor)commonDao.getObjectByUniqueCode("Possessor", "numberCode", numberCode);
        if(possessor == null){
            return ReturnUtil.returnMap(0,"机构编号有误!");
        }
        return ReturnUtil.returnMap(1,possessor);
    }
    
    
    @Override
    public Map<String, Object> addPossessor(
            String nameCn,
            String remark) {
        if(nameCn==null || "".equals(nameCn)){
            return ReturnUtil.returnMap(0, "参数丢失！");
        }
        
        Possessor possessor = new Possessor();
        possessor.setAddDate(new Date());
        possessor.setNumberCode(UUIDUtil.getUUIDStringFor32());
        possessor.setAlterDate(new Date());
        possessor.setNameCn(nameCn);
        possessor.setRemark(remark);
        commonDao.save(possessor);
        System.out.println("*************************************************");
        
//        Possessor_Relate possessorRelate = new Possessor_Relate();
//        possessorRelate.setPossessor(possessor.getNumberCode());
//        possessorRelate.setRelate(channel);
//        possessorRelate.setType("channel");
//        commonDao.save(possessorRelate);
        
        return ReturnUtil.returnMap(1,null);
    }

    @Override
    public Map<String, Object> editPossessor(
            String numberCode, 
            String nameCn,
            String remark) {
        
        Possessor possessor = (Possessor)commonDao.getObjectByUniqueCode("Possessor", "numberCode", numberCode);
        if(nameCn != null && !"".equals(nameCn)){
            possessor.setNameCn(nameCn);
        }
        possessor.setRemark(remark);
        possessor.setAlterDate(new Date());
        commonDao.update(possessor);
        return ReturnUtil.returnMap(1,null);
    }

    @Override
    public Map<String, Object> deletePossessor(String numberCodes) {
        String deletePossessorSql = "delete from possessor where number_code in ("+StringTool.StrToSqlString(numberCodes)+")";
        commonDao.getSqlQuery(deletePossessorSql).executeUpdate();
        return ReturnUtil.returnMap(1,null);
    }
    
    @Override
    public Map<String, Object> addPossessorResource(
            String possessor,
            String resources,
            String resourceType) {
        
        if(possessor == null || "".equals(possessor)){
            return ReturnUtil.returnMap(0, "机构有误!");
        }
        if(resources == null || "".equals(resources)){
            return ReturnUtil.returnMap(0, "请先选中需要操作的对象!");
        }
        
        String[] resourceArr = resources.split(",");
        Set<String> resourceSet = new LinkedHashSet<String>();
        for (int i = 0; i < resourceArr.length; i++) {
            if(resourceArr[i] != null && !"".equals(resourceArr[i])){
                resourceSet.add(resourceArr[i]);
            }
        }
        for (String str : resourceSet) {
            Possessor_Relate possessorRelate = new Possessor_Relate();
            possessorRelate.setPossessor(possessor);
            possessorRelate.setRelate(str);
            possessorRelate.setType(resourceType);
            commonDao.save(possessorRelate);
        }
        
        return ReturnUtil.returnMap(1,null);
    }
    
    @Override
    public Map<String, Object> deletePossessorResource(String possessor ,String possessorResources ,boolean isChannel) {
        if(isChannel){
            String countSql = "select count(*) from possessor_relate tt where tt.possessor = '"+possessor+"' and tt.type = 'channel'";
            Query countQuery = commonDao.getSqlQuery(countSql);
            Number countTemp = (Number)countQuery.uniqueResult();
            int haveCount = countTemp==null?0:countTemp.intValue();
            
            int deleteCount = possessorResources.split(",").length;
            if(deleteCount >= haveCount){
                return ReturnUtil.returnMap(0,"请至少保留一个渠道!");
            }
        }
        
        String sql = "delete from possessor_relate where possessor = '"+possessor+"' and relate in ("+StringTool.StrToSqlString(possessorResources)+")";
        commonDao.getSqlQuery(sql).executeUpdate();
        return ReturnUtil.returnMap(1,null);
    }

    @Override
    public Map<String, Object> queryPositionListByInOrNotInUserNumberCodes(
            int offset, 
            int pagesize, 
            String inPositionNumberCodes,
            String queryJson, 
            boolean flag) {
        
        String cPosition = MyRequestUtil.getSecurityLogin().getPosition();
        SecurityPosition securityPosition = (SecurityPosition)commonDao.getObjectByUniqueCode("SecurityPosition","numberCode",cPosition);
        if(securityPosition==null){
            throw new MyExceptionForRole("岗位不存在，请联系系统管理员！");
        }
        
        StringBuffer hql = new StringBuffer();
        hql.append("from SecurityPosition t where 1=1");
        {
            Map<String, List<String>> decideMap = MyRequestUtil.getDecideMap();
            if(decideMap!=null){
                SecurityUser securityUser = (SecurityUser)commonDao.getObjectByUniqueCode("SecurityUser", "username", MyRequestUtil.getSecurityLogin().getUsername());
                String sql = "select t.relate from possessor_relate t where t.type = 'position' and t.possessor = '"+securityUser.getPossessor()+"'";
                @SuppressWarnings("unchecked")
                List<String> numberCodeList = commonDao.getSqlQuery(sql).list();
                hql.append(" and t.numberCode in ("+StringTool.listToSqlString(numberCodeList)+")");
            }
        }
        String not = flag?"":" not ";
        hql.append(" and t.numberCode "+not+" in ("+StringTool.StrToSqlString(inPositionNumberCodes)+")");
        if(StringUtil.yqqQueryExists(queryJson, "")){
            hql.append(" and t.numberCode = '"+StringUtil.yqqQueryString(queryJson, "")+"'");
        }
        if(StringUtil.yqqQueryExists(queryJson, "nameCn")){
            hql.append(" and t.nameCn like '%"+StringUtil.yqqQueryString(queryJson, "nameCn")+"%'");
        }
        
        Query query = commonDao.getQuery(hql.toString());
        int count = query.list().size();
        query.setMaxResults(pagesize);
        query.setFirstResult(offset);
        @SuppressWarnings("unchecked")
        List<SecurityPosition> positionList = query.list();
        commonDao.clear();
        
        for(SecurityPosition position:positionList){
            String sql = "select count(t.iidd) from security_user t where t.position = '"+position.getNumberCode()+"'";
            Number number = (Number)commonDao.getSqlQuery(sql).uniqueResult();
            int countUser = number==null?0:number.intValue();
            position.setCountUser(countUser);
        }
        
        Map<String,Object> mapContent = new HashMap<String, Object>();
        mapContent.put("total",count);
        mapContent.put("rows",positionList);

        return ReturnUtil.returnMap(1, mapContent);
    }

    @Override
    public Map<String, Object> queryPossessorResourceList(
            String possessor,
            String type) {
        
        StringBuffer hql = new StringBuffer();
        hql.append("from Possessor_Relate pr where 1 = 1");
        if(possessor != null && !"".equals(possessor)){
            hql.append(" and pr.possessor = '"+possessor+"'");
        }
        if(type != null && !"".equals(type)){
            hql.append(" and pr.type = '"+type+"'");
        }
        
        @SuppressWarnings("unchecked")
        List<Possessor_Relate> possessorRelateList = commonDao.getQuery(hql.toString()).list();
        
        return ReturnUtil.returnMap(1, possessorRelateList);
    }

    @Override
    public Map<String, Object> getPossessorList(String q) {
        String sql = "SELECT p.number_code AS value,p.name_cn AS text FROM possessor p where 1=1";
        if(q!=null && !"".equals(q)){
            sql += " and p.name_cn like '%"+q+"%'";
        }
        {
            Map<String, List<String>> decideMap = MyRequestUtil.getDecideMap();
            if(decideMap!=null){
                List<String> channels = decideMap.get("channels");
                Collections.sort(channels);
                String sqlT = "SELECT t.possessor FROM possessor_relate t WHERE t.type = 'channel' GROUP BY t.possessor HAVING '"+StringTool.listToString(channels, "")+"' LIKE CONCAT('%', group_concat(t.relate ORDER BY t.relate ASC SEPARATOR '%' ),'%')";
                @SuppressWarnings("unchecked")
                List<String> numberCodeList = commonDao.getSqlQuery(sqlT).list();
                sql+=" and p.number_code in ("+StringTool.listToSqlString(numberCodeList)+")";
            }
        }
        
        SQLQuery query = commonDao.getSqlQuery(sql);
        query.addScalar("value").
        addScalar("text").
        setResultTransformer(Transformers.aliasToBean(ListNumberCode.class));
        
        query.setMaxResults(100);
        
        @SuppressWarnings("unchecked")
        List<ListNumberCode> results = query.list();
        return ReturnUtil.returnMap(1, results);
    }
}
