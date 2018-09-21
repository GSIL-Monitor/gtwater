package com.gt.manager;

/**
 * <p>ClassName: UserRoleCommonEnum.java</p>
 * <p>Description:用户角色杖举值 </p>
 * <p>author: zhanghongsheng</p>
 * <p>2017年11月20日 上午</p>
 */
public enum UserRoleCommonEnum {

    USER("普通用户", 1),
    RECEPTIONIST("前台", 2),
    MEMBERSHIP("会藉", 3),
    PERSONAL_TRAINER("私人教练", 4),
    EMPLOYEE("健身房员工", 5),
    MANAGER("健身房管理员", 6);

    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private UserRoleCommonEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 根据杖值获取相应的名称
    public static String getName(int index) {
        for (UserRoleCommonEnum c : UserRoleCommonEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // 根据杖值获取相应的杖举值
    public static UserRoleCommonEnum getUserRole(int index) {
        for (UserRoleCommonEnum c : UserRoleCommonEnum.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}