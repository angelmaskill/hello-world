package net.myl.business.mock;

import net.myl.business.base.BaseJunitClassTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 马彦卢
 * @version V1.0
 * @Description
 * @Package net.myl.business.mock
 * @date 2020/8/7 16:28
 */
public class MainTstObjectMockitoTest extends BaseJunitClassTest {
    // 指定Mock的对象
    @Mock
    private MayMockInterface mayMockObject;

    private MainTstObject mainTstObject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // 声明测试用例类
        this.mainTstObject = new MainTstObject();
        this.mainTstObject.setMayMockObject(this.mayMockObject);
    }

    @Test
    public void process() {
        String str1 = "Mockito返回字符串。";
        String str2 = "Mockito返回字符串。";
        Mockito.when(this.mayMockObject.getString(str2)).thenReturn(""); // Mock返回值
        String ret = this.mainTstObject.create(str1, str2);
        Assert.assertEquals(ret, str1);
    }

    @After
    public void tearDown() {
    }
}
