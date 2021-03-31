package tu.vvps;

public class NumbersDTO {

    private final long userId;
    private final long pageId;
    private final long courseModuleId;

    public NumbersDTO(long userId, long pageId, long courseModuleId) {
        this.userId = userId;
        this.pageId = pageId;
        this.courseModuleId = courseModuleId;
    }

    public long getUserId() {
        return userId;
    }

    public long getPageId() {
        return pageId;
    }

    public long getCourseModuleId() {
        return courseModuleId;
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
