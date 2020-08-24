package net.myl.business.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.myl.business.base.ApiResult;
import net.myl.business.domain.SysNotice;
import net.myl.business.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 公告 信息操作处理
 *
 * @author saas_portal
 */
@RestController
@RequestMapping("/notice")
public class SysNoticeController  {
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
}
