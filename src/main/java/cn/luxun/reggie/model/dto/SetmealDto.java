package cn.luxun.reggie.model.dto;


import cn.luxun.reggie.model.entity.Setmeal;
import cn.luxun.reggie.model.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
