package jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class JMeter测试用例 implements JavaSamplerClient {

    @Override
    public void setupTest(JavaSamplerContext context) {
        System.out.println("init.");
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult result = new SampleResult();
        result.sampleStart();// 开始计时
        String user = context.getParameter("user");
        String pwd = context.getParameter("pwd");
        System.out.println(user);
        System.out.println(pwd);
        result.setSuccessful(true);
        result.sampleEnd();
        return result;
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
        System.out.println("stop test.");
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("user", "");
        arguments.addArgument("pwd", "");
        return arguments;
    }

}
