package tu.vvps;

public class NumbersDTO {

    private long userId;
    private long pageId;
    private long courseModuleId;

    public NumbersDTO(long userId, long pageId, long courseModuleId) {
        this.userId = userId;
        this.pageId = pageId;
        this.courseModuleId = courseModuleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPageId() {
        return pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public long getCourseModuleId() {
        return courseModuleId;
    }

    public void setCourseModuleId(long courseModuleId) {
        this.courseModuleId = courseModuleId;
    }

    @Override
    public String toString() {
        return "NumbersDTO{" +
                "userId=" + userId +
                ", pageId=" + pageId +
                ", courseModuleId=" + courseModuleId +
                '}';
    }
}
