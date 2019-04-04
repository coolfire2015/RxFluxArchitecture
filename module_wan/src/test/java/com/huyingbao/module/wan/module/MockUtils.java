package com.huyingbao.module.wan.module;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
public class MockUtils {
    private static MockComponent mMockComponent;

    public static void setComponent(MockComponent mockComponent) {
        mMockComponent = mockComponent;
    }

    public static MockComponent getComponent() {
        return mMockComponent;
    }
}
