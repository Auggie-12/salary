package cn.jt.pojo.result;

import lombok.Data;
import java.util.*;

@Data
public class ChangeCascader {

    private int value;      // 绑定的值

    private String label;   // 显示文本

    private List<ReasonCascader> children;  // 包含选项

}
