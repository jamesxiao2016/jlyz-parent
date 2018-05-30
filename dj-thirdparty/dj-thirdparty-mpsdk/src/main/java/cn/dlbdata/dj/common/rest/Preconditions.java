package cn.dlbdata.dj.common.rest;

public class Preconditions {

    /**
     * 检查是否不为NULL，为NULl抛出NullPointerException
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }
}
