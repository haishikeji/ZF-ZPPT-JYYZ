package com.px.employmentsite.mapper;

import com.px.employmentsite.model.SysRoleSysMenuShortcutExample;
import com.px.employmentsite.model.SysRoleSysMenuShortcutKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRoleSysMenuShortcutMapper {
    long countByExample(SysRoleSysMenuShortcutExample example);

    int deleteByExample(SysRoleSysMenuShortcutExample example);

    int deleteByPrimaryKey(SysRoleSysMenuShortcutKey key);

    int insert(SysRoleSysMenuShortcutKey row);

    int insertSelective(SysRoleSysMenuShortcutKey row);

    List<SysRoleSysMenuShortcutKey> selectByExample(SysRoleSysMenuShortcutExample example);

    int updateByExampleSelective(@Param("row") SysRoleSysMenuShortcutKey row, @Param("example") SysRoleSysMenuShortcutExample example);

    int updateByExample(@Param("row") SysRoleSysMenuShortcutKey row, @Param("example") SysRoleSysMenuShortcutExample example);
}