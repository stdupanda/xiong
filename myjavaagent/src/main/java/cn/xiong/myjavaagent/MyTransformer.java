package cn.xiong.myjavaagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

public class MyTransformer implements ClassFileTransformer {

    @SuppressWarnings("rawtypes")
    public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        String cname = "cn.xxx.config.spring.ConsumerBean";
        ClassPool classPool = new ClassPool();
        classPool.insertClassPath(new LoaderClassPath(loader));
        if (className.replaceAll("/", ".").equals(cname)) {
            try {
                CtClass ctClass = classPool.get(cname);
                // note this is calling "insertBefore", so the order is opposite
                CtMethod ctMethod = ctClass.getDeclaredMethod("afterPropertiesSet");
                ctMethod.insertBefore("System.out.println(\"完成  ->  \" + getConnStrategy());");
                ctMethod.insertBefore("getConnStrategy().setInitialSize(1);");
                ctMethod.insertBefore("setConnStrategy(new com.jd.jsf.gd.config.ConnStrategyConfig());");
                ctMethod.insertBefore("setMaxConnectThreads(1);");

                byte[] bytecode = ctClass.toBytecode();
                ctClass.detach();

                return bytecode;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // when error occurs
        return new byte[0];
    }
}