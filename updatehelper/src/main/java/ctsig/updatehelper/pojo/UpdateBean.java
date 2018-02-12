package ctsig.updatehelper.pojo;


import com.google.gson.annotations.SerializedName;

/**
 * Created by zyz on 2017/11/15.
 */

public class UpdateBean {


    /**
     * code : 200
     * message : 成功
     * datas : {"id":32,"packageName":"OnlineRead-v1.0.02beta-build10002","name":"文档分享","type":{"id":1,"type":1},"version":"10002","versionCode":10002,"updateUser":null,"createTime":1510735345000,"updateTime":null,"project":{"id":6,"name":"文档分享","path":"文档分享","comment":null,"available":null},"changeLog":null,"forceUpgradeVersion":"10002","path":"文档分享10002.apk","downloadPath":"192.168.8.163:8010/api/app/文档分享/OnlineRead-v1.0.02beta-build10002/10002/true/1","stopUse":false,"forceUpgrade":true,"downloadCount":0,"updateCount":0}
     * flexiPageDto : null
     */

    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("datas")
    private DatasBean datas;
    @SerializedName("flexiPageDto")
    private Object flexiPageDto;

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public void setFlexiPageDto(Object flexiPageDto) {
        this.flexiPageDto = flexiPageDto;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public Object getFlexiPageDto() {
        return flexiPageDto;
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", datas=" + datas +
                ", flexiPageDto=" + flexiPageDto +
                '}';
    }

    public static class DatasBean {
        /**
         * id : 32
         * packageName : OnlineRead-v1.0.02beta-build10002
         * name : 文档分享
         * type : {"id":1,"type":1}
         * version : 10002
         * versionCode : 10002
         * updateUser : null
         * createTime : 1510735345000
         * updateTime : null
         * project : {"id":6,"name":"文档分享","path":"文档分享","comment":null,"available":null}
         * changeLog : null
         * forceUpgradeVersion : 10002
         * path : 文档分享10002.apk
         * downloadPath : 192.168.8.163:8010/api/app/文档分享/OnlineRead-v1.0.02beta-build10002/10002/true/1
         * stopUse : false
         * forceUpgrade : true
         * downloadCount : 0
         * updateCount : 0
         */

        @SerializedName("id")
        private int id;
        @SerializedName("packageName")
        private String packageName;
        @SerializedName("name")
        private String name;
        @SerializedName("type")
        private TypeBean type;
        @SerializedName("version")
        private String version;
        @SerializedName("versionCode")
        private int versionCode;
        @SerializedName("updateUser")
        private Object updateUser;
        @SerializedName("createTime")
        private long createTime;
        @SerializedName("updateTime")
        private Object updateTime;
        @SerializedName("project")
        private ProjectBean project;
        @SerializedName("changeLog")
        private Object changeLog;
        @SerializedName("forceUpgradeVersion")
        private String forceUpgradeVersion;
        @SerializedName("path")
        private String path;
        @SerializedName("downloadPath")
        private String downloadPath;
        @SerializedName("stopUse")
        private boolean stopUse;
        @SerializedName("forceUpgrade")
        private boolean forceUpgrade;
        @SerializedName("downloadCount")
        private int downloadCount;
        @SerializedName("updateCount")
        private int updateCount;

        @Override
        public String toString() {
            return "DatasBean{" +
                    "id=" + id +
                    ", packageName='" + packageName + '\'' +
                    ", name='" + name + '\'' +
                    ", type=" + type +
                    ", version='" + version + '\'' +
                    ", versionCode=" + versionCode +
                    ", updateUser=" + updateUser +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", project=" + project +
                    ", changeLog=" + changeLog +
                    ", forceUpgradeVersion='" + forceUpgradeVersion + '\'' +
                    ", path='" + path + '\'' +
                    ", downloadPath='" + downloadPath + '\'' +
                    ", stopUse=" + stopUse +
                    ", forceUpgrade=" + forceUpgrade +
                    ", downloadCount=" + downloadCount +
                    ", updateCount=" + updateCount +
                    '}';
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setType(TypeBean type) {
            this.type = type;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public void setUpdateUser(Object updateUser) {
            this.updateUser = updateUser;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public void setProject(ProjectBean project) {
            this.project = project;
        }

        public void setChangeLog(Object changeLog) {
            this.changeLog = changeLog;
        }

        public void setForceUpgradeVersion(String forceUpgradeVersion) {
            this.forceUpgradeVersion = forceUpgradeVersion;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public void setDownloadPath(String downloadPath) {
            this.downloadPath = downloadPath;
        }

        public void setStopUse(boolean stopUse) {
            this.stopUse = stopUse;
        }

        public void setForceUpgrade(boolean forceUpgrade) {
            this.forceUpgrade = forceUpgrade;
        }

        public void setDownloadCount(int downloadCount) {
            this.downloadCount = downloadCount;
        }

        public void setUpdateCount(int updateCount) {
            this.updateCount = updateCount;
        }

        public int getId() {
            return id;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getName() {
            return name;
        }

        public TypeBean getType() {
            return type;
        }

        public String getVersion() {
            return version;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public Object getUpdateUser() {
            return updateUser;
        }

        public long getCreateTime() {
            return createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public ProjectBean getProject() {
            return project;
        }

        public Object getChangeLog() {
            return changeLog;
        }

        public String getForceUpgradeVersion() {
            return forceUpgradeVersion;
        }

        public String getPath() {
            return path;
        }

        public String getDownloadPath() {
            return downloadPath;
        }

        public boolean getStopUse() {
            return stopUse;
        }

        public boolean getForceUpgrade() {
            return forceUpgrade;
        }

        public int getDownloadCount() {
            return downloadCount;
        }

        public int getUpdateCount() {
            return updateCount;
        }

        public static class TypeBean {
            /**
             * id : 1
             * type : 1
             */

            @SerializedName("id")
            private int id;
            @SerializedName("type")
            private int type;

            @Override
            public String toString() {
                return "TypeBean{" +
                        "id=" + id +
                        ", type=" + type +
                        '}';
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getId() {
                return id;
            }

            public int getType() {
                return type;
            }
        }

        public static class ProjectBean {
            /**
             * id : 6
             * name : 文档分享
             * path : 文档分享
             * comment : null
             * available : null
             */

            @SerializedName("id")
            private int id;
            @SerializedName("name")
            private String name;
            @SerializedName("path")
            private String path;
            @SerializedName("comment")
            private Object comment;
            @SerializedName("available")
            private Object available;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public void setComment(Object comment) {
                this.comment = comment;
            }

            public void setAvailable(Object available) {
                this.available = available;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getPath() {
                return path;
            }

            public Object getComment() {
                return comment;
            }

            public Object getAvailable() {
                return available;
            }
        }
    }
}
