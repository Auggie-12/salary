package cn.jt.pojo.result;

import lombok.Data;

@Data
public class ChangeReason {

    int reasonId;                // 具体变动类型号

    String description;         // 具体津贴名

    double totalMoney;          // 该种类型变动本月的总金额

}
