package example;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;

public class JavaAgentMain1 {
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("hello from java agent 1. args = " + args);
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(
                    ClassLoader loader,
                    String className, Class<?> classBeingRedefined,
                    ProtectionDomain protectionDomain,
                    byte[] classfileBuffer
            ) throws IllegalClassFormatException {
                try {
                if("scala/Some".equals(className)){
                    var cp = ClassPool.getDefault();
                    var cc = cp.get("scala.Some");
                    var isFinal = Modifier.isFinal(cc.getModifiers());
                    if (isFinal) {
                        System.out.println("scala.Someはfinalなので無理やりfinal消します");
                        cc.setModifiers(Modifier.clear(cc.getModifiers(), Modifier.FINAL));
                        return cc.toBytecode();
                    } else {
                        System.out.println("scala.Someはfinalではないぞ");
                        return null;
                    }
                } else {
                    return null;
                }
                } catch (Throwable e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
