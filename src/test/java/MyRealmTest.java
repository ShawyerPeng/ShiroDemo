import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/*.xml","classpath*:mybatis/SqlMapConfig.xml"})
public class MyRealmTest {
    //@Test
    //public void testFooRealm() {
    //    // 创建SecurityManager工厂，通过ini配置文件创建 SecurityManager工厂
    //    Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-realm.ini");
    //    // 创建SecurityManager
    //    SecurityManager securityManager = factory.getInstance();
    //    // 设置SecurityManager到运行环境中，保持单例模式
    //    SecurityUtils.setSecurityManager(securityManager);
    //    // 从SecurityUtils里边创建一个subject
    //    Subject subject = SecurityUtils.getSubject();
    //    // 在认证提交前准备token（令牌）
    //    // 这里的账号和密码 将来是由用户输入进去
    //    UsernamePasswordToken token = new UsernamePasswordToken("lee", "123456");
    //    try {
    //        // 执行认证提交
    //        subject.login(token);
    //    } catch (AuthenticationException e) {
    //        e.printStackTrace();
    //    }
    //    // 是否认证通过
    //    boolean isAuthenticated = subject.isAuthenticated();
    //    System.out.println("是否认证通过：" + isAuthenticated);
    //}

    @Test
    public void testLoginWithNoGivenRealm () {
        // 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:spring/shiro-realm.ini");

        // 2、得到SecurityManager实例并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        // org.apache.shiro.mgt.DefaultSecurityManager
        System.out.println("security manager is " + securityManager + ".");
        SecurityUtils.setSecurityManager(securityManager);

        // 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("Shawyer", "123");

        try{
            // 4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            // 5、身份验证失败
            System.out.println("用户身份验证失败");
            e.printStackTrace();
        }

        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        if (subject.isAuthenticated()) {
            System.out.println("用户登录成功。");
        } else {
            System.out.println("用户登录失败。");
        }

        // 6、退出
        subject.logout();
    }
}
