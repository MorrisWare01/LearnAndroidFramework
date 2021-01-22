package com.xiqu.android.register.utils

class ScanSetting {
    static final String PLUGIN_NAME = "com.xiqu.android.register"
    /**
     * The register code is generated into this class
     */
    static final String GENERATE_TO_CLASS_NAME = 'com/xiqu/android/base/register/RegisterCenter'
    /**
     * you know. this is the class file(or entry in jar file) name
     */
    static final String GENERATE_TO_CLASS_FILE_NAME = GENERATE_TO_CLASS_NAME + '.class'
    /**
     * The register code is generated into this method
     */
    static final String GENERATE_TO_METHOD_NAME = 'loadModules'
    /**
     * The package name of the interfaces
     */
    private static final INTERFACE_PACKAGE_NAME = 'com/xiqu/android/base/register/'
    /**
     * only scan this package
     */
    static final String SCAN_CLASS_PACKAGE_NAME = 'com/xiqu'
    /**
     * register method name in class: {@link #GENERATE_TO_CLASS_NAME}
     */
    static final String REGISTER_METHOD_NAME = 'registerModule'
    /**
     * scan for classes which implements this interface
     */
    String interfaceName = ''

    /**
     * jar file which contains class: {@link #GENERATE_TO_CLASS_NAME}
     */
    File fileContainsInitClass
    /**
     * scan result for {@link #interfaceName}
     * class names in this list
     */
    ArrayList<String> classList = new ArrayList<>()

    /**
     * constructor for arouter-auto-register settings
     * @param interfaceName interface to scan
     */
    ScanSetting(String interfaceName) {
        this.interfaceName = INTERFACE_PACKAGE_NAME + interfaceName
    }

}