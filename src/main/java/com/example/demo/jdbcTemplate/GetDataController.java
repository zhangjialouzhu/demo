package com.example.demo.jdbcTemplate;

import com.example.demo.bean.BaseResult;
import com.example.demo.bean.SqlBean;
import com.example.demo.utils.ExcelUtil;
import com.example.demo.utils.SQLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

//@Controller
public class GetDataController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插寻数据库
     * @param response
     * @param sqlBean
     * @throws Exception
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public void test(HttpServletResponse response,SqlBean sqlBean) throws Exception{
        String sql = SQLUtil.createSql(sqlBean);
        jdbcTemplate.execute("");
        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT m.* from PATENT_MAIN m");
        if(list.size()==0){
            return;
        }
        ExcelUtil.writeExcel(response,"test.xls",list);
    }
}
