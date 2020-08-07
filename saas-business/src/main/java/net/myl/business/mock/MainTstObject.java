package net.myl.business.mock;

import org.springframework.stereotype.Service;

/**
 * @author 马彦卢
 * @version V1.0
 * @Description
 * @Package net.myl.business.mock
 * @date 2020/8/7 16:28
 */
@Service
public class MainTstObject {
    private MayMockInterface mayMockObject; // 引用了外接接口

    public String create(String str1, String str2) {
        return str1 + this.mayMockObject.getString(str2);
    }

    public void setMayMockObject(MayMockInterface mayMockObject) {
        this.mayMockObject = mayMockObject;
    }
}
