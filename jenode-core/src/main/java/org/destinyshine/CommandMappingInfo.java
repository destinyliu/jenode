package org.destinyshine;

/**
 * 映射元信息，条件
 * @author destinyliu
 */
public class CommandMappingInfo {

    private Class<?> commandType;

    public Class<?> getCommandType() {
        return commandType;
    }

    public void setCommandType(Class<?> commandType) {
        this.commandType = commandType;
    }
}
