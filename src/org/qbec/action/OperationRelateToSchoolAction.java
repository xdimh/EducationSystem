package org.qbec.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.qbec.dao.DB;



public class OperationRelateToSchoolAction {

	
	private String aaData;
	private String aoColumns;
	private String queryData;
	private String sid;
	private String schoolInfoData;
	
	public String getSchoolInfoData() {
		return schoolInfoData;
	}

	public void setSchoolInfoData(String schoolInfoData) {
		this.schoolInfoData = schoolInfoData;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getQueryData() {
		return queryData;
	}

	public void setQueryData(String queryData) {
		this.queryData = queryData;
	}



	private Map sentData = new HashMap();
	
	public String getAaData() {
		return aaData;
	}

	public void setAaData(String aaData) {
		this.aaData = aaData;
	}

	
	public String getAoColumns() {
		return aoColumns;
	}

	public void setAoColumns(String aoColumns) {
		this.aoColumns = aoColumns;
	}

	public Map getSentData() {
		return sentData;
	}

	public void setSentData(Map sentData) {
		this.sentData = sentData;
	}

	public ArrayList<Map<String,String>> makeTitle(int n,Map<String,String> m)
	{
		ArrayList<Map<String,String>> tList = new ArrayList<Map<String,String>>();
		for(int i=0;i<n;i++)
		{
			m.clear();
			switch(i)
			{
				case 0:
					m.put("sTitle", "学校名称");
					m.put("sWidth", "25%");
					break;
				case 1:
					m.put("sTitle", "学校地址");
					m.put("sWidth", "20%");
					break;
				case 2:
					m.put("sTitle", "学校性质");
					m.put("sWidth", "15%");
					break;
				case 3:
					m.put("sTitle", "学校类别");
					m.put("sWidth", "15%");
					break;
				case 4:
					m.put("sTitle", "备注");
					m.put("sWidth", "25%");
					break;
				case 5:
					break;
			}
			tList.add(m);
		}
		
		return tList;
	}
	
	
	
	public String search()
	{
	//	HashMap<String,String> title_map = new HashMap<String,String>();
		ArrayList<Map> recordList = new ArrayList<Map>(); 
	//	ArrayList<Map<String,String>> titleList = new ArrayList<Map<String,String>>(); 
		Connection conn =null;
		Statement stmt = null;
		ResultSet rs = null;
		//构造查询语句
		
		
		String querySql = "";
		String queryHeader = "select a.sid,sno,sname,province,city,attr,category,remark";
		String fromTable = " from school_key_info a";
		String caseStr = "";
		String leftjoin = "";
		String lkStr = "";
		String wkStr = "";
		String lkRatiOrWkRatioStr = "";
		String whereStr = " where";
		String snoStr = "";
		String snameStr = "";
		String yearStr = "";
		String addrStr = "";
		String attrStr = "";
		String classStr = "";
		String remarkStr ="";
		String piciStr = "";
		String groupByStr = "";
		String havingStr = "";
		String orderByStr = "";
		queryData = queryData.substring(1);
		queryData = queryData.substring(0,queryData.length()-1);
		queryData = queryData.replace("\\","");
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( queryData );
		sentData = (Map)JSONObject.toBean(jsonObject.getJSONObject("sentData"), Map.class);
		//构造地址条件addrStr
		if(sentData.containsKey("addr"))
		{
			ArrayList addr = (ArrayList)sentData.get("addr");
			for(int i=0; i<addr.size(); i++)
			{
				ArrayList subAddr = (ArrayList)addr.get(i);
				if(i == 0)
				{
					if(((String)subAddr.get(0)).equals("不限"))
					break;
					else
						addrStr += " (";
				}
				String tempStr = " and a.city in(";
				for(int j=0; j<subAddr.size(); j++)
				{
					if(j==0)
					{
						addrStr += "(a.province = '" + subAddr.get(j) + "' ";
					}else{
						if(!((String)subAddr.get(j)).equals("不限"))
						{
							if(j==(subAddr.size()-1))
							{
								tempStr += "'"+subAddr.get(j)+"') ";
								addrStr += tempStr;
							}else{
								tempStr += "'"+subAddr.get(j)+"',";
							}
						}
					}
					if(j==(subAddr.size()-1))
					{
						if(i==(addr.size()-1))
							addrStr += ")) ";
						else
							addrStr += ") or ";
					}
				}
			}
		}//if addr
		//构造属性条件
		
		//构造snoStr
		if(sentData.containsKey("sno"))
		{
			snoStr += " sno REGEXP '"+(String)sentData.get("sno") + "'";
		}
		
		//构造snameStr
		if(sentData.containsKey("sname"))
		{
			snameStr += " sname like '%"+(String)sentData.get("sname") + "%'";
		}
		
		//构造attrStr 
		if(sentData.containsKey("attr"))
		{
			ArrayList attr = (ArrayList)sentData.get("attr");
			attrStr = " attr in(";
			for(int i=0; i<attr.size(); i++)
			{
				if(i==(attr.size()-1))
					attrStr += "'" + (String)attr.get(i) + "')";
				else
					attrStr += "'" + (String)attr.get(i) + "',";
			}
			if(attr.size() == 0)
			{
				attrStr = "";
			}
		}
		
		//构造classStr
		if(sentData.containsKey("category"))
		{
			ArrayList classList = (ArrayList)sentData.get("category");
			classStr = " category in(";
			for(int i=0; i<classList.size(); i++)
			{
				if(i==(classList.size()-1))
					classStr += "'" + (String)classList.get(i) + "')";
				else
					classStr += "'" + (String)classList.get(i) + "',";
			}
			if(classList.size() == 0)
			{
				classStr = "";
			}
		}
		
		//构造lkStr WkStr
		
		if(sentData.containsKey("lk"))
		{
			lkStr = " a.lk like '";
			ArrayList lk = (ArrayList)sentData.get("lk");
			if(sentData.containsKey("bl"))
			{
				lkStr += "%" + (String)lk.get(0) + "%' ";
			}else{
//					for(int i=0; i<lk.size(); i++)
//					{
//						if(i==0)
//						{
//							lkStr += (String)lk.get(i);
//						}else{
//							lkStr += "/"+ (String)lk.get(i);
//						}
//
//						if(i==(lk.size()-1)){
//							lkStr += "' ";
//						}
//					}
//					if(lk.size() == 0)
//						lkStr = "";
				lkStr = "";
			}
		}
		
		if(sentData.containsKey("wk"))
		{
			wkStr = " a.wk like '";
			ArrayList wk = (ArrayList)sentData.get("wk");
			if(sentData.containsKey("bl"))
			{
				wkStr += "%" + (String)wk.get(0) + "%' ";
			}else{
					for(int i=0; i<wk.size(); i++)
					{
						if(i==0)
						{
							wkStr += (String)wk.get(i);
						}else{
							wkStr += "/"+ (String)wk.get(i);
						}
						if(i==(wk.size()-1)){
							wkStr += "' ";
						}
					}
					if(wk.size() == 0)
						wkStr = "";
			}
		}
		String c1 = "(lk_ratio=0 AND EXISTS (SELECT * FROM t_skx WHERE sid = a.sid AND (lk_ratio >= 1 AND lk_ratio <="; 
		String c2 = "(lk_ratio=-1 AND EXISTS (SELECT * FROM t_skx WHERE sid = a.sid AND (lk_ratio >= 1 AND lk_ratio <=";
		String lkNumStr = "and (lk_num IS NULL OR EXISTS ( SELECT * FROM t_skx WHERE sid = a.sid AND (lk_num IS NOT NULL) AND YEAR IN ( SELECT YEAR FROM ( SELECT DISTINCT YEAR FROM t_skx ORDER BY `year` DESC LIMIT 3 ) AS c )))";
		String wkNumStr = "and (wk_num IS NULL OR EXISTS ( SELECT * FROM t_skx WHERE sid = a.sid AND (wk_num IS NOT NULL) AND YEAR IN ( SELECT YEAR FROM ( SELECT DISTINCT YEAR FROM t_skx ORDER BY `year` DESC LIMIT 3 ) AS c )))";
		String common = ") AND YEAR IN ( SELECT YEAR FROM(SELECT DISTINCT YEAR FROM t_skx ORDER BY `year` DESC LIMIT 3) AS c)))";
		String w_c1 = "(wk_ratio=0 AND EXISTS (SELECT * FROM t_skx WHERE sid = a.sid AND (wk_ratio >= 1 AND wk_ratio <="; 
		String w_c2 = "(wk_ratio=-1 AND EXISTS (SELECT * FROM t_skx WHERE sid = a.sid AND (wk_ratio >= 1 AND wk_ratio <=";
		//构造remarkStr
		if(sentData.containsKey("remark"))
		{
			remarkStr += " remark like '%"+(String)sentData.get("remark") + "%'";
		}		
		//构造leftjoin lkRatiOrWkRatioStr caseStr yearStr havingStr orderByStr piciStr
		if(sentData.containsKey("bl"))
		{
			leftjoin = " left join t_skx b on a.sid = b.sid ";
			ArrayList bl = (ArrayList)sentData.get("bl");
			if(sentData.containsKey("pre")&&sentData.containsKey("post"))
			{
				if(sentData.containsKey("lk"))
				{
					lkRatiOrWkRatioStr = " ((lk_ratio>=" + sentData.get("pre") + " and " + "lk_ratio<="+sentData.get("post")+") or "+ c1 + sentData.get("post") + common + "or " + c2 + sentData.get("post") + common + ")" + lkNumStr;
				}else{
					lkRatiOrWkRatioStr = " ((wk_ratio>=" + sentData.get("pre") + " and " + "wk_ratio<="+sentData.get("post")+") or "+ w_c1 + sentData.get("post") + common + "or " + w_c2 + sentData.get("post") + common + ")" + wkNumStr;
				}
				
			}else if(sentData.containsKey("pre"))
			{
				if(sentData.containsKey("lk"))
				{
					lkRatiOrWkRatioStr = " (lk_ratio>=" + sentData.get("pre") + " or lk_ratio=0 or lk_ratio=-1)";
				}else{
					lkRatiOrWkRatioStr = " (wk_ratio>=" + sentData.get("pre") + " or wk_ratio=0 or wk_ratio=-1)";
				}
			}else {
				if(sentData.containsKey("lk"))
				{
//					lkRatiOrWkRatioStr = " ((lk_ratio>=1 and " + "lk_ratio<="+sentData.get("post")+") or "+ c1 + sentData.get("post") + common + "or " + c2 + sentData.get("post") + common + ")" + lkNumStr;
					lkRatiOrWkRatioStr = " ((lk_ratio>=1 and " + "lk_ratio<="+sentData.get("post")+")" + " or lk_ratio=0 or lk_ratio=-1)";
				}else{
//					lkRatiOrWkRatioStr = " ((wk_ratio>=1 and " + "wk_ratio<="+sentData.get("post")+") or "+ w_c1 + sentData.get("post") + common + "or " + w_c2 + sentData.get("post") + common + ")" + wkNumStr;
					lkRatiOrWkRatioStr = " ((wk_ratio>=1 and " + "wk_ratio<="+sentData.get("post")+")"  + " or wk_ratio=0 or wk_ratio=-1)";
				}
			}
			if(((String)bl.get(0)).equals("按年数查询"))
			{	
				yearStr = " year IN(SELECT year from (SELECT DISTINCT year FROM t_skx ORDER BY `year` DESC LIMIT 3) as c)";
				String num_of_year = (String)bl.get(1);
				if(num_of_year.equals("3年"))
				{
					caseStr = " ,case count(*) when 3 then '100%' when 2 then '85%' when 1 then '60%' end as ratio";
					havingStr = " having count(*)>0 ";
				}else if(num_of_year.equals("2年"))
				{
					caseStr = " ,case count(*) when 2 then '100%' when 1 then '60%' end as ratio";
					havingStr = " having count(*)>0 and count(*)<3 ";
				}else if(num_of_year.equals("1年"))
				{
					caseStr = " ,case count(*) when 1 then '100%' end as ratio";
					havingStr = " having count(*)>0 and count(*)<2 ";
				}
			}else {
				int i = 1;
				int count = bl.size()-1;
				yearStr = " year in(";
				if(bl.get(1).equals("全部"))
				{
					i = 2;
					count = bl.size()-2;
				}
				for(;i<bl.size();i++)
				{
					if(i == bl.size()-1)
						yearStr += "'" + ((String)bl.get(i)).replaceAll("[^\\d]", "") + "')";
					else
						yearStr += "'" + ((String)bl.get(i)).replaceAll("[^\\d]", "") + "',";
				}
				havingStr = " having count(*)=" + count;
			}
			groupByStr = " group by a.sname";
			orderByStr = " order by count(*) desc";
			piciStr = " b.pici = '";
			if(sentData.containsKey("lk")){
				piciStr += ((String)((ArrayList)sentData.get("lk")).get(0)) + "'";
			}else{
				piciStr += ((String)((ArrayList)sentData.get("wk")).get(0)) + "'";
			}
		}
		
		
		//构造querySql
		querySql += queryHeader;
		if(!caseStr.equals(""))
		{
			querySql += caseStr;
		}
		querySql += fromTable;
		if(!leftjoin.equals(""))
		{
			querySql+=leftjoin;
		}
		querySql += whereStr;
		if(!addrStr.equals(""))
		{
			querySql += addrStr + " and ";
		}
		if(!snoStr.equals(""))
		{
			querySql += snoStr + " and ";
		}
		if(!snameStr.equals(""))
		{
			querySql += snameStr + " and ";
		}
		if(!yearStr.equals(""))
		{
			querySql += yearStr + " and ";
		}
		if(!attrStr.equals(""))
		{
			querySql += attrStr + " and ";
		}
		if(!classStr.equals(""))
		{
			querySql += classStr + " and ";
		}
		if(!lkStr.equals(""))
		{
			querySql += lkStr + " and ";
		}
		if(!wkStr.equals(""))
		{
			querySql += wkStr + " and ";
		}
		if(!lkRatiOrWkRatioStr.equals(""))
		{
			querySql += lkRatiOrWkRatioStr + " and ";
		}
		if(!remarkStr.equals(""))
		{
			querySql += remarkStr + " and ";
		}
		if(!piciStr.equals(""))
		{
			querySql += piciStr + " and ";
		}
		if(querySql.endsWith("and "))
		{
			querySql = querySql.substring(0,querySql.length()-5);
		}
		if(querySql.endsWith("where"))
		{
			querySql = querySql.substring(0,querySql.length()-6);
		}
		if(!groupByStr.equals(""))
		{
			querySql += groupByStr;
		}
		if(!havingStr.equals(""))
		{
			querySql += havingStr;
		}
		if(!orderByStr.equals(""))
		{
			querySql += orderByStr;
		}
		System.out.println(querySql);
		
		//执行查询
		conn = DB.getConn();
		stmt = DB.getStmt(conn);
		rs = DB.executeQuery(stmt, querySql);
		String str = "";
		//int count = 0;
		try {
			while(rs.next())
			{
				//count++;
				Map<String,String> listForMap = new HashMap<String,String>(); 
				listForMap.put("sname","<a href=\"PDF?sid=" + rs.getInt("sid") + "\" id=\"" + rs.getInt("sid") + "\" target=\"_blank\">" + rs.getString("sname") + "</a>");
				listForMap.put("modify","<a href=\"qbec_schoolInfo?sid=" + rs.getInt("sid") + "\" id=\"" + rs.getInt("sid") + "\" target=\"_blank\">" + "修改</a>");
				listForMap.put("address","<span data-sno=\"" + rs.getString("sno") + "\">" + rs.getString("province") + " " + rs.getString("city") + "</span>");
				listForMap.put("attr",rs.getString("attr"));
				listForMap.put("sclass",rs.getString("category"));
				listForMap.put("remark",rs.getString("remark") == null?"无":rs.getString("remark"));
				if(!caseStr.equals(""))
				{
					listForMap.put("ratio",rs.getString("ratio"));
				}else{
					listForMap.put("ratio","1");
				}
				/*str = "sid:" + rs.getInt("sid")+" sname:" + rs.getString("sname")
				+ " province:" + rs.getString("province") + "city: " + rs.getString("city") + " attr:"
				+ rs.getString("attr") + " class:" + rs.getString("class") + " remark:" + rs.getString("remark");*/
				//result_map.put(String.valueOf(rs.getInt("sid")), listForMap);
				recordList.add(listForMap);
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*JSONObject jsonData = new JSONObject();
		jsonData.put("iTotalRecords", count);
		jsonData.put("iTotalDisplayRecords", 10);
		jsonData.put("sEcho", 0);*/
		DB.close(conn);
		DB.close(stmt);
		DB.close(rs);
		
		
		JSONArray jsonObj = JSONArray.fromObject(recordList);
		//jsonData.put("aaData", jsonObj);
	//	JSONArray title = JSONArray.fromObject(titleList);
		//JSONObject jsonObj = JSONObject.fromObject(result_map);
		setAaData(jsonObj.toString());
	//	setAoColumns(title.toString());
		System.out.println(jsonObj.toString());
		
		return "queryset";
	}

	


	
	public String getSchoolInfo() {
		
		Connection conn =null;
		Statement stmt = null;
		ResultSet rs = null;
		JSONObject schoolInfoJSON = new JSONObject();
		JSONArray year = new JSONArray();
		List yearList;
		String sql1 = "SELECT * FROM school_key_info a JOIN school_scale b where a.sid = b.sid and a.sid = " + sid;
		String sql2 = "SELECT DISTINCT year from t_skx a where a.sid = " + sid + " ORDER BY year desc LIMIT 3;";
		String sql3 = "SELECT * from t_skx a where a.sid = " + sid + " and a.year = ";
		
		
		conn = DB.getConn();
		stmt = DB.getStmt(conn);
		rs = DB.executeQuery(stmt, sql1);
		
		try {
			while(rs.next()){
				schoolInfoJSON.accumulate("sname", rs.getString("sname"));
				schoolInfoJSON.accumulate("sno", rs.getString("sno"));
				schoolInfoJSON.accumulate("province", rs.getString("province"));
				schoolInfoJSON.accumulate("city", rs.getString("city"));
				schoolInfoJSON.accumulate("remark", rs.getString("remark"));
				schoolInfoJSON.accumulate("attr", rs.getString("attr"));
				schoolInfoJSON.accumulate("category", rs.getString("category"));
				schoolInfoJSON.accumulate("wk", rs.getString("wk"));
				schoolInfoJSON.accumulate("lk", rs.getString("lk"));
				schoolInfoJSON.accumulate("area", rs.getObject("area") == null || rs.getString("area").equals("0")  ? "" : rs.getString("area"));
				schoolInfoJSON.accumulate("stu_num", rs.getObject("stu_num") == null || rs.getString("stu_num").equals("0")  ? "" : rs.getString("stu_num"));
				schoolInfoJSON.accumulate("m_point", rs.getString("m_point") == null ? "" : rs.getString("m_point"));
				schoolInfoJSON.accumulate("d_point", rs.getString("d_point") == null ? "" : rs.getString("d_point"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		rs = DB.executeQuery(stmt, sql2);

		try {
			while(rs.next()) {
				year.add(rs.getInt("year"));
			}
			schoolInfoJSON.accumulate("year", year);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		yearList =new ArrayList(JSONArray.toCollection(year));
		
		for(int i = 0; i < yearList.size(); i++) {
			rs = DB.executeQuery(stmt, sql3 + yearList.get(i));
			JSONArray skxList = new JSONArray();
			
			try {
				while(rs.next()) {
					JSONObject skx = new JSONObject();
					skx.accumulate("wk",rs.getString("wk"));
					skx.accumulate("lk", rs.getString("lk"));
					skx.accumulate("lk_num", rs.getObject("lk_num") == null ? "" : rs.getInt("lk_num"));
					skx.accumulate("wk_num", rs.getObject("wk_num") == null ? "" : rs.getInt("wk_num"));		
					skxList.add(skx);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			schoolInfoJSON.accumulate(yearList.get(i)+"", skxList);
		}
		
		
		DB.close(conn);
		DB.close(conn);
		DB.close(conn);
		
		setSchoolInfoData(schoolInfoJSON.toString());
		
		return "schoolinfo";
	}

	
	public String updateSchoolInfo() {
		
		Connection conn =null;
		Statement stmt = null;
		ResultSet rs = null;
		
		queryData = queryData.substring(1);
		queryData = queryData.substring(0,queryData.length()-1);
		queryData = queryData.replace("\\","");
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( queryData );
		sentData = (Map)JSONObject.toBean(jsonObject, Map.class);
		
		String whereStr = " where sid="+sid;
		String sql1 = "update school_key_info set ";
		String sql2 = "update school_scale set ";

		if(sentData.containsKey("sno")) {
			sql1 += "sno='" + sentData.get("sno") + "'";
		}
		
		if(sentData.containsKey("sname")) {
			sql1 += ",sname='" + sentData.get("sname") + "'";
		}
		
		if(sentData.containsKey("city")) {
			sql1 += ",city='" + sentData.get("city") + "'";
		}
		
		if(sentData.containsKey("category")) {
			sql1 += ",category='" + sentData.get("category") + "'";
		}
		
		if(sentData.containsKey("attr")) {
			sql1 += ",attr='" + sentData.get("attr") + "'";
		}
		
		if(sentData.containsKey("wk")) {
			sql1 += ",wk='" + sentData.get("wk") + "'";
		}
		
		if(sentData.containsKey("lk")) {
			sql1 += ",lk='" + sentData.get("lk") + "'";
		}
		
		if(sentData.containsKey("remark")) {
			sql1 += ",remark='" + sentData.get("remark") + "'";
		}
		
		if(sentData.containsKey("area")) {
			sql2 += "area='" + sentData.get("area") + "'";
		}
		
		if(sentData.containsKey("stu_num")) {
			sql2 += ",stu_num='" + sentData.get("stu_num") + "'";
		}
		
		if(sentData.containsKey("m_point")) {
			String mp = "";
			if(sentData.get("m_point").equals("")) {
				mp = "null";
			} else {
				mp = (String)sentData.get("m_point");
			}
			sql2 += ",m_point=" + mp;
		}
		
		if(sentData.containsKey("d_point")) {
			String dp = "";
			if(sentData.get("d_point").equals("")) {
				dp = "null";
			} else {
				dp = (String)sentData.get("d_point");
			}
			sql2 += ",d_point=" + dp;
		}
		
		sql1 += whereStr;
		sql2 += whereStr;
		
		System.out.println("sql1 : " + sql1);
		System.out.println("sql2 : " + sql2);
		Map<Integer,String> t = new HashMap<Integer,String>();
		t.put(1, "一");
		t.put(2, "二");
		t.put(3, "三");
		
		
		List year = (List)sentData.get("year");
		List oldyear = (List)sentData.get("oldyear");
		List<String> sqlList = new ArrayList<String>(); 
		for(int i = 0; i <  year.size(); i++) {
			String sql = "";
			String colSql = "";
			String valueSql = "";
			String wStr = "";
			String y = (String)year.get(i);
			String oy = String.valueOf(oldyear.get(i));
			List skxs = (List)sentData.get(y);
			for(int j = 0; j < skxs.size(); j++) {
				if(y.equals(oy)) {
					 sql = "update t_skx set year=" + y;
					 wStr = " where sid=" + sid + " and year=" + oy + " and pici='" + t.get(j+1) +"'";
				} else {
					 sql = "insert into t_skx";
					 colSql = "(pici,year";
					 valueSql = "values('" + t.get(j+1) + "'," + y;
				}
				
				
				Map skx = (Map)JSONObject.toBean(JSONObject.fromObject(skxs.get(j)), Map.class);
				Iterator<String> it = skx.keySet().iterator();
			
				while(it.hasNext()) {
					String key = it.next();
					if(key.equals("wk")) {
						String wk = (String)skx.get(key);
						String[] wkfs = wk.split("[^0-9]");
						String wk_ratio = "";
						if(wkfs.length > 1) {
							wk_ratio = Float.valueOf(wkfs[1])/Float.valueOf(wkfs[0]) + "";
						} else {
							wk_ratio = "-1";
						}
						if(!y.equals(oy)) {
							colSql += ",wk,wk_ratio";
							valueSql += ",'" + wk + "'," + wk_ratio;
						} else {
							sql += ",wk='" + wk + "',wk_ratio = " + wk_ratio;
						}
							
						
					} 
					
					if(key.equals("lk")) {
						String lk = (String)skx.get(key);
						String[] lkfs = lk.split("[^0-9]");
						String lk_ratio = "";
						if(lkfs.length > 1) {
							lk_ratio = Float.valueOf(lkfs[1])/Float.valueOf(lkfs[0]) + "";
						} else {
							lk_ratio = "-1";
						}
						if (!y.equals(oy)) {
							colSql += ",lk,lk_ratio";
							valueSql += ",'" + lk + "'," + lk_ratio;
						} else {
							sql += ",lk='" + lk + "',lk_ratio = " + lk_ratio;
						}
					
							
						
					} 
					
					if(key.equals("wk_num")) {
						String wk_num = (String)skx.get(key);
						
						if(wk_num.equals("")) {
							wk_num = "null";
						}
						                       
						
						if (!y.equals(oy)) {
							colSql += ",wk_num";
							valueSql += "," + wk_num;
						} else {
							sql += ",wk_num=" + wk_num;
						}
							
						
					} 
					
					if(key.equals("lk_num")) {
						String lk_num = (String)skx.get(key);
						
						if(lk_num.equals("")) {
							lk_num = "null";
						}
						                       
						if (!y.equals(oy)) {
							colSql += ",lk_num";
							valueSql += "," + lk_num;
						} else {
							sql += ",lk_num=" + lk_num;
						}
						
							
					} 
					
				}
				if (!y.equals(oy)) {
					sql += colSql +   ",sid) " + valueSql + "," + sid + ")"; 
				} else {
					sql += wStr;
				}
				
				sqlList.add(sql);
				
			}
		}
		
		conn = DB.getConn();
		stmt = DB.getStmt(conn);
		DB.updateQuery(stmt, sql1);
		DB.updateQuery(stmt, sql2);
		for(int i = 0; i < sqlList.size(); i++) {
			DB.updateQuery(stmt, sqlList.get(i));
		}
		DB.close(stmt);
		DB.close(conn);
		
		setSchoolInfoData("success");
		return "updateinfo";
	}
	
	public String saveSchoolInfo() {
		
		Connection conn =null;
		Statement stmt = null;
		ResultSet rs = null;
		
		queryData = queryData.substring(1);
		queryData = queryData.substring(0,queryData.length()-1);
		queryData = queryData.replace("\\","");
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( queryData );
		sentData = (Map)JSONObject.toBean(jsonObject, Map.class);
		
		String sql1 = "insert into school_key_info values(null";
		String sql2 = "insert into school_scale values(";
		
		if(sentData.containsKey("sno")) {
			sql1 += ",'" + sentData.get("sno") + "'";
		}
		
		if(sentData.containsKey("sname")) {
			sql1 += ",'" + sentData.get("sname") + "'";
		}
		
		if(sentData.containsKey("city")) {
			String[] address = (sentData.get("city") + "").split("\\s+");
			sql1 += ",'" + address[0] + "','" + address[1] + "'";
		}
		
		if(sentData.containsKey("attr")) {
			sql1 += ",'" + sentData.get("attr") + "'";
		}
		
		if(sentData.containsKey("class_name")) {
			sql1 += ",'" + sentData.get("class_name") + "'";
		}
		
		if(sentData.containsKey("wk")) {
			sql1 += ",'" + sentData.get("wk") + "'";
			sql1 += ",'" + sentData.get("wk") + "'";
		}

		// 理科和文科一样了，都变成段次
//		if(sentData.containsKey("lk")) {
//			sql1 += ",'" + sentData.get("lk") + "'";
//		}
		
		if(sentData.containsKey("remark")) {
			sql1 += ",'" + sentData.get("remark") + "'";
		}
		
		if(sentData.containsKey("area")) {
			sql2 += "'" + sentData.get("area") + "'";
		}
		if(sentData.containsKey("stu_num")) {
			sql2 += ",'" + sentData.get("stu_num") + "'";
		}
		
		if(sentData.containsKey("m_point")) {
			String mp = (String)sentData.get("m_point");
			if(mp.equals("")) {
				sql2 += ",null";
			} else {
				sql2 += ",'" + sentData.get("m_point") + "'";
			}
		}
		
		if(sentData.containsKey("d_point")) {
			String mp = (String)sentData.get("d_point");
			if(mp.equals("")) {
				sql2 += ",null";
			} else {
				sql2 += ",'" + sentData.get("d_point") + "'";
			}
		}
		
		sql1 += ")";
		
		
		int sid = 0;
		
		conn = DB.getConn();
		stmt = DB.getStmt(conn);
		try {
			stmt.executeUpdate(sql1,Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			while(rs.next()) {
				sid = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		sql2 += "," + sid + ")";
		
		Map<Integer,String> t = new HashMap<Integer,String>();
		t.put(1, "一");
		t.put(2, "二");
		t.put(3, "三");
		
		
		List year = (List)sentData.get("year");
		List<String> sqlList = new ArrayList<String>(); 
		for(int i = 0; i <  year.size(); i++) {
			String y = (String)year.get(i);
			List skxs = (List)sentData.get(y);
			for(int j = 0; j < skxs.size(); j++) {
				String sql = "insert into t_skx";
				String colSql = "(pici,year";
				
				
				String valueSql = "values('" + t.get(j+1) + "'," + y;
				
				String wStr = " where sid=" + sid + " and year=" + y + " and pici='" + t.get(j+1) +"'";
				
				Map skx = (Map)JSONObject.toBean(JSONObject.fromObject(skxs.get(j)), Map.class);
				Iterator<String> it = skx.keySet().iterator();
				
				while(it.hasNext()) {
					String key = it.next();
					if(key.equals("wk")) {
						String wk = (String)skx.get(key);
						String[] wkfs = wk.split("[^0-9]");
						String wk_ratio = "";
						if(wkfs.length > 1) {
							wk_ratio = Float.valueOf(wkfs[1])/Float.valueOf(wkfs[0]) + "";
						} else {
							wk_ratio = "-1";
						}
						colSql += ",wk,wk_ratio";
						valueSql += ",'" + wk + "'," + wk_ratio;
					} 
					
					if(key.equals("lk")) {
						String lk = (String)skx.get(key);
						String[] lkfs = lk.split("[^0-9]");
						String lk_ratio = "";
						if(lkfs.length > 1) {
							lk_ratio = Float.valueOf(lkfs[1])/Float.valueOf(lkfs[0]) + "";
						} else {
							lk_ratio = "-1";
						}
						colSql += ",lk,lk_ratio";
						valueSql += ",'" + lk + "'," + lk_ratio;
					} 
					
					if(key.equals("wk_num")) {
						String wk_num = (String)skx.get(key);
						
						if(wk_num.equals("")) {
							wk_num = "null";
						} 
						      
						colSql += ",wk_num";
						valueSql += "," + wk_num;
						
					} 
					
					if(key.equals("lk_num")) {
						String lk_num = (String)skx.get(key);
						
						if(lk_num.equals("")) {
							lk_num = "null";
						}                   
						colSql += ",lk_num";
						valueSql += "," + lk_num;
					} 
					
				}
				sql += colSql +   ",sid) " + valueSql + "," + sid + ")"; 
				sqlList.add(sql);
				
			}
		}
		

		try {
			stmt.executeUpdate(sql2);
			for(int i = 0; i < sqlList.size(); i++) {
				stmt.executeUpdate(sqlList.get(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DB.close(rs);
		DB.close(stmt);
		DB.close(conn);
		System.out.println("sql1：" + sql1);
		System.out.println("sql2：" + sql2);
		
		setSchoolInfoData("success");
		
		return "addSchoolInfo";
	}
	
	public String deleteSchool() {
		String sql1 = "delete from school_key_info where sid=" + sid;
		String sql2 = "delete from school_scale where sid=" + sid;
		String sql3 = "delete from t_skx where sid=" + sid;
		
		Connection conn =null;
		Statement stmt = null;
		ResultSet rs = null;
		
		conn = DB.getConn();
		stmt = DB.getStmt(conn);
		
	
		
		try {
			stmt.executeUpdate(sql2);
			stmt.executeUpdate(sql3);
			stmt.executeUpdate(sql1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setSchoolInfoData("success");
		return "deleteSchool";
	}
	
	
	public String getLastThreeYear() {
		
		Connection conn =null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select DISTINCT year from t_skx where year is not NULL ORDER BY year DESC LIMIT 3";
		 
		JSONArray year = new JSONArray();
		
		
		conn = DB.getConn();
		stmt = DB.getStmt(conn);
		rs = DB.executeQuery(stmt, sql);
		
		try {
			while(rs.next()) {
				year.add(rs.getInt("year"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		setSchoolInfoData(year.toString());
		
		return "year";
	}
	
	
}//class
