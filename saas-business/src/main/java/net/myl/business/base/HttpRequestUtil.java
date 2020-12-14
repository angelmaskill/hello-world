package net.myl.business.base;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HttpRequestUtil
 * @Description httprequest 取值相关工具方法
 * @Author yanlu.myl
 * @Date 2020-12-14 16:03
 */
public class HttpRequestUtil {
    /**
     * 获取请求参数中所有的信息, 不包含value为空的k-v对
     *
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParamWithoutNullValue(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }

    /***************************异步通知辅助方法start********************************************************/
    /**
     * @param request httprequest对象
     * @return 请求参数xml
     * @author sw
     * @Title: parseUmpayNotify
     * @Description: 异步通知参数解析
     * @createTime : 2017年12月21日下午5:24:25
     */
    public static String getReaderContentFromHttpRequest(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            br = request.getReader();
            for (String line; (line = br.readLine()) != null; ) {
                result.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                }
        }
        return result.toString();
    }
}
