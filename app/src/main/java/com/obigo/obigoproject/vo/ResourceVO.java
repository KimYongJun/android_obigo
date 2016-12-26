package com.obigo.obigoproject.vo;

/**
 * Created by O BI HE ROCK on 2016-12-22
 * 김용준, 최현욱
 */

public class ResourceVO {
    private int resourceNumber;
    private String resourceName;
    private String path;
    private String resourceVersion;
    private String bundleKey;

    public int getResourceNumber() {
        return resourceNumber;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getPath() {
        return path;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public String getBundleKey() {
        return bundleKey;
    }

    public void setResourceNumber(int resourceNumber) {
        this.resourceNumber = resourceNumber;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public void setBundleKey(String bundleKey) {
        this.bundleKey = bundleKey;
    }

    @Override
    public String toString() {
        return "ResourceVO{" +
                "resourceNumber=" + resourceNumber +
                ", resourceName='" + resourceName + '\'' +
                ", path='" + path + '\'' +
                ", resourceVersion='" + resourceVersion + '\'' +
                ", bundleKey='" + bundleKey + '\'' +
                '}';
    }
}
