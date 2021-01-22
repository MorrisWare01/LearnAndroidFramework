package com.morrisware.android.ttad

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor

import java.util.jar.JarEntry
import java.util.jar.JarFile

class TtadTransform extends Transform {

    @Override
    String getName() {
        return "com.morrisware.android.ttad"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        transformInvocation.inputs.each {
            it.jarInputs.each {
                it.file
            }
            it.directoryInputs.each {
                it.file
            }
        }
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        transformInvocation.inputs.each {
            it.jarInputs.each {
                it.file
            }
            it.directoryInputs.each {
                it.file
            }
        }
//        transformInvocation.inputs.each { TransformInput input ->
//            input.jarInputs.each { JarInput jarInput ->
//                String destName = jarInput.name
//                // rename jar files
//                def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath)
//                if (destName.endsWith(".jar")) {
//                    destName = destName.substring(0, destName.length() - 4)
//                }
//
//                // input file
//                File src = jarInput.file
//
//                // output file
//                File dest = transformInvocation.outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR)
//
//                // deal with question
//                def jarFile = new JarFile(src)
//                Enumeration enumeration = jarFile.entries()
//                while (enumeration.hasMoreElements()) {
//                    JarEntry jarEntry = (JarEntry) enumeration.nextElement()
//                    String entryName = jarEntry.getName()
//                    if ("com/leto/game/ad/toutiao/ToutiaoVideoAD.class" == entryName) {
//                        InputStream is = jarFile.getInputStream(jarEntry)
//                        dealWithClass(is)
//                        is.close()
//                    }
//                }
//                jarFile.close()
//
//                // 拷贝
//                FileUtils.copyFile(src, dest)
//            }
//        }
    }

    private byte[] dealWithClass(InputStream inputStream) {
        ClassReader cr = new ClassReader(inputStream)
        ClassWriter cw = new ClassWriter(cr, 0)
        ClassVisitor cv = new ScanClassVisitor(Opcodes.ASM5, cw)
        cv.accept(cr, ClassReader.EXPAND_FRAMES)
        return cw.toByteArray()
    }

    static class ScanClassVisitor extends ClassVisitor {

        ScanClassVisitor(int i, ClassVisitor classVisitor) {
            super(i, classVisitor)
        }

        @Override
        MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
            MethodVisitor mv = super.visitMethod(i, s, s1, s2, strings)
            return mv
        }
    }
}


