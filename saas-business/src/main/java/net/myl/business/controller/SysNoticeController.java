package net.myl.business.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.myl.business.base.ApiResult;
import net.myl.business.base.HttpRequestUtil;
import net.myl.business.base.StringUtils;
import net.myl.business.domain.SysNotice;
import net.myl.business.filter.BodyReaderHttpServletRequestWrapper;
import net.myl.business.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 公告 信息操作处理
 *
 * @author saas_portal
 */
@RestController
@RequestMapping("/notice")
public class SysNoticeController {
    @Autowired
    private ISysNoticeService iSysNoticeService;

    /**
     * 获取通知公告列表
     */
    @GetMapping("/list")
    public ApiResult list() {
        PageHelper.startPage(1, 10);
        List<SysNotice> list = iSysNoticeService.selectNoticeList(new SysNotice());
        PageInfo<SysNotice> pageInfo = new PageInfo<SysNotice>(list);
        System.out.println(pageInfo.getTotal());
        return ApiResult.success(list);
    }

    @GetMapping("/health")
    public String health() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String contentType = request.getContentType();
        System.out.println("contentType: " + contentType);

        System.out.println("para name: " + request.getParameter("name"));

        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k, v) -> {
            System.out.println("getParameterMap key: " + k + "; v:" + StringUtils.valueOf(v, true));
        });

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            System.out.println("para map names: " + parameterNames.nextElement());
        }

        Map<String, String> noEnptyMap = HttpRequestUtil.getAllRequestParamWithoutNullValue(request);
        noEnptyMap.forEach((k, v) -> {
            System.out.println("getAllRequestParamWithoutNullValue key: " + k + "; v:" + v);
        });

        System.out.println("para map readerContent: " + HttpRequestUtil.getReaderContentFromHttpRequest(request));
        System.out.println("para map readerContent: " + new BodyReaderHttpServletRequestWrapper(request).getBodyString(request));
        return "UP";
    }
}
